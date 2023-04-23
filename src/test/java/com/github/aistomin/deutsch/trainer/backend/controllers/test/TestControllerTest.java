/*
 * Copyright (c) 2023, Andrej Istomin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.aistomin.deutsch.trainer.backend.controllers.test;

import com.github.aistomin.deutsch.trainer.backend.controllers.utils.TestUserHolder;
import com.github.aistomin.deutsch.trainer.backend.controllers.vocabulary.VocabularyItemDto;
import com.github.aistomin.deutsch.trainer.backend.model.QuestionRepository;
import com.github.aistomin.deutsch.trainer.backend.model.Test.Status;
import com.github.aistomin.deutsch.trainer.backend.model.TestRepository;
import com.github.aistomin.deutsch.trainer.backend.services.impl.TestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Test for {@link TestController}.
 *
 * @since 0.1
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
final class TestControllerTest {

    /**
     * Test REST template.
     */
    @Autowired
    private TestRestTemplate template;

    /**
     * Test user's holder.
     */
    @Autowired
    private TestUserHolder holder;

    /**
     * Question's repository.
     */
    @Autowired
    private QuestionRepository questions;

    /**
     * Test's repository.
     */
    @Autowired
    private TestRepository tests;

    /**
     * Check that we can correctly start a test.
     */
    @Test
    void testStart() {
        createRandomItem();
        final var test = this.template.getForEntity(
            "/test/start", TestDto.class
        ).getBody();
        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.getId());
        Assertions.assertNotNull(test.getDateCreated());
        Assertions.assertEquals(
            TestServiceImpl.QUESTIONS_COUNT, test.getQuestions().size()
        );
    }

    /**
     * Check that we can correctly answer questions.
     */
    @Test
    void testAnswer() {
        createRandomItem();
        final var test = this.template.getForEntity(
            "/test/start", TestDto.class
        ).getBody();
        final var unanswered = new ArrayList<>(test.getQuestions());
        final var question = unanswered
            .stream()
            .findFirst()
            .get();
        final var cheat = questions.findById(question.getId()).get();
        final var correct = this.template.postForEntity(
            "/test/answer",
            new HttpEntity<>(
                new AnswerDto(question.getId(), cheat.getAnswer())
            ),
            AnswerResultDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, correct.getStatusCode());
        Assertions.assertEquals(
            AnswerResultDto.Result.RIGHT, correct.getBody().getResult()
        );
        final var bs = "some bullshit";
        final var wrong = this.template.postForEntity(
            "/test/answer",
            new HttpEntity<>(
                new AnswerDto(question.getId(), bs)
            ),
            AnswerResultDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, wrong.getStatusCode());
        Assertions.assertEquals(
            AnswerResultDto.Result.WRONG, wrong.getBody().getResult()
        );
        Assertions.assertEquals(bs, wrong.getBody().getProvided());
        Assertions.assertEquals(
            cheat.getAnswer(), wrong.getBody().getCorrect()
        );
        unanswered.remove(question);
        final var missing = this.template.postForEntity(
            "/test/answer",
            new HttpEntity<>(
                new AnswerDto(666L, "10")
            ),
            AnswerResultDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, missing.getStatusCode());
        Assertions.assertEquals(
            AnswerResultDto.Result.NOT_FOUND, missing.getBody().getResult()
        );
        Assertions.assertEquals(
            Status.ACTIVE, this.tests.findById(test.getId()).get().getStatus()
        );
    }

    /**
     * Check that we can correctly calculate user's statistic.
     */
    @Test
    void testStatistic() {
        final var random = new Random();
        createRandomItem();
        final var test = this.template.getForEntity(
            "/test/start", TestDto.class
        ).getBody();
        final var total = new ArrayList<>(test.getQuestions());
        final var correct = new ArrayList<QuestionDto>();
        for (final var item : total) {
            final var cheat = this.questions.findById(item.getId()).get();
            final String answer;
            if (random.nextBoolean()) {
                answer = cheat.getAnswer();
                correct.add(item);
            } else {
                answer = UUID.randomUUID().toString();
            }
            this.template.postForEntity(
                "/test/answer",
                new HttpEntity<>(new AnswerDto(item.getId(), answer)),
                AnswerResultDto.class
            );
        }
        final var stat = this.template.getForEntity(
            "/test/stat", List.class
        ).getBody();
        Assertions.assertEquals(1, stat.size());
        final var item = (Map) stat.get(0);
        Assertions.assertEquals(
            this.holder.defaultUser().getId(),
            Long.valueOf(item.get("userId").toString())
        );
        Assertions.assertEquals(
            test.getId(),
            Long.valueOf(item.get("testId").toString())
        );
        Assertions.assertEquals(
            TestServiceImpl.QUESTIONS_COUNT,
            item.get("total")
        );
        Assertions.assertEquals(
            correct.size(),
            item.get("correct")
        );
    }

    /**
     * Create a random vocabulary item.
     *
     * @return Vocabulary item.
     */
    private VocabularyItemDto createRandomItem() {
        final var response = this.template.postForEntity(
            "/vocabulary",
            new VocabularyItemDto(
                null,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                null,
                null,
                this.holder.defaultUser(),
                null
            ),
            VocabularyItemDto.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        return response.getBody();
    }
}

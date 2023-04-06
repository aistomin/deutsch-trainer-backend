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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;

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
     * Check that we can correctly start a test.
     */
    @Test
    void testStart() {
        final var test = this.template.getForEntity(
            "/test/start", TestDto.class
        ).getBody();
        Assertions.assertNotNull(test);
        Assertions.assertEquals(1L, test.getId());
        Assertions.assertNotNull(test.getDateCreated());
        Assertions.assertEquals(2, test.getQuestions().size());
    }

    /**
     * Check that we can correctly answer questions.
     */
    @Test
    void testAnswer() {
        final var test = this.template.getForEntity(
            "/test/start", TestDto.class
        ).getBody();
        final var question = new ArrayList<>(test.getQuestions())
            .stream()
            .filter(q -> q.getText().equals("2 X 2"))
            .findFirst()
            .get();
        final var correct = this.template.postForEntity(
            "/test/answer",
            new HttpEntity<>(
                new AnswerDto(question.getId(), "4")
            ),
            AnswerResultDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, correct.getStatusCode());
        Assertions.assertEquals(
            AnswerResultDto.Result.RIGHT, correct.getBody().getResult()
        );
        final var wrong = this.template.postForEntity(
            "/test/answer",
            new HttpEntity<>(
                new AnswerDto(question.getId(), "10")
            ),
            AnswerResultDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, wrong.getStatusCode());
        Assertions.assertEquals(
            AnswerResultDto.Result.WRONG, wrong.getBody().getResult()
        );
        Assertions.assertEquals("10", wrong.getBody().getProvided());
        Assertions.assertEquals("4", wrong.getBody().getCorrect());
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
    }
}

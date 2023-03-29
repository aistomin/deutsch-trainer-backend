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
package com.github.aistomin.deutsch.trainer.backend.services.impl;

import com.github.aistomin.deutsch.trainer.backend.controllers.test.AnswerDto;
import com.github.aistomin.deutsch.trainer.backend.controllers.test.AnswerResultDto;
import com.github.aistomin.deutsch.trainer.backend.controllers.test.QuestionDto;
import com.github.aistomin.deutsch.trainer.backend.controllers.test.TestDto;
import com.github.aistomin.deutsch.trainer.backend.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Test's service's implementation.
 *
 * @since 0.1
 */
@Service
@Slf4j
public final class TestServiceImpl implements TestService {

    /**
     * Temporary questions storage.
     */
    private final Map<String, String> questions = new HashMap<>() {{
        put("1 + 1", "2");
        put("2 X 2", "4");
        put("7 * 8", "56");
    }};

    @Override
    public TestDto start() {
        log.info("Starting a new test .....");
        final Set<QuestionDto> items = this.loadQuestions();
        final var test = new TestDto(
            1L,
            items,
            new Date()
        );
        log.info("New test has been started. Test ID: {}.", test.getId());
        return test;
    }

    /**
     * Load questions.
     *
     * @return Questions.
     */
    private Set<QuestionDto> loadQuestions() {
        final Set<QuestionDto> items = new HashSet<>();
        long i = 1;
        for (Map.Entry<String, String> question : this.questions.entrySet()) {
            items.add(
                new QuestionDto(i, question.getKey(), new Date())
            );
            i++;
        }
        return items;
    }

    @Override
    public AnswerResultDto answer(final AnswerDto answer) {
        final var question = this.loadQuestions()
            .stream()
            .filter(item ->
                Objects.equals(item.getId(), answer.getQuestionId())
            ).findFirst();
        final var provided = answer.getText();
        if (question.isPresent()) {
            final var correct = this.questions.get(question.get().getText());
            if (correct.equals(provided)) {
                return new AnswerResultDto(
                    answer.getQuestionId(),
                    AnswerResultDto.Result.RIGHT,
                    provided,
                    correct
                );
            } else {
                return new AnswerResultDto(
                    answer.getQuestionId(),
                    AnswerResultDto.Result.WRONG,
                    provided,
                    correct
                );
            }
        } else {
            return new AnswerResultDto(
                answer.getQuestionId(),
                AnswerResultDto.Result.NOT_FOUND,
                provided,
                null
            );
        }
    }
}

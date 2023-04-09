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
import com.github.aistomin.deutsch.trainer.backend.controllers.test.TestDto;
import com.github.aistomin.deutsch.trainer.backend.model.Question;
import com.github.aistomin.deutsch.trainer.backend.model.QuestionRepository;
import com.github.aistomin.deutsch.trainer.backend.model.Test;
import com.github.aistomin.deutsch.trainer.backend.model.TestRepository;
import com.github.aistomin.deutsch.trainer.backend.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashSet;

/**
 * Test's service's implementation.
 *
 * @since 0.1
 */
@Service
@Slf4j
public final class TestServiceImpl implements TestService {

    /**
     * Tests' repository.
     */
    private final TestRepository tests;

    /**
     * Questions' repository.
     */
    private final QuestionRepository questions;

    /**
     * Ctor.
     *
     * @param testRepository Tests' repository.
     * @param questionRepository Questions' repository.
     */
    public TestServiceImpl(
        final TestRepository testRepository,
        final QuestionRepository questionRepository
    ) {
        this.tests = testRepository;
        this.questions = questionRepository;
    }

    @Override
    public TestDto start() {
        log.info("Starting a new test .....");
        final var test = new Test();
        test.setDateCreated(new Date());
        this.tests.save(test);
        final var items = new HashSet<Question>();
        items.add(
            this.questions.save(
                new Question(null, test, "1 + 1", "2", new Date())
            )
        );
        items.add(
            this.questions.save(
                new Question(null, test, "2 X 2", "4", new Date())
            )
        );
        test.setQuestions(items);
        log.info("New test has been started. Test ID: {}.", test.getId());
        return new TestDto(this.tests.save(test));
    }

    @Override
    public AnswerResultDto answer(final AnswerDto answer) {
        final var question = this.questions.findById(answer.getQuestionId());
        final var provided = answer.getText();
        if (question.isPresent()) {
            final var correct = question.get().getAnswer();
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

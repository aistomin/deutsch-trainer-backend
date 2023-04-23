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
import com.github.aistomin.deutsch.trainer.backend.model.VocabularyItem;
import com.github.aistomin.deutsch.trainer.backend.services.TestService;
import com.github.aistomin.deutsch.trainer.backend.services.VocabularyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

/**
 * Test's service's implementation.
 *
 * @since 0.1
 */
@Service
@Slf4j
public final class TestServiceImpl implements TestService {

    /**
     * Count of the questions that we return to the user.
     */
    public static final int QUESTIONS_COUNT = 10;

    /**
     * Tests' repository.
     */
    private final TestRepository tests;

    /**
     * Vocabulary's service.
     */
    private final VocabularyService vocabulary;

    /**
     * Questions' repository.
     */
    private final QuestionRepository questions;

    /**
     * Ctor.
     *
     * @param testRepository     Tests' repository.
     * @param vocabularyService  Vocabulary's service.
     * @param questionRepository Questions' repository.
     */
    public TestServiceImpl(
        final TestRepository testRepository,
        final VocabularyService vocabularyService,
        final QuestionRepository questionRepository
    ) {
        this.tests = testRepository;
        this.vocabulary = vocabularyService;
        this.questions = questionRepository;
    }

    @Override
    public TestDto start() {
        log.info("Starting a new test .....");
        final var test = new Test();
        test.setDateCreated(new Date());
        this.tests.save(test);
        final var all = this.vocabulary.loadAll();
        final var items = new HashSet<Question>();
        if (all.size() > 0) {
            final var random = new Random();
            for (int i = 0; i < QUESTIONS_COUNT; i++) {
                final var item = all.get(random.nextInt(all.size()));
                final var ger2eng = random.nextBoolean();
                final var question = new Question();
                if (ger2eng) {
                    question.setText(item.getGerman());
                    question.setAnswer(item.getEnglish());
                } else {
                    question.setText(item.getEnglish());
                    question.setAnswer(item.getGerman());
                }
                question.setTest(test);
                question.setVocabularyItem(new VocabularyItem(item));
                question.setDateCreated(new Date());
                items.add(this.questions.save(question));
            }
        }
        test.setQuestions(items);
        log.info("New test has been started. Test ID: {}.", test.getId());
        return new TestDto(this.tests.save(test));
    }

    @Override
    public AnswerResultDto answer(final AnswerDto answer) {
        final var question = this.questions.findById(answer.getQuestionId());
        final var provided = answer.getText();
        if (question.isPresent()) {
            final var entity = question.get();
            final var correct = entity.getAnswer();
            if (correct.equals(provided)) {
                entity.setResult(Question.Result.RIGHT);
            } else {
                entity.setResult(Question.Result.WRONG);
            }
            this.questions.save(entity);
            return new AnswerResultDto(entity, provided);
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

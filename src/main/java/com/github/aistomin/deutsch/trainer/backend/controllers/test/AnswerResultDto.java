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

import com.github.aistomin.deutsch.trainer.backend.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Answer DTO.
 *
 * @since 0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class AnswerResultDto {

    /**
     * Question ID.
     */
    private Long questionId;

    /**
     * Answer's result.
     */
    private Result result;

    /**
     * Provided answer.
     */
    private String provided;

    /**
     * Correct answer.
     */
    private String correct;

    /**
     * Ctor.
     *
     * @param question Question.
     * @param answer   User's answer.
     */
    public AnswerResultDto(final Question question, final String answer) {
        this(
            question.getId(),
            Result.valueOf(question.getResult().name()),
            answer,
            question.getAnswer()
        );
    }

    /**
     * Possible results.
     */
    public enum Result {

        /**
         * The answer is correct.
         */
        RIGHT,

        /**
         * The answer is not correct.
         */
        WRONG,

        /**
         * The question is not found.
         */
        NOT_FOUND
    }
}

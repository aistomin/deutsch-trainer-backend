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

import com.github.aistomin.deutsch.trainer.backend.controllers.vocabulary.VocabularyItemDto;
import com.github.aistomin.deutsch.trainer.backend.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Question DTO.
 *
 * @since 0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class QuestionDto {

    /**
     * Question ID.
     */
    private Long id;

    /**
     * Question's text.
     */
    private String text;

    /**
     * Vocabulary item that the question belongs to.
     */
    private VocabularyItemDto vocabularyItem;

    /**
     * Date when the question was created.
     */
    private Date dateCreated;

    /**
     * Ctor.
     *
     * @param question Question.
     */
    public QuestionDto(final Question question) {
        this(
            question.getId(),
            question.getText(),
            question.getVocabularyItem() != null
                ? new VocabularyItemDto(question.getVocabularyItem()) : null,
            question.getDateCreated()
        );
    }
}

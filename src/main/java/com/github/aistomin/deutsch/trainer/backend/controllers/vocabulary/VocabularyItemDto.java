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
package com.github.aistomin.deutsch.trainer.backend.controllers.vocabulary;

import com.github.aistomin.deutsch.trainer.backend.controllers.user.UserDto;
import com.github.aistomin.deutsch.trainer.backend.model.VocabularyItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * Vocabulary item DTO.
 *
 * @since 0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class VocabularyItemDto {

    /**
     * Vocabulary item ID.
     */
    private Long id;

    /**
     * The item in German language.
     */
    private String german;

    /**
     * The item in English language.
     */
    private String english;

    /**
     * The example sentence or any other hint.
     */
    private String example;

    /**
     * The picture that helps to remember the item.
     */
    private String pictureUrl;

    /**
     * The user that this item belongs to.
     */
    private UserDto owner;

    /**
     * Date when the item was created.
     */
    private Date dateCreated;

    /**
     * Ctor.
     *
     * @param item Vocabulary item.
     */
    public VocabularyItemDto(final VocabularyItem item) {
        this(
            item.getId(),
            item.getGerman(),
            item.getEnglish(),
            item.getExample(),
            item.getPictureUrl(),
            item.getOwner() != null ? new UserDto(item.getOwner()) : null,
            item.getDateCreated()
        );
    }
}

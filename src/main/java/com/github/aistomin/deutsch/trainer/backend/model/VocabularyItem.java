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
package com.github.aistomin.deutsch.trainer.backend.model;

import com.github.aistomin.deutsch.trainer.backend.controllers.vocabulary.VocabularyItemDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

/**
 * Data object that stores vocabulary data.
 *
 * @since 0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public final class VocabularyItem {

    /**
     * Item ID.
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    /**
     * The item in German language.
     */
    @Column(nullable = false)
    private String german;

    /**
     * The item in English language.
     */
    @Column(nullable = false)
    private String english;

    /**
     * The example sentence or any other hint.
     */
    @Column
    private String example;

    /**
     * The picture that helps to remember the item.
     */
    @Column
    private String pictureUrl;

    /**
     * The user that this item belongs to.
     */
    @ManyToOne
    private User owner;

    /**
     * Date when the item was created.
     */
    @Column(nullable = false)
    private Date dateCreated;

    /**
     * Ctor.
     *
     * @param dto Vocabulary item DTO.
     */
    public VocabularyItem(final VocabularyItemDto dto) {
        this(
            dto.getId(),
            dto.getGerman(),
            dto.getEnglish(),
            dto.getExample(),
            dto.getPictureUrl(),
            dto.getOwner() != null ? new User(dto.getOwner()) : null,
            dto.getDateCreated()
        );
    }
}

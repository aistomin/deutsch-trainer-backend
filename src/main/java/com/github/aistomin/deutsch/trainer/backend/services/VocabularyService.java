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
package com.github.aistomin.deutsch.trainer.backend.services;

import com.github.aistomin.deutsch.trainer.backend.controllers.vocabulary.VocabularyItemDto;
import java.util.List;

/**
 * Vocabulary service.
 *
 * @since 0.1
 */
public interface VocabularyService {

    /**
     * Load all the items that exist in the vocabulary.
     *
     * @return List of items.
     */
    List<VocabularyItemDto> loadAll();

    /**
     * Create a vocabulary item.
     *
     * @param item The item that needs to be created.
     * @return Created item.
     */
    VocabularyItemDto create(VocabularyItemDto item);

    /**
     * Delete a vocabulary item.
     *
     * @param item Vocabulary item.
     */
    void delete(VocabularyItemDto item);

    /**
     * Find a vocabulary item by ID.
     *
     * @param id ID.
     * @return Found item or null if the item is not found.
     */
    VocabularyItemDto findItemById(Long id);

    /**
     * Update a vocabulary item.
     *
     * @param item The item that needs to be edited.
     * @return Updated item.
     */
    VocabularyItemDto update(VocabularyItemDto item);
}

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

import com.github.aistomin.deutsch.trainer.backend.services.VocabularyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

/**
 * Controller that provides client all the vocabulary related functionality.
 *
 * @since 0.1
 */
@RestController
@RequestMapping("/vocabulary")
public final class VocabularyController {

    /**
     * Vocabulary service.
     */
    private final VocabularyService vocabulary;

    /**
     * Ctor.
     *
     * @param service Vocabulary service.
     */
    public VocabularyController(final VocabularyService service) {
        this.vocabulary = service;
    }

    /**
     * Load all the items that exist in the vocabulary.
     *
     * @return List of items.
     */
    @GetMapping()
    public List<VocabularyItemDto> loadVocabulary() {
        return this.vocabulary.loadAll();
    }

    /**
     * Create a vocabulary item.
     *
     * @param item The item that needs to be created.
     * @return Created item.
     */
    @PostMapping()
    public ResponseEntity<VocabularyItemDto> create(
        @RequestBody final VocabularyItemDto item
    ) {
        return new ResponseEntity<>(
            this.vocabulary.create(item), HttpStatus.CREATED
        );
    }

    /**
     * Edit a vocabulary item.
     *
     * @param item The item that needs to be edited.
     * @return Updated item.
     */
    @PutMapping()
    public ResponseEntity<VocabularyItemDto> edit(
        @RequestBody final VocabularyItemDto item
    ) {
        return new ResponseEntity<>(
            this.vocabulary.update(item), HttpStatus.OK
        );
    }

    /**
     * Delete a vocabulary item.
     *
     * @param id Vocabulary item ID.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id) {
        final var item = this.vocabulary.findItemById(id);
        if (item == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Item with ID = %d not found.", id)
            );
        }
        this.vocabulary.delete(item);
    }
}

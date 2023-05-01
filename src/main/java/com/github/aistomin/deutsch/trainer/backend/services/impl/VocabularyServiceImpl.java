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

import com.github.aistomin.deutsch.trainer.backend.controllers.vocabulary.VocabularyItemDto;
import com.github.aistomin.deutsch.trainer.backend.model.VocabularyItem;
import com.github.aistomin.deutsch.trainer.backend.model.VocabularyItemRepository;
import com.github.aistomin.deutsch.trainer.backend.services.UserService;
import com.github.aistomin.deutsch.trainer.backend.services.VocabularyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Vocabulary service's implementation.
 *
 * @since 0.1
 */
@Service
@Slf4j
public final class VocabularyServiceImpl implements VocabularyService {

    /**
     * Vocabulary's repository.
     */
    private final VocabularyItemRepository vocabulary;

    /**
     * User's service.
     */
    private final UserService users;

    /**
     * Ctor.
     *
     * @param repo        Vocabulary's repository.
     * @param userService User's service.
     */
    public VocabularyServiceImpl(
        final VocabularyItemRepository repo,
        final UserService userService
    ) {
        this.vocabulary = repo;
        this.users = userService;
    }

    @Override
    public List<VocabularyItemDto> loadAll() {
        return this.vocabulary
            .findAllByOwner(this.users.defaultUser().toEntity())
            .stream()
            .map(VocabularyItemDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public VocabularyItemDto create(final VocabularyItemDto item) {
        final var entity = new VocabularyItem(item);
        entity.setDateCreated(new Date());
        entity.setOwner(this.users.defaultUser().toEntity());
        return new VocabularyItemDto(this.vocabulary.save(entity));
    }

    @Override
    public void delete(final VocabularyItemDto item) {
        this.vocabulary.delete(new VocabularyItem(item));
    }

    @Override
    public VocabularyItemDto findItemById(final Long id) {
        return this.vocabulary.findById(id)
            .map(VocabularyItemDto::new)
            .orElse(null);
    }

    @Override
    public VocabularyItemDto update(final VocabularyItemDto item) {
        return new VocabularyItemDto(
            this.vocabulary.save(new VocabularyItem(item))
        );
    }
}

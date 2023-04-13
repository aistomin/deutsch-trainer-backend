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
     * Repository.
     */
    private final VocabularyItemRepository vocabulary;

    /**
     * Ctor.
     *
     * @param repo Repository.
     */
    public VocabularyServiceImpl(final VocabularyItemRepository repo) {
        this.vocabulary = repo;
    }

    @Override
    public List<VocabularyItemDto> loadAll() {
        return this.vocabulary.findAll()
                .stream()
                .map(VocabularyItemDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public VocabularyItemDto create(final VocabularyItemDto item) {
        final var entity = new VocabularyItem(item);
        entity.setDateCreated(new Date());
        return new VocabularyItemDto(this.vocabulary.save(entity));
    }

    @Override
    public void delete(final Long id) {
        this.vocabulary.delete(this.vocabulary.findById(id).get());
    }
}

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
import com.github.aistomin.deutsch.trainer.backend.services.UserService;
import com.github.aistomin.deutsch.trainer.backend.services.VocabularyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Vocabulary service's implementation.
 *
 * @since 0.1
 */
@Service
@Slf4j
public final class VocabularyServiceImpl implements VocabularyService {

    /**
     * User service.
     */
    private final UserService users;

    /**
     * Ctor.
     *
     * @param userService User service.
     */
    public VocabularyServiceImpl(final UserService userService) {
        this.users = userService;
    }

    @Override
    public List<VocabularyItemDto> loadAll() {
        final var user = users.defaultUser();
        return Arrays.asList(
            new VocabularyItemDto(
                1L, "die Katze", "the cat", user, new Date()
            ),
            new VocabularyItemDto(
                2L, "der Hund", "the dog", user, new Date()
            )
        );
    }
}

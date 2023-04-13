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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import java.util.List;

/**
 * Test for {@link VocabularyController}.
 *
 * @since 0.1
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
final class VocabularyControllerTest {

    /**
     * Test REST template.
     */
    @Autowired
    private TestRestTemplate template;

    /**
     * Check that we can correctly create and get items from the vocabulary.
     */
    @Test
    void testCreate() {
        final var empty = this.template.getForEntity(
            "/vocabulary", List.class
        ).getBody();
        Assertions.assertNotNull(empty);
        Assertions.assertEquals(0, empty.size());
        final var cat = new VocabularyItemDto(
            null, "die Katze", "the cat", null
        );
        final var createdCat = this.template.postForEntity(
            "/vocabulary",
            cat,
            VocabularyItemDto.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, createdCat.getStatusCode());
        final var body = createdCat.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(cat.getGerman(), body.getGerman());
        Assertions.assertEquals(cat.getEnglish(), body.getEnglish());
        final var dog = new VocabularyItemDto(
            null, "der Hund", "the dog", null
        );
        final var createdDog = this.template.postForEntity(
            "/vocabulary",
            dog,
            VocabularyItemDto.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, createdDog.getStatusCode());
        final var items = this.template.getForEntity(
            "/vocabulary", List.class
        ).getBody();
        Assertions.assertNotNull(items);
        Assertions.assertEquals(2, items.size());
        template.delete(
            String.format("/vocabulary/%d", createdDog.getBody().getId())
        );
        final var itemsAfterDeletion = this.template.getForEntity(
            "/vocabulary", List.class
        ).getBody();
        Assertions.assertNotNull(itemsAfterDeletion);
        Assertions.assertEquals(1, itemsAfterDeletion.size());
    }
}

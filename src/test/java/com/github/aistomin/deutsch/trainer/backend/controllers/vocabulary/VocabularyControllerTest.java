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

import com.github.aistomin.deutsch.trainer.backend.controllers.utils.TestUserHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Test user's holder.
     */
    @Autowired
    private TestUserHolder holder;

    /**
     * Check that we can correctly create item in the vocabulary.
     */
    @Test
    void testCreate() {
        final var cat = new VocabularyItemDto(
            null,
            "die Katze",
            "the cat",
            "Die Katze frisst Mäuse.",
            "https://cats.com/picture1.jpg",
            this.holder.defaultUser(),
            null
        );
        final var created = this.template.postForEntity(
            "/vocabulary",
            cat,
            VocabularyItemDto.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, created.getStatusCode());
        final var body = created.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertNotNull(body.getId());
        Assertions.assertEquals(cat.getGerman(), body.getGerman());
        Assertions.assertEquals(cat.getEnglish(), body.getEnglish());
        Assertions.assertEquals(cat.getExample(), body.getExample());
        Assertions.assertEquals(cat.getPictureUrl(), body.getPictureUrl());
        Assertions.assertNotNull(body.getDateCreated());
        Assertions.assertNotNull(body.getOwner());
    }

    /**
     * Check that we can correctly edit any item in the vocabulary.
     */
    @Test
    void testEdit() {
        final var cat = new VocabularyItemDto(
            null,
            "die Maus",
            "the mouse",
            null,
            null,
            this.holder.defaultUser(),
            null
        );
        final var created = this.template.postForEntity(
            "/vocabulary",
            cat,
            VocabularyItemDto.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, created.getStatusCode());
        final var edited = created.getBody();
        edited.setGerman("die Maus, -\"e");
        edited.setEnglish("the mouse, -s");
        final var response = template.exchange(
            "/vocabulary",
            HttpMethod.PUT,
            new HttpEntity<>(edited),
            VocabularyItemDto.class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        final var body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(edited.getId(), body.getId());
        Assertions.assertEquals(edited.getGerman(), body.getGerman());
        Assertions.assertEquals(edited.getEnglish(), body.getEnglish());
    }

    /**
     * Check that we can correctly load the items from the vocabulary.
     */
    @Test
    void testLoadVocabulary() {
        final var id1 = this.template.postForEntity(
            "/vocabulary",
            new VocabularyItemDto(
                null,
                "die Katze",
                "the cat",
                null,
                null,
                this.holder.defaultUser(),
                null
            ),
            VocabularyItemDto.class
        ).getBody().getId();
        final var id2 = this.template.postForEntity(
            "/vocabulary",
            new VocabularyItemDto(
                null,
                "der Hund",
                "the dog",
                null,
                null,
                this.holder.defaultUser(),
                null
            ),
            VocabularyItemDto.class
        ).getBody().getId();
        final var items = (List<Map>) this.template.getForEntity(
            "/vocabulary", List.class
        ).getBody();
        Assertions.assertNotNull(items);
        final var ids = items.stream()
            .map(map -> Long.parseLong(map.get("id").toString()))
            .toList();
        Assertions.assertTrue(ids.contains(id1));
        Assertions.assertTrue(ids.contains(id2));
    }

    /**
     * Check that we correctly delete the item from the library.
     */
    @Test
    void testDelete() {
        final var created = this.template.postForEntity(
            "/vocabulary",
            new VocabularyItemDto(
                null,
                "der Hund",
                "the dog",
                null,
                null,
                this.holder.defaultUser(),
                null
            ),
            VocabularyItemDto.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, created.getStatusCode());
        final var before = this.template.getForEntity(
            "/vocabulary", List.class
        ).getBody();
        Assertions.assertNotNull(before);
        final var deleted = template.exchange(
            String.format("/vocabulary/%d", created.getBody().getId()),
            HttpMethod.DELETE,
            new HttpEntity<>(new HashMap<String, String>()),
            Void.class
        );
        Assertions.assertEquals(
            HttpStatus.OK, deleted.getStatusCode()
        );
        final var after = this.template.getForEntity(
            "/vocabulary", List.class
        ).getBody();
        Assertions.assertNotNull(after);
        Assertions.assertEquals(before.size() - 1, after.size());
        final ResponseEntity<Void> notFound = template.exchange(
            "/vocabulary/666",
            HttpMethod.DELETE,
            new HttpEntity<>(new HashMap<String, String>()),
            Void.class
        );
        Assertions.assertEquals(
            HttpStatus.NOT_FOUND, notFound.getStatusCode()
        );
    }
}

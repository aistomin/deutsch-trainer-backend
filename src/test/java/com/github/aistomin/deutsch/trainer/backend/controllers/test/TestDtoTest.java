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

import com.github.aistomin.deutsch.trainer.backend.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Tests for {@link TestDto}.
 */
final class TestDtoTest {

    /**
     * Check that we correctly convert question to the DTO.
     */
    @Test
    void testConvert() {
        final var random = new Random();
        final var test = new com.github.aistomin.deutsch.trainer.backend.model.Test(
            random.nextLong(),
            new HashSet<>(
                Arrays.asList(
                    new Question(
                        random.nextLong(),
                        null,
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        new Date()
                    ),
                    new Question(
                        random.nextLong(),
                        null,
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        new Date()
                    ),
                    new Question(
                        random.nextLong(),
                        null,
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        new Date()
                    )
                )
            ),
            new Date()
        );
        final var dto = new TestDto(test);
        Assertions.assertEquals(test.getId(), dto.getId());
        Assertions.assertEquals(
            test.getQuestions().size(), dto.getQuestions().size()
        );
        Assertions.assertEquals(test.getDateCreated(), dto.getDateCreated());
    }
}

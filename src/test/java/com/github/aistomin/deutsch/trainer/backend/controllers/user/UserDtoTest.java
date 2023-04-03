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
package com.github.aistomin.deutsch.trainer.backend.controllers.user;

import com.github.aistomin.deutsch.trainer.backend.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.UUID;

/**
 * Tests for {@link UserDto}.
 */
final class UserDtoTest {

    /**
     * Check that we correctly convert user to the DTO.
     */
    @Test
    void testConvert() {
        final var user = new User(
            new Random().nextLong(), UUID.randomUUID().toString()
        );
        final var dto = new UserDto(user);
        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getUsername(), dto.getUsername());
    }
}

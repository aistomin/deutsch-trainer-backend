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

import com.github.aistomin.deutsch.trainer.backend.controllers.user.UserDto;
import com.github.aistomin.deutsch.trainer.backend.model.Test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Test DTO.
 *
 * @since 0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class TestDto {

    /**
     * Test ID.
     */
    private Long id;

    /**
     * Test's questions.
     */
    private Set<QuestionDto> questions;

    /**
     * User who took the test.
     */
    private UserDto user;

    /**
     * Date when the test was created.
     */
    private Date dateCreated;

    /**
     * Ctor.
     *
     * @param test Test.
     */
    public TestDto(final Test test) {
        this(
            test.getId(),
            test.getQuestions()
                .stream()
                .map(QuestionDto::new).collect(Collectors.toSet()),
            new UserDto(test.getUser()),
            test.getDateCreated()
        );
    }
}

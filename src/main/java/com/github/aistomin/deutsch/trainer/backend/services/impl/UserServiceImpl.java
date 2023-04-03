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

import com.github.aistomin.deutsch.trainer.backend.controllers.user.UserDto;
import com.github.aistomin.deutsch.trainer.backend.model.UserRepository;
import com.github.aistomin.deutsch.trainer.backend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Test's service's implementation.
 *
 * @since 0.1
 */
@Service
@Slf4j
public final class UserServiceImpl implements UserService {

    /**
     * User's repository.
     */
    private final UserRepository users;

    /**
     * Ctor.
     *
     * @param repository User's repository.
     */
    public UserServiceImpl(final UserRepository repository) {
        this.users = repository;
    }

    @Override
    public UserDto defaultUser() {
        return new UserDto(this.users.findByUsername("andrej"));
    }
}

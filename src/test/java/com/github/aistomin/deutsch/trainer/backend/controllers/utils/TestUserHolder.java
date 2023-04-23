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
package com.github.aistomin.deutsch.trainer.backend.controllers.utils;

import com.github.aistomin.deutsch.trainer.backend.controllers.user.UserDto;
import com.github.aistomin.deutsch.trainer.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class contains some logic related to the test user.
 */
@Component
public final class TestUserHolder {

    /**
     * User's service.
     */
    @Autowired
    private UserService users;

    /**
     * Default test user.
     *
     * @return Test user.
     */
    public UserDto defaultUser() {
        return this.users.defaultUser();
    }
}

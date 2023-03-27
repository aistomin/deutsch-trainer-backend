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

import com.github.aistomin.deutsch.trainer.backend.controllers.test.TestDto;
import com.github.aistomin.deutsch.trainer.backend.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * Test's service's implementation.
 *
 * @since 0.1
 */
@Service
@Slf4j
public final class TestServiceImpl implements TestService {

    @Override
    public TestDto start() {
        log.info("Starting a new test .....");
        final var test = new TestDto(1L, new Date());
        log.info("New test has been started. Test ID: {}.", test.getId());
        return test;
    }
}

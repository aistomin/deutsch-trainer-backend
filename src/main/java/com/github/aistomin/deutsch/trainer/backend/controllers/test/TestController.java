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

import com.github.aistomin.deutsch.trainer.backend.services.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that provides client all the test related functionality.
 *
 * @since 0.1
 */
@RestController
@RequestMapping("/test")
public final class TestController {

    /**
     * Test service.
     */
    private final TestService test;

    /**
     * Ctor.
     *
     * @param service Test service.
     */
    public TestController(final TestService service) {
        this.test = service;
    }

    /**
     * Start test.
     *
     * @return Users.
     */
    @GetMapping("/start")
    public TestDto start() {
        return this.test.start();
    }

    /**
     * Answer the test's question.
     *
     * @param answer User's answer.
     * @return Answer result.
     */
    @PostMapping("/answer")
    public AnswerResultDto answer(@RequestBody final AnswerDto answer) {
        return this.test.answer(answer);
    }
}

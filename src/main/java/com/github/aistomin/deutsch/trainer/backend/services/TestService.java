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
package com.github.aistomin.deutsch.trainer.backend.services;

import com.github.aistomin.deutsch.trainer.backend.controllers.test.AnswerDto;
import com.github.aistomin.deutsch.trainer.backend.controllers.test.AnswerResultDto;
import com.github.aistomin.deutsch.trainer.backend.controllers.test.TestDto;
import com.github.aistomin.deutsch.trainer.backend.controllers.test.UserStatisticItemDto;
import java.util.List;

/**
 * Test's service.
 *
 * @since 0.1
 */
public interface TestService {

    /**
     * Start a test.
     *
     * @return Test.
     */
    TestDto start();

    /**
     * Answer the test's question.
     *
     * @param answer User's answer.
     * @return Answer result.
     */
    AnswerResultDto answer(AnswerDto answer);

    /**
     * User's tests' statistic.
     *
     * @return Statistics.
     */
    List<UserStatisticItemDto> statistics();
}

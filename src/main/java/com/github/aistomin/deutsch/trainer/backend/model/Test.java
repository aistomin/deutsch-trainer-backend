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
package com.github.aistomin.deutsch.trainer.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Set;

/**
 * Data object that stores test's data.
 *
 * @since 0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dt_test")
public final class Test {

    /**
     * Test ID.
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    /**
     * Questions.
     */
    @OneToMany
    private Set<Question> questions;

    /**
     * Test's status.
     */
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    /**
     * User who took the test.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    /**
     * Date when the test was created.
     */
    @Column(nullable = false)
    private Date dateCreated;

    /**
     * The test's status.
     */
    public enum Status {

        /**
         * User is still answering.
         */
        ACTIVE,

        /**
         * The test is completed.
         */
        COMPLETED
    }
}

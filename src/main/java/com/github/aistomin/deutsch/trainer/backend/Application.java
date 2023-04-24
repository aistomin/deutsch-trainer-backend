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
package com.github.aistomin.deutsch.trainer.backend;

import com.github.aistomin.deutsch.trainer.backend.model.User;
import com.github.aistomin.deutsch.trainer.backend.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Application.
 */
@SpringBootApplication
@Slf4j
public class Application {

    /**
     * User repository.
     */
    private final UserRepository users;

    /**
     * Ctor.
     *
     * @param repository User repository.
     */
    public Application(final UserRepository repository) {
        this.users = repository;
    }

    /**
     * Application's entry point.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Command line runner.
     *
     * @param ctx Application context.
     * @return Runner.
     */
    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        return args -> {
            log.info("Application is starting .....");
            final var andrej = "andrej";
            if (this.users.findByUsername(andrej) == null) {
                log.info("Admin user does not exist, let's create it .....");
                this.users.save(new User(null, andrej));
                log.info("Admin user is created. Username: {}", andrej);
            }
            log.info("Application is up and running.");
        };
    }

    /**
     * Create CORS configurer bean.
     *
     * @return CORS configurer bean.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry
                    .addMapping("/**")
                    .allowedOrigins("http://localhost:5173");
            }
        };
    }
}

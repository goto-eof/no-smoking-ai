package com.andreidodu.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.andreidodu.server.entity", "com.andreidodu.server.repository"})
public class DatabaseConfiguration {
}

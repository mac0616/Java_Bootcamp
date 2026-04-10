package com.wanted.project.global.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.wanted.project")     // JPA 사용 가능하도록
@EntityScan(basePackages = "com.wanted.project")                // Entity scan 가능하도록
public class JpaConfig {
}

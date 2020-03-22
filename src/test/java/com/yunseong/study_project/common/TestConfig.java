package com.yunseong.study_project.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@Profile("test")
@EnableJpaAuditing
@TestConfiguration
public class TestConfig {

    @Bean
    public AuditorAware auditorAware() {
        return () -> Optional.of((long)(Math.random()*10000));
    }
}

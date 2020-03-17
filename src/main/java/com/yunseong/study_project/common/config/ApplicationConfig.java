package com.yunseong.study_project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class ApplicationConfig {

    @Bean
    public AuditorAware auditorAware() {
        return () -> Optional.of(UUID.randomUUID());
    }
}

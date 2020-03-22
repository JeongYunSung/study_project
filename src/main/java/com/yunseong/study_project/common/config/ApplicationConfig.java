package com.yunseong.study_project.common.config;

import com.yunseong.study_project.common.domain.CustomUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Profile(value = { "dev" })
@Configuration
@EnableJpaAuditing
public class ApplicationConfig {

    @Bean
    public AuditorAware auditorAware() {
        return () -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(principal);
            if (principal instanceof CustomUser) {
                CustomUser user = (CustomUser) principal;
                return Optional.of(user.getId());
            }
            return Optional.of(0L);
        };
    }
}

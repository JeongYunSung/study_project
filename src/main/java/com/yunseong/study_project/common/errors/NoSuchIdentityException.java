package com.yunseong.study_project.common.errors;

import lombok.Getter;

@Getter
public class NoSuchIdentityException extends RuntimeException {

    private String entityName;
    private Long id;

    public NoSuchIdentityException(String entityName, Long id) {
        super();
        this.entityName = entityName;
        this.id = id;
    }
}

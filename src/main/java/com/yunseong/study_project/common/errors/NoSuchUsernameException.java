package com.yunseong.study_project.common.errors;

import lombok.Getter;

@Getter
public class NoSuchUsernameException extends RuntimeException {

    private String entityName;
    private String username;

    public NoSuchUsernameException(String entityName, String username) {
        this.entityName = entityName;
        this.username = username;
    }
}

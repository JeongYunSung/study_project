package com.yunseong.study_project.member.ui.validator;

import lombok.Getter;

@Getter
public class NoSuchMyItemException extends RuntimeException {

    private String username;
    private Long id;

    public NoSuchMyItemException(String username, Long id) {
        super();
        this.username = username;
        this.id = id;
    }
}

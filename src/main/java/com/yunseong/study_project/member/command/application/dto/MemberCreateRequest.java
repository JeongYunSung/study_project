package com.yunseong.study_project.member.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {

    private String username;
    private String nickname;
    private String password;
}

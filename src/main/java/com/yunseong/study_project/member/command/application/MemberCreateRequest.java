package com.yunseong.study_project.member.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MemberCreateRequest {

    private String username;
    private String nickname;
    private String password;
}

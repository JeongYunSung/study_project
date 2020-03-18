package com.yunseong.study_project.member.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MemberUpdateRequest {

    private String nickname;
    private String password;
}
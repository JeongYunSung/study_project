package com.yunseong.study_project.member.query.application.dto;

import com.yunseong.study_project.member.command.domain.Grade;
import com.yunseong.study_project.member.command.domain.Member;
import lombok.Getter;

@Getter
public class MemberInfoResponse {

    private String username;
    private String nickname;
    private long money;
    private Grade grade;

    public MemberInfoResponse(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.money = member.getMoney();
        this.grade = member.getGrade();
    }
}

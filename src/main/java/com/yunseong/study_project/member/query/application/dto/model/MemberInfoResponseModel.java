package com.yunseong.study_project.member.query.application.dto.model;

import com.yunseong.study_project.member.query.application.dto.MemberInfoResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class MemberInfoResponseModel extends EntityModel<MemberInfoResponse> {

    public MemberInfoResponseModel(MemberInfoResponse content, Link... links) {
        super(content, links);
    }
}

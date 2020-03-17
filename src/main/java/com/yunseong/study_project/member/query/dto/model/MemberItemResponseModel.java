package com.yunseong.study_project.member.query.dto.model;

import com.yunseong.study_project.member.query.dto.MemberItemResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class MemberItemResponseModel extends EntityModel<MemberItemResponse> {

    public MemberItemResponseModel(MemberItemResponse content, Link... links) {
        super(content, links);
    }
}

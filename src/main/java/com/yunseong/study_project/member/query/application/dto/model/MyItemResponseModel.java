package com.yunseong.study_project.member.query.application.dto.model;

import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

@Getter
public class MyItemResponseModel extends EntityModel<MyItemResponse> {

    public MyItemResponseModel(MyItemResponse content, Link... links) {
        super(content, links);
    }
}

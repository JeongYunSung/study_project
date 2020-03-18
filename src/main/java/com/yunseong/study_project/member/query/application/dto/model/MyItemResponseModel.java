package com.yunseong.study_project.member.query.application.dto.model;

import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;

@Getter
public class MyItemResponseModel extends EntityModel {

    private MyItemResponse myItemResponse;

    public MyItemResponseModel(MyItemResponse myItemResponse) {
        this.myItemResponse = myItemResponse;
    }
}

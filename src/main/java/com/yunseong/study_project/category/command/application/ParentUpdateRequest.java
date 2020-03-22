package com.yunseong.study_project.category.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ParentUpdateRequest {

    private Long parentId;
    private Long myId;
}

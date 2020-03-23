package com.yunseong.study_project.product.query.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSearchCondition {

    private String productName;
    private String content;
    private String categoryName;
}

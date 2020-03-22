package com.yunseong.study_project.product.query.application.dto;

import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

@Getter
public class ProductResponseModel extends EntityModel<ProductResponse> {

    public ProductResponseModel(ProductResponse content, Link... links) {
        super(content, links);
    }
}

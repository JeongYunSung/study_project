package com.yunseong.study_project.category.query.model;

import com.yunseong.study_project.category.query.CategoryResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class CategoryResponseModel extends EntityModel<CategoryResponse> {

    public CategoryResponseModel(CategoryResponse content, Link... links) {
        super(content, links);
    }
}

package com.yunseong.study_project.category.query;

import com.querydsl.core.annotations.QueryProjection;
import com.yunseong.study_project.category.command.domain.Category;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryResponse {

    private Long createId;
    private Long id;
    private String categoryName;
    private Long parentId;
    private String parentName;
    private List<Long> childName;

    @QueryProjection
    public CategoryResponse(Category category) {
        this.createId = category.getCreatdBy();
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        if (category.getParent() != null) {
            this.parentId = category.getParent().getId();
            this.parentName = category.getParent().getCategoryName();
        }
        this.childName = category.getChildList().stream().map(Category::getId).collect(Collectors.toList());
    }
}

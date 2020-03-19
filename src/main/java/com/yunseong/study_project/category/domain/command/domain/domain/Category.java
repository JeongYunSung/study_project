package com.yunseong.study_project.category.domain.command.domain.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "category_id"))
public class Category extends BaseUserEntity {

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_name")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> childList = new ArrayList<>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public void addSubCategory(Category category) {
        this.getChildList().add(category);
        this.parent = this;
    }
}

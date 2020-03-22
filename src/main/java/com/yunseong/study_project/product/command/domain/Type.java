package com.yunseong.study_project.product.command.domain;

import com.yunseong.study_project.category.command.domain.Category;
import com.yunseong.study_project.common.domain.BaseUserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "type_id"))
public class Type extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_name")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_name")
    private Category type;

    public Type(Category type) {
        this.type = type;
    }

    void setProduct(Product product) {
        this.product = product;
    }
}

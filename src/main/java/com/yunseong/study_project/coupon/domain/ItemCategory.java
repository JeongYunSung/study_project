package com.yunseong.study_project.coupon.domain;

import com.yunseong.study_project.category.domain.command.domain.domain.Category;
import com.yunseong.study_project.common.domain.BaseUserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "itemcategory_id"))
public class ItemCategory extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_name")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_name")
    private Category category;

    public ItemCategory(Category category) {
        this.category = category;
    }

    void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}

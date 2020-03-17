package com.yunseong.study_project.coupon.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "coupon_id"))
public class Coupon extends BaseUserEntity {

    private String couponName;

    @OneToMany(mappedBy = "coupon")
    private List<Owner> owners = new ArrayList<>();

    @OneToMany(mappedBy = "coupon")
    private List<ItemCategory> categories = new ArrayList<>();

    public Coupon(String couponName) {
        this.couponName = couponName;
    }

    public void addCategory(ItemCategory category) {
        this.getCategories().add(category);
        category.setCoupon(this);
    }

    public void addOwner(Owner owner) {
        this.getOwners().add(owner);
        owner.setCoupon(this);
    }
}

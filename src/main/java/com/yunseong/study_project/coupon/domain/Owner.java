package com.yunseong.study_project.coupon.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.member.command.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "owner_id"))
public class Owner extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_name")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_name")
    private Coupon coupon;

    public Owner(Member member) {
        this.member = member;
    }

    void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}

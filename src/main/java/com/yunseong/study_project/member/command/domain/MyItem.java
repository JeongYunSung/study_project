package com.yunseong.study_project.member.command.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.product.command.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "myitem_id"))
public class MyItem extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_name")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_name")
    private Product product;

    public MyItem(Product product) {
        this.product = product;
    }

    void setMember(Member member) {
        this.member = member;
    }
}

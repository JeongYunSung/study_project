package com.yunseong.study_project.review.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.product.command.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "review_id"))
public class Review extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_name")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_name")
    private Product target;

    private String comment;

    public Review(Member writer, String comment) {
        this.writer = writer;
        this.comment = comment;
    }

    public void setProduct(Product product) {
        this.target = product;
    }

    public void changeComment(String comment) {
        this.comment = comment;
    }
}

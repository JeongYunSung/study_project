package com.yunseong.study_project.product.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.review.domain.Review;
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
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
public class Product extends BaseUserEntity {

    private String productName;

    @OneToMany(mappedBy = "product")
    private List<Type> types = new ArrayList<>();

    @OneToMany(mappedBy = "target")
    private List<Review> reviews = new ArrayList<>();

    private String content;

    private int amount;

    private long price;

    public void addType(Type type) {
        this.getTypes().add(type);
        type.setProduct(this);
    }

    public void addReview(Review review) {
        this.getReviews().add(review);
        review.setProduct(this);
    }
}

package com.yunseong.study_project.product.command.domain;

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
import java.util.stream.Stream;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
public class Product extends BaseUserEntity {

    private String productName;

    @OneToMany(mappedBy = "product")
    private List<Type> types = new ArrayList<>();

    private String content;

    private long price;

    public Product(String productName, String content, long price, List<Type> list) {
        this.productName = productName;
        this.content = content;
        this.price = price;
        this.types.addAll(list);
    }

    public void changeName(String name) {
        this.productName = name;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changePrice(long price) {
        this.price = price;
    }

    public void addType(Type type) {
        this.getTypes().add(type);
        type.setProduct(this);
    }
}

package com.yunseong.study_project.order.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "orderproduct_id"))
public class OrderProduct extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_name")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_name")
    private Product product;

    public OrderProduct(Product product) {
        this.product = product;
    }

    void setOrder(Order order) {
        this.order = order;
    }
}

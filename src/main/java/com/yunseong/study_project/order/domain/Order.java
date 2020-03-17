package com.yunseong.study_project.order.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.member.command.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "order_id"))
public class Order extends BaseUserEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderer_name")
    private Member orderer;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProductList = new ArrayList<>();

    public void addProduct(OrderProduct product) {
        this.getOrderProductList().add(product);
        product.setOrder(this);
    }

    @Override
    public void delete() {
        super.delete();
        this.getOrderProductList().forEach(OrderProduct::delete);
    }
}

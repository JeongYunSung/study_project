package com.yunseong.study_project.payment.domain;

import com.yunseong.study_project.common.domain.BaseUserEntity;
import com.yunseong.study_project.order.domain.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "payment_id"))
public class Payment extends BaseUserEntity {

    private boolean payment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_name")
    private Order order;
}

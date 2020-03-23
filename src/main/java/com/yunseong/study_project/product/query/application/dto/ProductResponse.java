package com.yunseong.study_project.product.query.application.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yunseong.study_project.product.command.domain.Product;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductResponse {

    private Long productId;
    private Long createId;
    private String productName;
    private String content;
    private List<Long> categoryIdList;
    private long price;

    @QueryProjection
    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.createId = product.getCreatdBy();
        this.productName = product.getProductName();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.categoryIdList = product.getTypes().stream().map(type -> type.getType().getId()).collect(Collectors.toList());
    }
}

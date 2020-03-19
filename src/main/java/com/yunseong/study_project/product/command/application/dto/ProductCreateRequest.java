package com.yunseong.study_project.product.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductCreateRequest {

    private String productName;
    private String content;
    private List<Long> categoryIdList;
    private int amount;
    private long price;
}

package com.yunseong.study_project.product.ui.validator;

import com.yunseong.study_project.product.command.application.ProductRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductRequest request = (ProductRequest) target;
        if (!StringUtils.hasText(request.getProductName())) {
            errors.rejectValue("productName", "required", "productName is required");
        }
        if (!StringUtils.hasText(request.getContent())) {
            errors.rejectValue("content", "required", "Content is required");
        }
        if (request.getPrice() < 0) {
            errors.rejectValue("price", "wrongValue", "Price can't be negative");
        }
        if (request.getCategoryIdList() == null || request.getCategoryIdList().size() == 0) {
            errors.rejectValue("category", "required", "Category is required");
        }
    }
}

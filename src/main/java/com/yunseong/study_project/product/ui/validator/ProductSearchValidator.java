package com.yunseong.study_project.product.ui.validator;

import com.yunseong.study_project.product.query.application.dto.ProductSearchCondition;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.util.StringUtils.hasText;

@Component
public class ProductSearchValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductSearchCondition.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductSearchCondition condition = (ProductSearchCondition) target;

        if(!(hasText(condition.getProductName()) || hasText(condition.getContent()) || hasText(condition.getCategoryName()))) {
            errors.reject("required", "SearchCondition is not null");
        }
    }
}

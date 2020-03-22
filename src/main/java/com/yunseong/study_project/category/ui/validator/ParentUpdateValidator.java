package com.yunseong.study_project.category.ui.validator;

import com.yunseong.study_project.category.command.application.ParentUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ParentUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ParentUpdateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ParentUpdateRequest request = (ParentUpdateRequest) target;
        if (request.getParentId() <= 0) {
            errors.rejectValue("parentId", "required", "ParentId is required");
        }
        if (request.getChildId() <= 0) {
            errors.rejectValue("childId", "required", "ChildId is required");
        }
    }
}

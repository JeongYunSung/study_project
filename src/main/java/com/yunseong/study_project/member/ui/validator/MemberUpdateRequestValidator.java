package com.yunseong.study_project.member.ui.validator;

import com.yunseong.study_project.member.command.application.dto.MemberUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberUpdateRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberUpdateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberUpdateRequest memberUpdateRequest = (MemberUpdateRequest) target;

        if(!StringUtils.hasText(memberUpdateRequest.getPassword())) {
            errors.rejectValue("password", "required", "Password is required");
        }
        if(!StringUtils.hasText(memberUpdateRequest.getNickname())) {
            errors.rejectValue("nickname", "required", "Nickname is required");
        }
    }
}

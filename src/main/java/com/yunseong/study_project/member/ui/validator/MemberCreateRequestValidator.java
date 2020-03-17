package com.yunseong.study_project.member.ui.validator;

import com.yunseong.study_project.member.command.application.MemberCreateRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberCreateRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberCreateRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberCreateRequest memberRequest = (MemberCreateRequest) target;

        if(!StringUtils.hasText(memberRequest.getUsername())) {
            errors.rejectValue("username", "required", "username is required");
        }
        if(!StringUtils.hasText(memberRequest.getPassword())) {
            errors.rejectValue("password", "required", "password is required");
        }
        if(!StringUtils.hasText(memberRequest.getNickname())) {
            errors.rejectValue("nickname", "required", "nickname is required");
        }
    }
}

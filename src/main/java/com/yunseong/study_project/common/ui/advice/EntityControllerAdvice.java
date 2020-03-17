package com.yunseong.study_project.common.ui.advice;

import com.yunseong.study_project.common.errors.NoSuchIdentityException;
import com.yunseong.study_project.common.errors.NoSuchUsernameException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntityControllerAdvice {

    @ExceptionHandler(NoSuchIdentityException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchIdentityException exception) {
        Errors errors = new BeanPropertyBindingResult(exception.getId(), "[" + exception.getEntityName() + "] entityId");
        errors.rejectValue( exception.getEntityName() + ".id", "wrongValue", "id is wrongValue");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NoSuchUsernameException.class)
    public ResponseEntity handleNoSuchUsernameException(NoSuchUsernameException exception) {
        Errors errors = new BeanPropertyBindingResult(exception.getUsername(), "[" + exception.getEntityName() + "] entityId");
        errors.rejectValue(exception.getEntityName() + ".username", "wrongValue", "username is wrongValue");
        return ResponseEntity.badRequest().body(errors);
    }
}

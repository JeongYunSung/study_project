package com.yunseong.study_project.member.ui;

import com.yunseong.study_project.member.command.application.MemberCommandService;
import com.yunseong.study_project.member.command.application.dto.MemberCreateRequest;
import com.yunseong.study_project.member.command.application.dto.MemberUpdateRequest;
import com.yunseong.study_project.member.ui.validator.MemberCreateRequestValidator;
import com.yunseong.study_project.member.ui.validator.MemberUpdateRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/members", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;

    private final MemberCreateRequestValidator memberCreateRequestValidator;
    private final MemberUpdateRequestValidator memberUpdateRequestValidator;

    @PostMapping
    public ResponseEntity createMember(@RequestBody MemberCreateRequest memberRequest, Errors errors) {
        this.memberCreateRequestValidator.validate(memberRequest, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        this.memberCommandService.registerMember(memberRequest);
        URI uri = WebMvcLinkBuilder.linkTo(MemberMyController.class).toUri();

        return ResponseEntity.created(uri).body(memberRequest);
    }

    @PutMapping
    public ResponseEntity updateMemberUsername(@RequestBody MemberUpdateRequest memberRequest, Errors errors) {
        this.memberUpdateRequestValidator.validate(memberRequest, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        this.memberCommandService.updateMember(username, memberRequest);
        URI uri = WebMvcLinkBuilder.linkTo(MemberMyController.class).toUri();

        return ResponseEntity.noContent().location(uri).build();
    }

    @DeleteMapping
    public ResponseEntity deleteMember() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        this.memberCommandService.deleteMemberById(username);

        return ResponseEntity.noContent().build();
    }
}

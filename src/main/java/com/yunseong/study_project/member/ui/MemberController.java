package com.yunseong.study_project.member.ui;

import com.yunseong.study_project.member.command.application.MemberCommandService;
import com.yunseong.study_project.member.command.application.MemberCreateRequest;
import com.yunseong.study_project.member.command.application.MemberUpdateRequest;
import com.yunseong.study_project.member.query.application.MemberQueryService;
import com.yunseong.study_project.member.ui.validator.MemberCreateRequestValidator;
import com.yunseong.study_project.member.ui.validator.MemberUpdateRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createMember(MemberCreateRequest memberRequest, Errors errors) {
        this.memberCreateRequestValidator.validate(memberRequest, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        this.memberCommandService.reigsterMember(memberRequest);
        URI uri = WebMvcLinkBuilder.linkTo(MemberController.class).slash("my").toUri();

        return ResponseEntity.created(uri).body(memberRequest);
    }

    @PutMapping
    public ResponseEntity updateMemberUsername(MemberUpdateRequest memberRequest, Errors errors) {
        this.memberUpdateRequestValidator.validate(memberRequest, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        // User user = (User)SecurityContext.getContext().getAuthentication().getPrincipal()
        String username = "test";
        this.memberCommandService.updateMember(username, memberRequest);
        URI uri = WebMvcLinkBuilder.linkTo(MemberController.class).slash("my").toUri();

        return ResponseEntity.created(uri).body(memberRequest);
    }

    @DeleteMapping
    public ResponseEntity deleteMember() {
        String username = "test";
        this.memberCommandService.deleteMemberById(username);

        return ResponseEntity.ok(username);
    }
}

package com.yunseong.study_project.admin.ui;

import com.yunseong.study_project.common.ui.RestDocsController;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.query.application.MemberQueryService;
import com.yunseong.study_project.member.query.dto.MemberInfoResponse;
import com.yunseong.study_project.member.query.dto.model.MemberInfoResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/admin", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

    private final MemberQueryService memberQueryService;

    @GetMapping("/members/{username}")
    public ResponseEntity findMember(@PathVariable String username) {
        Member member = this.memberQueryService.findMemberByUsername(username);
        MemberInfoResponseModel memberResponseModel = new MemberInfoResponseModel(new MemberInfoResponse(member));
        memberResponseModel.add(linkTo(methodOn(AdminController.class).findMember(username)).withSelfRel());
        memberResponseModel.add(linkTo(methodOn(RestDocsController.class).memberDocs()).withRel("profile"));

        return ResponseEntity.ok(memberResponseModel);
    }
}

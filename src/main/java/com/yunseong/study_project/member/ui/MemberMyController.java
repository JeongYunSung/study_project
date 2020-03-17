package com.yunseong.study_project.member.ui;

import com.yunseong.study_project.common.ui.RestDocsController;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.query.application.MemberQueryService;
import com.yunseong.study_project.member.query.dto.MemberInfoResponse;
import com.yunseong.study_project.member.query.dto.MemberItemResponse;
import com.yunseong.study_project.member.query.dto.model.MemberInfoResponseModel;
import com.yunseong.study_project.member.query.dto.model.MemberItemResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/members/my", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class MemberMyController {

    private final MemberQueryService memberQueryService;

    @GetMapping
    public ResponseEntity findMember() {
        String username = "test";
        Member member = this.memberQueryService.findMemberByUsername(username);
        MemberInfoResponseModel memberResponseModel = new MemberInfoResponseModel(new MemberInfoResponse(member));
        memberResponseModel.add(linkTo(MemberMyController.class).withSelfRel());
        memberResponseModel.add(linkTo(methodOn(MemberMyController.class).findMyItem()).withRel("myItem"));
        memberResponseModel.add(linkTo(methodOn(RestDocsController.class).memberDocs()).withRel("profile"));

        return ResponseEntity.ok(memberResponseModel);
    }

    @GetMapping("/item")
    public ResponseEntity findMyItem() {
        String username = "test";
        Member member = this.memberQueryService.findMemberByUsername(username);
        MemberItemResponseModel memberResponseModel = new MemberItemResponseModel(new MemberItemResponse(member));
        memberResponseModel.add(linkTo(methodOn(MemberMyController.class).findMyItem()).withSelfRel());
        memberResponseModel.add(linkTo(MemberMyController.class).withRel("myInfo"));
        memberResponseModel.add(linkTo(methodOn(RestDocsController.class).memberDocs()).withRel("profile"));

        return ResponseEntity.ok(memberResponseModel);
    }
}

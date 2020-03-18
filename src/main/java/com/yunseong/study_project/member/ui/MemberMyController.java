package com.yunseong.study_project.member.ui;

import com.yunseong.study_project.common.ui.RestDocsController;
import com.yunseong.study_project.common.util.QueryUtil;
import com.yunseong.study_project.member.command.application.MemberCommandService;
import com.yunseong.study_project.member.command.domain.Member;
import com.yunseong.study_project.member.query.application.MemberQueryService;
import com.yunseong.study_project.member.query.application.dto.MemberInfoResponse;
import com.yunseong.study_project.member.query.application.dto.MyItemResponse;
import com.yunseong.study_project.member.query.application.dto.model.MemberInfoResponseModel;
import com.yunseong.study_project.member.query.application.dto.model.MyItemResponseModel;
import com.yunseong.study_project.member.ui.validator.NoSuchMyItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/members/my", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class MemberMyController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @ExceptionHandler(NoSuchMyItemException.class)
    public ResponseEntity handleNoSuchMyItemException(NoSuchMyItemException exception) {
        Errors errors = new BeanPropertyBindingResult("[" + exception.getUsername() + ", " + exception.getId() + "]", "[ Username, Id ]");

        errors.rejectValue("member.username", "wrongValue", "Username is a wrongValue");
        errors.rejectValue("member.myItem.id", "wrongValue", "Id is a wrongValue");

        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping
    public ResponseEntity findMember() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        Member member = this.memberQueryService.findMemberByUsername(username);
        MemberInfoResponseModel memberResponseModel = new MemberInfoResponseModel(new MemberInfoResponse(member));

        memberResponseModel.add(linkTo(MemberMyController.class).withSelfRel());
        memberResponseModel.add(linkTo(MemberController.class).slash("items").withRel("myItemList"));
        addProfileLink(memberResponseModel);

        return ResponseEntity.ok(memberResponseModel);
    }

    @PostMapping("/items/{id}")
    public ResponseEntity createMyItem(@PathVariable  Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        this.memberCommandService.addMyItem(username, id);
        URI uri = WebMvcLinkBuilder.linkTo(MemberMyController.class).slash("my/items").slash(id).toUri();

        return ResponseEntity.created(uri).body(id);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity findMyItem(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        MyItemResponse myItem = this.memberQueryService.findMyItem(username, id);
        MyItemResponseModel model = new MyItemResponseModel(myItem);

        model.add(linkTo(MemberController.class).slash("items").withRel("myItemList"));
        addBasicLink(model);

        return ResponseEntity.ok(model);
    }

    @GetMapping("/items")
    public ResponseEntity findMyItems(@PageableDefault Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        Page<MyItemResponse> page = this.memberQueryService.findMemberItemByPage(username, pageable);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel pagedModel = new PagedModel(page.getContent(), pageMetadata);

        pagedModel.add(linkTo(MemberController.class).slash("items" + QueryUtil.pageableQuery(pageable)).withSelfRel());
        addBasicLink(pagedModel);

        return ResponseEntity.ok(pagedModel);
    }

    private void addBasicLink(RepresentationModel model) {
        model.add(linkTo(MemberMyController.class).withRel("myInfo"));
        addProfileLink(model);
    }

    private void addProfileLink(RepresentationModel model) {
        model.add(linkTo(RestDocsController.class).slash("members").withRel("profile"));
    }
}

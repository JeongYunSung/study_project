package com.yunseong.study_project.category.ui;

import com.yunseong.study_project.category.command.application.CategoryCommandService;
import com.yunseong.study_project.category.command.application.ParentUpdateRequest;
import com.yunseong.study_project.category.query.CategoryQueryService;
import com.yunseong.study_project.category.query.CategoryResponse;
import com.yunseong.study_project.category.query.model.CategoryResponseModel;
import com.yunseong.study_project.category.ui.validator.ParentUpdateValidator;
import com.yunseong.study_project.common.util.Util;
import com.yunseong.study_project.member.query.application.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories", produces = MediaTypes.HAL_JSON_VALUE)
public class CategoryController {

    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    private final ParentUpdateValidator parentUpdateValidator;

    private final MemberQueryService memberQueryService;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody String categoryName) {
        Long id = this.categoryCommandService.createCategory(categoryName);
        URI uri = linkTo(CategoryController.class).slash(id).toUri();

        return ResponseEntity.created(uri).body(id);
    }

    @PutMapping
    public ResponseEntity parentUpdateCategory(@RequestBody ParentUpdateRequest parentUpdateRequest, Errors errors) {
        if (this.memberQueryService.isOwner(this.categoryQueryService.findCategoryResponseById(parentUpdateRequest.getMyId()).getCreateId(), errors)) {
            return ResponseEntity.badRequest().body(errors);
        }
        this.parentUpdateValidator.validate(parentUpdateRequest, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Long childId = this.categoryCommandService.updateChildCategory(parentUpdateRequest.getParentId(), parentUpdateRequest.getMyId());
        URI uri = linkTo(CategoryController.class).slash(childId).toUri();

        return ResponseEntity.noContent().location(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateNameCategory(@PathVariable Long id, @RequestBody String categoryName) {
        Errors errors = new BeanPropertyBindingResult(id, "id");
        if (this.memberQueryService.isOwner(this.categoryQueryService.findCategoryResponseById(id).getCreateId(), errors)) {
            return ResponseEntity.badRequest().body(errors);
        }
        URI uri = linkTo(CategoryController.class).slash(this.categoryCommandService.updateCategoryName(id, categoryName)).toUri();
        return ResponseEntity.noContent().location(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findCategory(@PathVariable Long id) {
        CategoryResponseModel responseModel = new CategoryResponseModel(this.categoryQueryService.findCategoryResponseById(id));
        responseModel.add(linkTo(CategoryController.class).withRel("categories"));
        addBasicLink(responseModel.getContent(), responseModel);
        responseModel.add(new Link(Util.profileURL).withRel("profile").withRel("profile"));

        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/list")
    public ResponseEntity findCategoryAll(@PageableDefault Pageable pageable) {
        Page<CategoryResponseModel> page = this.categoryQueryService.findCategoryResponseByPage(pageable).map(CategoryResponseModel::new);
        page.stream().forEach(category -> addBasicLink(category.getContent(), category));
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        PagedModel model = new PagedModel(page.getContent(), metadata);

        model.add(linkTo(CategoryController.class).slash("list" + Util.pageableQuery(pageable)).withSelfRel());
        model.add(new Link(Util.profileURL).withRel("profile").withRel("profile"));

        return ResponseEntity.ok(model);
    }

    private void addBasicLink(CategoryResponse category, RepresentationModel model) {
        if(category.getParentId() != null)
            model.add(linkTo(CategoryController.class).slash(category.getParentId()).withRel("parent"));

        model.add(linkTo(CategoryController.class).slash(category.getId()).withSelfRel());
        category.getChildName().stream().forEach(child -> model.add(linkTo(CategoryController.class).slash(child).withRel("child")));
    }
}

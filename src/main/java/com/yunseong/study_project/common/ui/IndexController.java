package com.yunseong.study_project.common.ui;

import com.yunseong.study_project.category.ui.CategoryController;
import com.yunseong.study_project.common.util.Util;
import com.yunseong.study_project.member.ui.MemberController;
import com.yunseong.study_project.product.ui.ProductController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity indexResponse() {
        RepresentationModel model = new RepresentationModel();

        model.add(linkTo(MemberController.class).withRel("members"));
        model.add(linkTo(CategoryController.class).withRel("categories"));
        model.add(linkTo(ProductController.class).withRel("products"));
        model.add(new Link(Util.profileURL).withRel("profile"));

        return ResponseEntity.ok(model);
    }
}

package com.yunseong.study_project.product.ui;

import com.yunseong.study_project.common.util.Util;
import com.yunseong.study_project.member.query.application.MemberQueryService;
import com.yunseong.study_project.product.command.application.ProductCommendService;
import com.yunseong.study_project.product.command.application.ProductRequest;
import com.yunseong.study_project.product.query.application.ProductQueryService;
import com.yunseong.study_project.product.query.application.dto.ProductResponse;
import com.yunseong.study_project.product.query.application.dto.ProductResponseModel;
import com.yunseong.study_project.product.query.application.dto.ProductSearchCondition;
import com.yunseong.study_project.product.ui.validator.ProductRequestValidator;
import com.yunseong.study_project.product.ui.validator.ProductSearchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductCommendService productCommendService;
    private final ProductQueryService productQueryService;

    private final MemberQueryService memberQueryService;

    private final ProductRequestValidator productRequestValidator;
    private final ProductSearchValidator productSearchValidator;

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody ProductRequest productCreateRequest, Errors errors) {
        if (validate(productCreateRequest, errors)) return ResponseEntity.badRequest().body(errors);
        Long id = this.productCommendService.registerProduct(productCreateRequest);
        URI uri = linkTo(ProductController.class).slash(id).toUri();
        return ResponseEntity.created(uri).body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody ProductRequest request, Errors errors) {
        if (this.memberQueryService.isOwner(this.productQueryService.findProductResponseById(id).getCreateId(), errors)) {
            return ResponseEntity.badRequest().body(errors);
        }
        if (validate(request, errors)) return ResponseEntity.badRequest().body(errors);
        this.productCommendService.updateProduct(id, request);
        URI uri = linkTo(ProductController.class).slash(id).toUri();
        return ResponseEntity.noContent().location(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity selectProduct(@PathVariable Long id) {
        ProductResponse product = this.productQueryService.findProductResponseById(id);
        ProductResponseModel model = new ProductResponseModel(product);

        model.add(linkTo(ProductController.class).slash(id).withSelfRel());
        model.add(linkTo(ProductController.class).withRel("products"));
        model.add(new Link(Util.profileURL).withRel("profile"));

        return ResponseEntity.ok(model);
    }

    @GetMapping
    public ResponseEntity selectProducts(@PageableDefault Pageable pageable, @RequestBody ProductSearchCondition condition, Errors errors) {
        this.productSearchValidator.validate(condition, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Page<ProductResponseModel> product = this.productQueryService.findProductReponseByPage(condition, pageable).map(response ->
            new ProductResponseModel(response, linkTo(ProductController.class).slash(response.getProductId()).withRel("product")));
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(product.getSize(), product.getNumber(), product.getTotalElements(), product.getTotalPages());
        PagedModel<ProductResponseModel> model = new PagedModel(product.getContent(), metadata);

        model.add(linkTo(ProductController.class).withSelfRel());
        model.add(new Link(Util.profileURL).withRel("profile"));

        return ResponseEntity.ok(model);
    }

    private boolean validate(@RequestBody ProductRequest request, Errors errors) {
        this.productRequestValidator.validate(request, errors);
        if (errors.hasErrors()) {
            return true;
        }
        return false;
    }
}

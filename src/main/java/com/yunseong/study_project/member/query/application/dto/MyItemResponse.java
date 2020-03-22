package com.yunseong.study_project.member.query.application.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.yunseong.study_project.member.command.domain.MyItem;
import com.yunseong.study_project.member.ui.MemberMyController;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class MyItemResponse {

    private Long id;
    private String username;
    private String nickname;
    private long productId;
    private String productName;
    private long productPrice;
    private LocalDateTime buyTime;

    @QueryProjection
    public MyItemResponse(MyItem myItem) {
        this.id = myItem.getId();
        this.username = myItem.getMember().getUsername();
        this.nickname = myItem.getMember().getNickname();
        this.productId = myItem.getProduct().getId();
        this.productName = myItem.getProduct().getProductName();
        this.productPrice = myItem.getProduct().getPrice();
        this.buyTime = myItem.getCreatedTime();
    }
}

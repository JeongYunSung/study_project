package com.yunseong.study_project.member.ui;

import com.yunseong.study_project.category.domain.command.domain.application.CategoryCommandService;
import com.yunseong.study_project.category.domain.query.CategoryQueryService;
import com.yunseong.study_project.common.BaseTest;
import com.yunseong.study_project.member.command.domain.MyItem;
import com.yunseong.study_project.member.query.application.MemberQueryService;
import com.yunseong.study_project.product.command.application.ProductCommendService;
import com.yunseong.study_project.product.command.application.dto.ProductCreateRequest;
import com.yunseong.study_project.product.query.application.ProductQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberMyControllerTest extends BaseTest {

    @Test
    public void findMemberTest() throws Exception {
        //given
        String bearerToken = this.getBearerToken(true);
        //when
        ResultActions perform = this.mockMvc.perform(get("/api/members/my")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .accept(MediaTypes.HAL_JSON_VALUE));
        //then
        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("select-member",
                        links(
                                linkWithRel("self").description("현재 경로"),
                                linkWithRel("myItemList").description("소유아이템 목록"),
                                linkWithRel("profile").description("참고 문서")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer"),
                                headerWithName(HttpHeaders.ACCEPT).description("Accept")
                        ),
                        responseFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("유저 아이디"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                fieldWithPath("money").type(JsonFieldType.NUMBER).description("보유자산"),
                                fieldWithPath("grade").type(JsonFieldType.STRING).description("현재등급"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("현재 경로"),
                                fieldWithPath("_links.myItemList.href").type(JsonFieldType.STRING).description("소유아이템 목록"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("참고 문서")
                        )
                ));
    }

    @Test
    public void createMyItemTest() throws Exception {
        //given
        String bearerToken = this.getBearerToken(true);
        //when
        ResultActions perform = this.mockMvc.perform(get("/api/members/my")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .accept(MediaTypes.HAL_JSON_VALUE));
        perform
                .andExpect(status().isOk());
/*        ResultActions perform = this.mockMvc.perform(post("/api/members/my/items")
                .accept(MediaTypes.HAL_FORMS_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(1L)));
        //then
        perform
                .andExpect(status().isCreated())
                .andDo(print());*/
    }
}
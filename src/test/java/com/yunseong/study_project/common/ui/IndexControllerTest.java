package com.yunseong.study_project.common.ui;

import com.yunseong.study_project.common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndexControllerTest extends BaseTest {

    @Test
    public void indexResponseEntity() throws Exception {
        //given
        //when
        ResultActions perform = this.mockMvc.perform(get("/"));
        //then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("index",
                        links(
                                linkWithRel("members").description("회원리 소스 링크"),
                                linkWithRel("categories").description("카테고리 리소스 링크"),
                                linkWithRel("products").description("상품 리소스 링크"),
                                linkWithRel("profile").description("관련 링크")
                        )
                ));
    }
}
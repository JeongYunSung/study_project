package com.yunseong.study_project.category.ui;

import com.yunseong.study_project.category.command.application.CategoryCommandService;
import com.yunseong.study_project.category.command.application.ParentUpdateRequest;
import com.yunseong.study_project.category.query.CategoryQueryService;
import com.yunseong.study_project.common.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends BaseTest {

    @Autowired
    private CategoryCommandService categoryCommandService;
    @Autowired
    private CategoryQueryService categoryQueryService;

    @Test
    public void createCategory() throws Exception {
        //given
        String bearerToken = this.getBearerToken();
        //when
        ResultActions result = this.mockMvc.perform(post("/api/categories")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content("Test")
                .contentType(MediaTypes.HAL_JSON_VALUE));
        //then
        result
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-category",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer"),
                                headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("새로운 카테고리")
                        )
                ));
    }

    @Test
    public void parentUpdateCategoryTest() throws Exception {
        //given
        String bearerToken = getBearerToken();
        String categoryA = "A";
        String categoryB = "B";
        Long aId = Long.parseLong(this.mockMvc.perform(post("/api/categories").header(HttpHeaders.AUTHORIZATION, bearerToken).content(categoryA)).andReturn().getResponse().getContentAsString());
        Long bId = Long.parseLong(this.mockMvc.perform(post("/api/categories").header(HttpHeaders.AUTHORIZATION, bearerToken).content(categoryB)).andReturn().getResponse().getContentAsString());
        ParentUpdateRequest request = new ParentUpdateRequest(aId, bId);
        //when
        ResultActions perform = this.mockMvc.perform(put("/api/categories")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request)));
        //then
        Assertions.assertEquals(this.categoryQueryService.findCategoryResponseById(bId).getParentId(), aId);
        perform
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("update-parent-category",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer"),
                                headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
                        ),
                        requestFields(
                                fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
                                fieldWithPath("childId").type(JsonFieldType.NUMBER).description("자식 카테고리 ID")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("새로운 카테고리")
                        )
                ));
    }

    @Test
    public void updateNameCategoryTest() throws Exception {
        //given
        String bearerToken = getBearerToken();
        String categoryA = "C";
        Long aId = Long.parseLong(this.mockMvc.perform(post("/api/categories").content(categoryA).header(HttpHeaders.AUTHORIZATION, bearerToken)).andReturn().getResponse().getContentAsString());
        String updateName = "D";
        //when
        ResultActions perform = this.mockMvc.perform(put("/api/categories/{id}", aId)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .content(updateName));
        //then
        Assertions.assertEquals(this.categoryQueryService.findCategoryResponseById(aId).getCategoryName(), updateName);
        perform
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("update-name-category",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer"),
                                headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
                        ),
                        pathParameters(
                                parameterWithName("id").description("카테고리의 고유번호")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("새로운 카테고리")
                        )
                ));
    }

    @Test
    public void findCategoryTest() throws Exception {
        //given
        String bearerToken = getBearerToken();
        String categoryName1 = "ABC1";
        String categoryName2 = "ABC2";
        String categoryName3 = "ABC3";
        Long aId = Long.parseLong(this.mockMvc.perform(post("/api/categories").header(HttpHeaders.AUTHORIZATION, bearerToken).content(categoryName1)).andReturn().getResponse().getContentAsString());
        Long bId = Long.parseLong(this.mockMvc.perform(post("/api/categories").header(HttpHeaders.AUTHORIZATION, bearerToken).content(categoryName2)).andReturn().getResponse().getContentAsString());
        Long cId = Long.parseLong(this.mockMvc.perform(post("/api/categories").header(HttpHeaders.AUTHORIZATION, bearerToken).content(categoryName3)).andReturn().getResponse().getContentAsString());
        ParentUpdateRequest request1 = new ParentUpdateRequest(aId, bId);
        ParentUpdateRequest request2 = new ParentUpdateRequest(bId, cId);
        //when
        this.mockMvc.perform(put("/api/categories")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request1))
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(put("/api/categories")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(request2))
                .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNoContent());
        ResultActions perform = this.mockMvc.perform(get("/api/categories/{id}", bId)
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .accept(MediaTypes.HAL_JSON_VALUE));
        //then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("select-category",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer"),
                                headerWithName(HttpHeaders.ACCEPT).description("Accept")
                        ),
                        pathParameters(
                                parameterWithName("id").description("카테고리 고유번호")
                        ),
                        responseFields(
                                fieldWithPath("_links.categories.href").type(JsonFieldType.STRING).description("카테고리 목록"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("현재 링크"),
                                fieldWithPath("_links.parent.href").type(JsonFieldType.STRING).optional().description("부모 링크"),
                                fieldWithPath("_links.child.href").type(JsonFieldType.STRING).optional().description("자식 링크"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("관련 문서"),
                                fieldWithPath("createId").type(JsonFieldType.STRING).optional().description("카테고리 소유자"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("현재 카테고리 고유번호"),
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("현재 카테고리 이름"),
                                fieldWithPath("parentId").type(JsonFieldType.NUMBER).optional().description("부모 카테고리 고유번호"),
                                fieldWithPath("parentName").type(JsonFieldType.STRING).optional().description("부모 카테고리의 이름"),
                                subsectionWithPath("childName").type(JsonFieldType.ARRAY).optional().description("자식 카테고리의 리스트")
                        )
                ));
    }

    @Test
    public void findCategoryAllTest() throws Exception {
        //given
        String bearToken = getBearerToken();
        IntStream.range(1, 10).forEach(i -> this.categoryCommandService.createCategory("[" + i + "]Test"));
        LongStream.range(1, 9).forEach(i -> this.categoryCommandService.updateChildCategory(i, i+1));
        //when
        ResultActions perform = this.mockMvc.perform(get("/api/categories/list")
                .param("size", "5")
                .accept(MediaTypes.HAL_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, bearToken));
        //then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("select-categories",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer"),
                                headerWithName(HttpHeaders.ACCEPT).description("Accept")
                        ),
                        requestParameters(
                                parameterWithName("size").optional().description("페이지 크기"),
                                parameterWithName("page").optional().description("현재 페이지"),
                                parameterWithName("sort").optional().description("정렬방식")
                        ),
                        responseFields(
                                subsectionWithPath("_embedded.categoryResponseList").description("각 카테고리 항목"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("현재 링크"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("관련 문서"),
                                fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("총 요소개수"),
                                fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("총 페이지개수"),
                                fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("현재 페이지번호")
                        )
                ));
    }
}
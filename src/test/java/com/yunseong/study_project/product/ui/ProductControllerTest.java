package com.yunseong.study_project.product.ui;

import com.google.common.net.HttpHeaders;
import com.yunseong.study_project.category.command.application.CategoryCommandService;
import com.yunseong.study_project.common.BaseTest;
import com.yunseong.study_project.product.command.application.ProductCommendService;
import com.yunseong.study_project.product.command.application.ProductRequest;
import com.yunseong.study_project.product.query.application.dto.ProductSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.stream.LongStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends BaseTest {

    @Autowired
    private CategoryCommandService categoryCommandService;
    @Autowired
    private ProductCommendService productCommendService;

    @Test
    public void registerProductTest() throws Exception {
        //given
        String bearerToken = this.getBearerToken();

        Long categoryId = this.categoryCommandService.createCategory("Test");

        String productName = "임시상품";
        String content = "임시상품에 해당하는 콘텐츠입니다 ^^";
        long price = 100L;
        ProductRequest request = new ProductRequest(productName, content, Arrays.asList(categoryId), price);
        //when
        ResultActions perform = this.mockMvc.perform(
                post("/api/products")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(request)));
        //then
        perform
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-product",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization")
                        ),
                        requestFields(
                                fieldWithPath("productName").type(JsonFieldType.STRING).description("상품이름"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("상품내용"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("categoryIdList").type(JsonFieldType.ARRAY).description("상품 카테고리 목록")
                        )));
    }
    
    @Test
    public void updateProductTest() throws Exception {
        //given
        String bearerToken = getBearerToken();

        Long categoryId = this.categoryCommandService.createCategory("임시카테고리");

        Long id = createProduct(categoryId, "상품", "상품에 해당하는 콘텐츠입니다 ^^", 100L);

        String updateName = "변경된상품";
        String updateContent = "변경된 상품에 해당하는 콘텐츠";
        ProductRequest updateRequest = new ProductRequest(updateName, updateContent, Arrays.asList(categoryId), 10000L);
        //when
        ResultActions perform = this.mockMvc.perform(
                put("/api/products/{id}", id)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .content(this.objectMapper.writeValueAsString(updateRequest)));
        //then
        perform
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("update-product",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorization")
                        ),
                        requestFields(
                                fieldWithPath("productName").type(JsonFieldType.STRING).description("상품이름"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("상품내용"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("categoryIdList").type(JsonFieldType.ARRAY).description("상품 카테고리 목록")
                        ),
                        pathParameters(
                                parameterWithName("id").description("변경될 상품의 고유번호")
                        )));
    }

    @Test
    public void selectProductTest() throws Exception {
        //given
        String bearerToken = getBearerToken();
        Long categoryId = this.categoryCommandService.createCategory("임시카테고리");
        Long id = createProduct(categoryId, "테스트상품", "테스트상품에 해당하는 콘텐츠입니다 ^^", 100L);
        //when
        ResultActions perform = this.mockMvc.perform(
                get("/api/products/{id}", id)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken));
        //then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("select-product",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("BearerToken")
                        ),
                        pathParameters(
                                parameterWithName("id").description("상품 고유번호")
                        ),
                        responseFields(
                                fieldWithPath("createId").type(JsonFieldType.NUMBER).description("판매자"),
                                fieldWithPath("productName").type(JsonFieldType.STRING).description("상품이름"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("상품내용"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("categoryIdList").type(JsonFieldType.ARRAY).description("상품 카테고리 목록"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("현재 링크"),
                                fieldWithPath("_links.products.href").type(JsonFieldType.STRING).description("상품목록 링크"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("관련정보 링크")
                        )));
    }

    @Test
    public void selectProductsTest() throws Exception {
        //given
        String bearerToken = getBearerToken();
        Long categoryId = this.categoryCommandService.createCategory("category");
        LongStream.range(0, 5).forEach(i -> createProduct(categoryId, "[" + i + "]testProduct", "[" + i + "]testProduct content ^^", 1000L * i));
        LongStream.range(0, 5).forEach(i -> createProduct(categoryId, "[" + i + "]~~~~", "[" + i + "]testProduct content ^^", 1000L * i));
        ProductSearchCondition condition = new ProductSearchCondition("product", null, "c");
        //when
        ResultActions perform = this.mockMvc.perform(
                get("/api/products")
                        .param("size", "5")
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(condition)));
        //then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("select-products",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("BearerToken")
                        ),
                        requestFields(
                                fieldWithPath("productName").type(JsonFieldType.STRING).optional().description("제목 검색"),
                                fieldWithPath("content").type(JsonFieldType.STRING).optional().description("내용 검색"),
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).optional().description("카테고리 검색")
                        ),
                        responseFields(
                                subsectionWithPath("_embedded.productResponseList").description("각 상품품항목"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("현재 링크"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("관련 문서"),
                                fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("총 요소개수"),
                                fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("총 페이지개수"),
                                fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("현재 페이지번호")
                        )
                ));
    }

    private Long createProduct(Long categoryId, String productName, String content, long price) {
        ProductRequest request = new ProductRequest(productName, content, Arrays.asList(categoryId), price);
        return this.productCommendService.registerProduct(request);
    }
}
package com.yunseong.study_project.member.ui;

import com.yunseong.study_project.common.BaseTest;
import com.yunseong.study_project.member.command.application.dto.MemberCreateRequest;
import com.yunseong.study_project.member.command.application.dto.MemberUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends BaseTest {



    @Test
    public void createMemberTest() throws Exception {
        //given
        String username = "createID";
        String password = "createPW";
        String nickname = "createName";
        MemberCreateRequest member = new MemberCreateRequest(username, password, nickname);
        //when
        ResultActions perform = this.mockMvc.perform(
                post("/api/members")
                        .contentType(MediaTypes.HAL_FORMS_JSON_VALUE)
                        .content(this.objectMapper.writeValueAsString(member)));
        //then
        perform
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-member",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
                        ),
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("새로운 유저의 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("새로운 유저의 비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("새로운 유저의 닉네임")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("새로운 유저 URL")
                        ),
                        responseFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("새로운 유저의 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("새로운 유저의 비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("새로운 유저의 닉네임")
                        )
                ));
    }

    @Test
    public void updateMemberTest() throws Exception {
        //given
        String bearerToken = this.getBearerToken();
        String updateName = "updateName";
        String updatePassword = "updatePassword";
        //when
        ResultActions result = this.mockMvc.perform(put("/api/members")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(new MemberUpdateRequest(updateName, updatePassword))));
        //then
        Assertions.assertEquals(this.memberQueryService.findMemberByUsername(this.username).getNickname(), updateName);
        result
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("update-member",
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer"),
                        headerWithName(HttpHeaders.ACCEPT).description("Accept"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
                ),
                requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경된 유저의 닉네임"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("변경된 유저의 비밀번호")
                ),
                responseHeaders(
                        headerWithName("location").description("변경된 유저 URL")
                )
        ));
    }
}
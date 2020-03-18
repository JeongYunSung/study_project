package com.yunseong.study_project.member.ui;

import com.yunseong.study_project.common.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends BaseTest {

    @Test
    public void createMemberTest() throws Exception {
        //given
        String username = "testID";
        String password = "testPW";
        String nickname = "testName";
        //when
        ResultActions perform = this.mockMvc.perform(
                post("/api/members")
                        .param("username", username)
                        .param("password", password)
                        .param("nickname", nickname));
        //then
        perform
                .andExpect(status().isCreated())
                .andDo(document("",
                        requestParameters(

                        )
                ));
    }

    @Test
    public void updateMemberTest() throws Exception {
        //given

        //when

        //then
    }
}
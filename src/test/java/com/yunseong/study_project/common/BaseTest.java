package com.yunseong.study_project.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunseong.study_project.member.command.application.MemberCommandService;
import com.yunseong.study_project.member.command.application.dto.MemberCreateRequest;
import com.yunseong.study_project.member.command.domain.Member;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfig.class)
public class BaseTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MemberCommandService memberCommandService;

    private final String username = "testID";
    private final String password = "testPW";

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken(UUID.randomUUID().toString(), "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
    }

    protected String getBearerToken(boolean account) throws Exception {
        return "Bearer " + getAccessToken(account);
    }

    private String getAccessToken(boolean account) throws Exception {
        if (account) registerMember();
        ResultActions result = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic("jys-client", "jys-password"))
                .param("username", this.username)
                .param("password", this.password)
                .param("grant_type", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

        String contentAsString = result.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser jsonParser = new Jackson2JsonParser();
        return jsonParser.parseMap(contentAsString).get("access_token").toString();
    }

    private void registerMember() {
        Member member = new Member(this.username, this.password, "testName");
        this.memberCommandService.registerMember(new MemberCreateRequest(member.getUsername(), member.getNickname(), member.getPassword()));
    }
}

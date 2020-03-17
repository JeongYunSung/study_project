package com.yunseong.study_project.common.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/docs")
public class RestDocsController {

    @GetMapping("/members")
    public String memberDocs() {
        return null;
    }
}

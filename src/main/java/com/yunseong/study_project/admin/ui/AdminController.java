package com.yunseong.study_project.admin.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {
}

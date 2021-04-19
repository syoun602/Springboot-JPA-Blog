package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"", "/"}) // ""일 때와 "/"일 때 둘 다 적용되게
    public String index() {

        // WEB-INF/views/index.jsp
        return "index";
    }
}

package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//RestController는 string return 시 문자 그 자체를 리턴
//Controller 해당 경로 이하에 있는 파일을 리턴해
@Controller
public class TempControllerTest {

    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("tempHome()");
        // Spring 파일 return 기본 경로: src/main/resources/static
        // 리턴명 : /home.html
        // 풀 경로: src/main/resources/static/home.html
        return "/home.html";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp() {
        // prefix: /WEB-INF/views/
        // suffix: .jsp
        // full name: /WEB-INF/views/test.jsp
        return "test";
    }
}

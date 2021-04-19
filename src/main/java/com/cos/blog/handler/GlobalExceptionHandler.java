package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 어디에서든 exception 발생 시, 해당 클래스로 들어오게 함
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //현재는 Exception이라 모든 exception에 대해 해당 함수 실행. 만약 exception 종류 별로 다르게 원한다면 IllegalArgumentException와 같이 개별로 exception 하위에 있는 것을 명
    public String handleArgumentException(Exception e) {
        return "<h1>" + e.getMessage() + "</h1>";
    }
}
package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML 파일) 이면 @Controller
// 사용자가 요청 -> 응답(Data) 이면 @RestController
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest : ";

    // http://localhost:8000/blog/http/lombok
    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
        System.out.println(TAG + "getter : " + m.getUsername());
        m.setUsername("cos");
        System.out.println(TAG + "setter : " + m.getUsername());
        return "lombok test 완료";
        //test1

    }
    //인터넷 브라우저 요청은 무조건 get 요청 밖에 할 수 없다. (post, put, get은 안됨)
    // http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m) { //id=1&username=sun&password=1234&email=sun@nate.com
        //밑에처럼 requestParam으로 하면 하나하나 다 적어주어야 하는데, member object를 통해
        return "get 요청: " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() +", "+ m.getEmail() ;
    }
//    public String getTest(@RequestParam int id, @RequestParam String username) {
//        return "get 요청: " + id + ", " + username;
//    }

    // http://localhost:8080/http/post (insert)
    @PostMapping("/http/post") // raw = text/plain    application/json
    public String postTest(@RequestBody Member m) { // SpringBoot의 MessageConverter가 Member m을 그대로 object에 mapping해서 집어 넣어줌
        return "post 요청: " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() +", "+ m.getEmail() ;
    }

    // http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청: " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() +", "+ m.getEmail() ;
    }

    // http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}

package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController // (html파일이 아닌 data를 return 해주는 controller = RestController
// 페이지 이동이 아닌 회원가입이 됐는지 안됐는지 응답만 해주게 하기 위해
public class DummyControllerTest {

    @Autowired //의존성 주입(DI)
    // 원래는 userRepository가 Null인데, @Autowired로 DummyControllerTest가 memory에 뜰 때, userRepository 같이 띄움
    private UserRepository userRepository;

    @GetMapping("/dummy/user")
    public List<User> list() {
        return userRepository.findAll();
    }

    // {id} 주소로 파라미터를 전달 받을 수 있음.
    // http://localhost:8000/blog/dummy/user/5
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {

//        아래에 대한 람다식
//        User user = userRepository.findById(id).orElseThrow(() ->{
//            return new IllegalArgumentException("해당 사용자는 없습니다.");
//        });

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            // findById의 return 값은 Optional
            // 왜? - user/4 를 찾으면 (현재 DB에는 3까지만 있음) DB에서 없으므 user는 null이 되고 return 또한 null이 됨. 로
            // 이는 프로그램에서 문제를 야기하므로 optional로 User 객체를 감싸서 가져와 null인지 아닌지 판단해서 return 할 수 있게 해줌
            // 하여 new Supplier<IllegalArgumentException>() 로 오류를 반환해줌
            // 현재는 whitelabel error page에서 표시되지만,  나중에 스프링 AOP 를 통해 현 exception을 가로채 user compatible 한 오류 페이지 보여줄 수 있음
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });

        // 요청 : 웹 브라우저
        // user 객체 = Java Object
        // 변환 (웹 브라우저가 이해할 수 있는 데이터) -> json
        // 예전에는 Gson 라이브러리 등을 이용했는데, SpringBoot = MessageConverter가 응답 시에 자동 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson이라는 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
        return user;
    }

    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("id : " + user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("role : " + user.getRole());
        System.out.println("createdate : " + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }
}

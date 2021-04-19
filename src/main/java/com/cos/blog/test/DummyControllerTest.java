package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController // (html파일이 아닌 data를 return 해주는 controller = RestController
// 페이지 이동이 아닌 회원가입이 됐는지 안됐는지 응답만 해주게 하기 위해
public class DummyControllerTest {

    @Autowired //의존성 주입(DI)
    // 원래는 userRepository가 Null인데, @Autowired로 DummyControllerTest가 memory에 뜰 때, userRepository 같이 띄움
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) { // 귀찮으면 Exception e 로 한번에 해도 되는데 그러면 다른 exception 도 이렇게 처리될 수 있
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제 되었습니다. id : " + id;
    }

    // save() 함수는 id를 전달하지 않으면 insert를 해주고,
    // save() 함수는 id를 전달하면 해당 id에 대한 데이터가 있을 때, update를 해주고,
    // save() 함수는 id를 전달하면 해당 id에 대한 데이터가 없을 때, insert를 한다
    // email, password 변경하기
//    @PutMapping("/dummy/user/{id}")
//    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
//        // json 데이터를 요청 => 스프링이 Java Object(MessageConverter의 Jackson Library가) 변환해서 받아줌
//        System.out.println("id: " + id);
//        System.out.println("password: " + requestUser.getPassword());
//        System.out.println("email: " + requestUser.getEmail());
//
//
////        update를 위해 save()를 할 수도 있지만, 지정해준 값만 넘겨주고 다른 값은 null로 넣어버림
////        그래서 save()를 자주 쓰지는 않음
////        requestUser.setId(id);
////        requestUser.setUsername("ssar");
////        userRepository.save(requestUser);
//
//        // save로 하는 법
//        // 위의 코드는 requestUser에 새로운 값을 넣어 바로 반영한거에 반면,
//        // 해당 코드는 user_id 에 매핑되는 user 객체를 받아와서  값을 변경해주고 ave 했기 때문
//        User user = userRepository.findById(id).orElseThrow(()->{
//            return new IllegalArgumentException("수정에 실패하였습니다.");
//        });
//        user.setPassword(requestUser.getPassword());
//        user.setEmail(requestUser.getEmail());
//
//        userRepository.save(user);
//
//        return null;
//    }

    // save말고 @Transactional을 이용한 update
    @Transactional //함수 종료 시에 자동으로 commit (Database transaction을 시작하고, updateUser method가 종료될 때 Transaction 종료)
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
        System.out.println("id: " + id);
        System.out.println("password: " + requestUser.getPassword());
        System.out.println("email: " + requestUser.getEmail());


        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // userdRepository.save(user);
        // save 없이 되는 이유? - 더티 체킹
       return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    //한 페이지당 2 건에 데이터를 리턴받기
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();

        return users;
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
                return new IllegalArgumentException("해당 유저는 없습니다.");
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

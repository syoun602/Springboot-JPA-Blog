package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 스프링이 컴포넌트 스캔을 통해 Bean에 등록을 해줌. IoC를 해준다
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // 하나의 transaction으로 묶어줘서 전체가 성공하면 commit, 실패시 rollback
    public int 회원가입(User user) {
        try {
            userRepository.save(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService: 회원가입() : " + e.getMessage());
        }
        return -1;
    }
}

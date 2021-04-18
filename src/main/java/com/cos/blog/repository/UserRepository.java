package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO
// 자동으로 bean 등록이 된다
// @Repository가 생략 가능하다
public interface UserRepository extends JpaRepository<User, Integer> { // 해당 JpaRepo는 User table이 관리하는 repository이며 user table의 pk는 integer라는 뜻

}

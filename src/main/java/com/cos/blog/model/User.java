package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// JPA는 ORM 인데,
// ORM이란? - Java(다른 언어) Object -> 테이플로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 필더 패턴
@Entity // User 클래스가 자동으로 MySQL에 테이블이 생성이 된다.
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. auto_inc를 사용하겠다는 뜻
    private int id; // auto_increment

    @Column(nullable = false, length = 30) // null 이 될 수 없으며, 30자를 넘을 수 없다
    private String username; // ID

    @Column(nullable = false, length = 100) // 1234 -> 해시를 통해 비밀번호 암호화할꺼라 길어야 한다
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @ColumnDefault("'user'")
    private String role; // Enum을 쓰는게 좋다. Data의 도메인을 만들 수 있음 ex) admin, user, manager 와 같이 둬서 권한 별로 정의 가능 + 오타 방지

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
    //updateDate 도 있으면 좋음
}
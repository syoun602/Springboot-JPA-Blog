package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 필더 패턴
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터에서 사용
    private String content;

    @ColumnDefault("0")
    private int count; // 조회 수

    @ManyToOne // Many = Board, User = One; 한 명의 유저는 여러 개의 게시글을 쓸 수 있게 해줌
    @JoinColumn(name = "userId")
    private User user; // user id 값으로 다시 user를 select나 join으로 data를 가져올 수 있지만,
                                  // ORM에서는 사용 X. FK로 찾지 않고 user object로 바로 넣어도 됨
    // 원래는 DB는 오브젝트를 저장할 수 없기에 FK를 사용하는데, 자바에서는 오브젝트 사용 가능. -> 충돌이 일어남
    // 하지만, JPA의 ORM은 object 그대로 저장 가능
    // 실제로는 FK로 저장되는데, 이를 위해서 @JoinColumn()을 사용해야 한다

    @CreationTimestamp
    private Timestamp createDate;
}

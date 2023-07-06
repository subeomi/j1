package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="t_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board") // board는 빼라
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replyFile;

    private String replyer;

    // 연관관계시 fetch는 기본으로 넣고 가라
    // 내가 필요한 순간까지 조회하지 않겠다.
    @ManyToOne(fetch = FetchType.LAZY) 
    private Board board;
}
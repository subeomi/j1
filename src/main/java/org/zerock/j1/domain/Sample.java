package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_sample")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Sample {
    
    // @Id = 기본 키
    @Id
    private String keyCol;
    
    // 컬럼 명이 DB의 예약어에 위반이 되지 않는지 고려
    private String first;

    private String last;

}

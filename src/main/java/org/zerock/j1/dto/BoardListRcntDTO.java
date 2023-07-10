package org.zerock.j1.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardListRcntDTO {
    
    private Long bno;
    private String title;
    private String writer;
    private long replyCount;
    private LocalDateTime regDate;

}

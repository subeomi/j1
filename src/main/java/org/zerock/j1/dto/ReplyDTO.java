package org.zerock.j1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    
    private Long rno;

    private String replyText;

    private String replyFile;

    private String replyer;

    private Long bno;

}

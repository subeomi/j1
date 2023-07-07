package org.zerock.j1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.Reply;
import org.zerock.j1.dto.ReplyPageRequestDTO;
import org.zerock.j1.service.ReplyService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {
    
    @Autowired
    private ReplyRepository replyRepository;

    // 원래 리포지토리 테스트 따로 서비스 테스트 따로해야함
    @Autowired
    private ReplyService replyService;

    @Test
    public void insertOne(){

        Long bno = 100L;

        // Reply의 Board객체
        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
        .replyText("Reply...1")
        .replyer("replyer00")
        .board(board)
        .build();

        replyRepository.save(reply);

    }

    @Test
    public void testInsertDummies(){

        Long[] bnoArr = {95L, 93L, 91L, 89L, 83L};

        for(Long bno : bnoArr) {

            Board board = Board.builder().bno(bno).build();

            for(int i = 0; i < 50; i++){
                Reply reply = Reply.builder()
                .replyText("Reply..."+bno+"--"+i)
                .replyer("replyer"+i)
                .board(board)
                .build();

                replyRepository.save(reply);
            }

        } // end for

    }

    @Test
    public void testListBoard(){

        Long bno = 95L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listBoard(bno, pageable);

        result.get().forEach(r -> log.info(r));

    }

    @Test
    public void testCount(){

        Long bno = 99L;

        long count = replyRepository.getCountBoard(bno);

        log.info("count: " + count);
    }

    @Test
    public void testListLast(){
        ReplyPageRequestDTO requestDTO = ReplyPageRequestDTO.builder()
            .bno(95L)
            .last(true)
            .build();

        log.info(replyService.list(requestDTO));
    }

}

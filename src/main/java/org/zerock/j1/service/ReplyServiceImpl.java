package org.zerock.j1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.j1.domain.Reply;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;
import org.zerock.j1.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO) {
        
        boolean last = requestDTO.isLast();

        // 현재 페이지
        int pageNum = requestDTO.getPage();

        // 마지막 페이지를 요청할 경우?
        if(last){
            // replyRepository에서 해당 bno 게시물의 전체 댓글 수를 가져온다
            long totalCount = replyRepository.getCountBoard(requestDTO.getBno());

            // 전체 댓글 수를 size로 나눠서 총 페이지 수를 계산한 후 pageNum에 넣음 예) 52/50.0 = 1.04 -> 올리면 2
            pageNum = (int)(Math.ceil(totalCount/(double)requestDTO.getSize()));

            // pageNum이 0이면 밑에서 -1이 되어 문제가 생기기 때문에 그에대한 조치
            pageNum = pageNum <= 0? 1: pageNum;
        }

        // 페이지어블 생성. PageRequest.of(페이지 번호, 페이지의 사이즈, 정렬 기준(rno로 오름차순 어센딩)
        Pageable pageable = PageRequest.of(pageNum -1 , requestDTO.getSize(), Sort.by("rno").ascending() );

        // pageable의 결과물인 Page타입 객체. 해당 bno의 해당 댓글페이지의 댓글을 가져온다
        Page<Reply> result = replyRepository.listBoard(requestDTO.getBno(), pageable);

        log.info("-----------------------------------");

        // 가져온 댓글인 Page타입 result의 총 댓글 수(getTotalElements)를 가져와 저장
        long totalReplyCount = result.getTotalElements();

        // 가져온 댓글 데이터를 List<ReplyDTO>타입으로 가공한다.
        List<ReplyDTO> dtoList = result.get()
        .map(en -> modelMapper.map(en, ReplyDTO.class))
        .collect(Collectors.toList());

        // 가공된 dtoList를 이용하여 PageResponseDTO를 생성 (댓글데이터, 총댓글수, ReplyPageRequestDTO(bno 페이지 사이즈 last))
        PageResponseDTO<ReplyDTO> responseDTO = new PageResponseDTO<>(dtoList, totalReplyCount, requestDTO);
        // PageResponseDTO의 페이지번호를 pageNum로 설정함
        responseDTO.setPage(pageNum);

        // 최종 댓글 데이터 반환
        return responseDTO;
    }

    @Override
    public Long register(ReplyDTO replyDTO) {
        
        // replyDTO를 Reply 엔티티클래스 형태로 변환(map)하여 Reply reply에 담는다
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        log.info("reply...");
        log.info(reply);

        Long newRno = replyRepository.save(reply).getRno();

        return newRno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        
        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply = result.orElseThrow();

        return modelMapper.map(reply, ReplyDTO.class);

    }

    @Override
    public void remove(Long rno) {
        
        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply = result.orElseThrow();

        reply.changeText("해당 글은 삭제되었습니다.");
        reply.changeFile(null);

        replyRepository.save(reply);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        
        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());

        Reply reply = result.orElseThrow();

        reply.changeText(replyDTO.getReplyText());
        reply.changeFile(replyDTO.getReplyFile());

        replyRepository.save(reply);

    }
    
}

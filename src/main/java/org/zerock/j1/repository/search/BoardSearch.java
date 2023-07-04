package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.j1.domain.Board;

public interface BoardSearch {
    
    // 1. 인터페이스 메소드 추가
    Page<Board> search1(String searchType, String keyword, Pageable pageable);

}

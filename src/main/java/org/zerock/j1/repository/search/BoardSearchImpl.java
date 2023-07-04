package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{
    
    public BoardSearchImpl() {
        super(Board.class);
    }

    // 2. 메소드 구현
    @Override
    public Page<Board> search1(String searchType, String keyword, Pageable pageable) {
        
        // compilejava가 일어나서 QBoard가 생성된 다음에 코딩을 해야 함.
        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        if(keyword != null && searchType != null){

            // tc -> [t, c]
            String[] searchArr = searchType.split("");

            // 우선순위 연산자 ( ... ) ...
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {
                switch(type) {
                    // or연산
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            } // end for

            query.where(searchBuilder);
        }

        query.where(board.bno.goe(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        query.where(board.title.contains("1"));

        this.getQuerydsl().applyPagination(pageable, query);

        // where나 join같은게 다 query.~ 에서 들어간다.
        // 실제로 목록데이터를 가져오는건 fetch()다.
        List<Board> list = query.fetch();
        // total
        long count = query.fetchCount();

        log.info(list);
        log.info("count: " + count);

        return new PageImpl<>(list, pageable, count);
    }

}

package org.zerock.j1.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.TodoDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {
    
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testRead() {

        Long tno = 100L;

        // Optional은 null을 대처하기 위해 사용
        Optional<Todo> result = todoRepository.findById(tno);

        Todo entity = result.orElseThrow();

        log.info("ENTITY ------------------");
        log.info(entity);

        TodoDTO dto = modelMapper.map(entity, TodoDTO.class);

        log.info(dto);
    }

    @Test
    public void testInsert(){

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Todo todo = Todo.builder()
            .title("Title"+i)
            .build();

            Todo result = todoRepository.save(todo);

            log.info(result);

        });

    }

    @Test
    public void testPaging(){
        // 페이징은 0부터 시작
        // 정렬에 쓰는 sort의 값은 엔티티 클래스의 멤버변수 이름이다.
        Pageable pageable = 
            PageRequest.of(0, 10, Sort.by("tno").descending());

        // Pageable을 쓰면 리턴 타입은 Page타입.
        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result);

    }

}

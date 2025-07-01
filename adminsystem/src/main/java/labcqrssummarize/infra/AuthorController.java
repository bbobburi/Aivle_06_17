package labcqrssummarize.infra;

import java.util.Optional;
import javax.transaction.Transactional;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

/**
 * Author 관련 관리자 기능을 제공하는 API 컨트롤러
 * - 승인/거부 요청 처리
 * - Aggregate 상태 변화 트리거
 * - 이벤트 발행은 Aggregate 내부 로직에 의해 자동 처리됨
 */
@RestController
@RequestMapping("/authors") // 해당 컨트롤러의 기본 URL 경로 설정
@Transactional // 메서드 단위 트랜잭션 처리 보장
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    /**
     * 작가 승인 API
     * @param id 승인할 작가의 ID
     * 1. 해당 Author 조회
     * 2. approve() 메서드 호출로 상태 변화 및 이벤트 발행
     * 3. 변경된 상태를 DB에 저장
     */
    @PutMapping("/{id}/approve")
    public void approveAuthor(@PathVariable String id) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.approve();
        authorRepository.save(author);
    }

    /**
     * 작가 거부 API
     * @param id 거부할 작가의 ID
     * 1. 해당 Author 조회
     * 2. deny() 메서드 호출로 상태 변화 및 이벤트 발행
     * 3. 변경된 상태를 DB에 저장
     */
    @PutMapping("/{id}/deny")
    public void denyAuthor(@PathVariable String id) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.deny();
        authorRepository.save(author);
    }
}
//>>> Clean Arch / Inbound Adaptor

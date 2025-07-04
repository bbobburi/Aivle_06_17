package labcqrssummarize.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value="/eBookPlatforms")
@Transactional
public class EBookPlatformController {

    private final EBookPlatformRepository eBookPlatformRepository;

    @Autowired
    public EBookPlatformController(EBookPlatformRepository eBookPlatformRepository) {
        this.eBookPlatformRepository = eBookPlatformRepository;
    }
    // EBookPlatform 전체 조회 (pid, registeredAt, ebooks 포함 전체 조회)
    @GetMapping("/all")
    public Iterable<EBookPlatform> getAllEBookPlatforms() {
        return eBookPlatformRepository.findAll();
    }


    // 전자책 열람 요청
    @GetMapping("/{ebookId}")
    public String open(@PathVariable Integer ebookId) {
        Optional<EBookPlatform> optionalEBook = eBookPlatformRepository.findById(ebookId);

        if (optionalEBook.isEmpty()) {
            return "열람 실패: 해당 전자책이 존재하지 않습니다.";
        }

        EBookPlatform eBook = optionalEBook.get();

        RequestOpenEBookAccept event = new RequestOpenEBookAccept();
        event.setUserId("test-user");  // 또는 로그인된 사용자 ID로 설정
        event.setSubscriberId("test-subscriber");  // 테스트용이거나 추후 real 값으로 설정

        boolean result = eBook.openEBook(event);
        eBookPlatformRepository.save(eBook);

        return result ? "전자책 열람 성공!" : "전자책 열람 실패: 아직 등록되지 않았습니다.";
    }
}
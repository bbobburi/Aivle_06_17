package labcqrssummarize.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EBookPlatformController {

    private final EBookPlatformRepository eBookPlatformRepository;

    @Autowired
    public EBookPlatformController(EBookPlatformRepository eBookPlatformRepository) {
        this.eBookPlatformRepository = eBookPlatformRepository;
    }

    // 전자책 등록 요청
    @PostMapping("/ebooks/register/{ebookId}")
    public String register(@PathVariable Integer ebookId) {
        Optional<EBookPlatform> optionalEBook = eBookPlatformRepository.findById(ebookId);

        if (optionalEBook.isEmpty()) {
            return "등록 실패: 해당 전자책이 존재하지 않습니다.";
        }

        EBookPlatform eBook = optionalEBook.get();
        try {
            eBook.register();
            eBookPlatformRepository.save(eBook);
            return "전자책이 등록되었습니다.";
        } catch (IllegalStateException e) {
            return "등록 실패: " + e.getMessage();
        }
    }

    // 전자책 열람 요청
    @GetMapping("/ebooks/open/{ebookId}")
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

    // 전자책 비공개 요청
    @DeleteMapping("/ebooks/remove/{ebookId}")
    public String remove(@PathVariable Integer ebookId) {
        Optional<EBookPlatform> optionalEBook = eBookPlatformRepository.findById(ebookId);

        if (optionalEBook.isEmpty()) {
            return "비공개 실패: 해당 전자책이 존재하지 않습니다.";
        }

        EBookPlatform eBook = optionalEBook.get();

        // 실제로 삭제 처리하므로 상태 업데이트 생략
        // eBook.updateStatus(EBookPlatform.EbookStatus.REMOVED);
        
        eBookPlatformRepository.delete(eBook);

        return "전자책이 비공개(삭제) 처리되었습니다.";
    }

    // 전자책 상태 조회
    @GetMapping("/ebooks/status/{ebookId}")
    public String checkStatus(@PathVariable Integer ebookId) {
        Optional<EBookPlatform> optionalEBook = eBookPlatformRepository.findById(ebookId);

        if (optionalEBook.isEmpty()) {
            return "조회 실패: 해당 전자책이 존재하지 않습니다.";
        }

        EBookPlatform eBook = optionalEBook.get();
        return "전자책 상태: " + eBook.getStatus();
    }
}
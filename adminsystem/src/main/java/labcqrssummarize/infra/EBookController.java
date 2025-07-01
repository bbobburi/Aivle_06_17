package labcqrssummarize.infra;

import javax.transaction.Transactional;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value = "/ebooks") // 주석 해제
@Transactional
public class EBookController {

    @Autowired
    EBookRepository eBookRepository;

    /**
     * 출간 승인 API
     * @param id 출간 승인할 전자책 ID
     */
    @PutMapping("/{id}/approve-publish")
    public void approvePublish(@PathVariable String id) {
        EBook ebook = eBookRepository.findById(id).orElseThrow();
        ebook.approvePublish();
        eBookRepository.save(ebook);
    }

    /**
     * 출간 거부 API
     * @param id 출간 거부할 전자책 ID
     */
    @PutMapping("/{id}/deny-publish")
    public void denyPublish(@PathVariable String id) {
        EBook ebook = eBookRepository.findById(id).orElseThrow();
        ebook.denyPublish();
        eBookRepository.save(ebook);
    }
}
//>>> Clean Arch / Inbound Adaptor

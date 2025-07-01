package labcqrssummarize.infra;

import labcqrssummarize.config.kafka.KafkaProcessor;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eBooks")
@Transactional
public class EBookController {

    @Autowired
    private EBookRepository eBookRepository;

    @Autowired
    private KafkaProcessor kafkaProcessor;

    // 1. 전자책 전체 목록 조회
    @GetMapping
    public List<EBook> getAllEBooks() {
        return (List<EBook>) eBookRepository.findAll();
    }

    // 2. 전자책 상세 조회
    @GetMapping("/{ebookId}")
    public ResponseEntity<EBook> getEBookById(@PathVariable String ebookId) {
        Optional<EBook> ebook = eBookRepository.findById(ebookId);
        return ebook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. 출간 요청 (AI 처리 시작 요청)
    @PostMapping("/publish")
    public ResponseEntity<String> requestPublish(@RequestBody EBook publishRequest) {
        // 3-1. 저장 또는 업데이트
        EBook ebook = eBookRepository.save(publishRequest);

        // 3-2. Kafka 출간 승인 이벤트 발행
        RequestPublishApproved event = new RequestPublishApproved();
        event.setEbookId(ebook.getEbookId());
        event.setAuthorId(ebook.getAuthorId());
        event.setPublicationStatus(true);  // 출간 승인 요청 상태 예시

        MessageChannel outputChannel = kafkaProcessor.outboundTopic();
        outputChannel.send(
            MessageBuilder.withPayload(event)
                .setHeader("type", "RequestPublishApproved")
                .build()
        );

        return ResponseEntity.ok("출간 요청이 접수되었습니다. EBook ID: " + ebook.getEbookId());
    }

    // 4. AI 처리 상태 조회 (예: 요약, 표지 생성, 가격 산정 등 완료 여부 확인용)
    @GetMapping("/{ebookId}/status")
    public ResponseEntity<String> getAIProcessingStatus(@PathVariable String ebookId) {
        Optional<EBook> ebookOpt = eBookRepository.findById(ebookId);
        if (ebookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EBook ebook = ebookOpt.get();

        // 간단 예시: 출간 승인 여부, 요약, 표지, 가격 모두 완료됐는지 확인
        boolean isReady = ebook.getSummary() != null
            && ebook.getCoverImage() != null
            && ebook.getPrice() != null
            && ebook.getCategory() != null
            && Boolean.TRUE.equals(ebook.getIsPublicationApproved());

        String status = isReady ? "AI 처리 완료" : "AI 처리 중 또는 미완료";

        return ResponseEntity.ok(status);
    }

    // 5. 전자책 수정 (선택)
    @PutMapping("/{ebookId}")
    public ResponseEntity<EBook> updateEBook(
        @PathVariable String ebookId,
        @RequestBody EBook updateRequest
    ) {
        Optional<EBook> ebookOpt = eBookRepository.findById(ebookId);
        if (ebookOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EBook ebook = ebookOpt.get();
        ebook.setTitle(updateRequest.getTitle());
        ebook.setAuthorId(updateRequest.getAuthorId());
        ebook.setContent(updateRequest.getContent());
        ebook.setIsPublicationApproved(updateRequest.getIsPublicationApproved());
        // 기타 필요한 필드 업데이트

        EBook updated = eBookRepository.save(ebook);
        return ResponseEntity.ok(updated);
    }
}

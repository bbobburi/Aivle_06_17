package labcqrssummarize.infra;

import javax.transaction.Transactional;
import labcqrssummarize.config.kafka.KafkaProcessor;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * PolicyHandler
 * - Kafka에서 발행되는 Event를 수신해 비즈니스 정책에 따라 반응
 * - Aggregate 상태 변화는 Aggregate 내부의 비즈니스 메서드로만 처리
 * - Pub/Sub 기반의 MSA 구조에서 핵심 연결 역할
 */
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    EBookRepository eBookRepository;

    /**
     * 테스트용 전체 메시지 로깅 (필요 없으면 삭제 가능)
     */
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    /**
     * [작가 등록 접수] 이벤트 수신
     * - 등록된 작가 정보를 바탕으로 상태 변화 트리거
     * - 신규 작가라면 생성, 기존 작가라면 무시 또는 로직 분기 가능
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RegisteredAuthor'"
    )
    public void wheneverRegisteredAuthor_HandleAuthorRegistrationRequest(
        @Payload RegisteredAuthor registeredAuthor
    ) {
        System.out.println("\n\n##### listener HandleAuthorRegistrationRequest : " + registeredAuthor + "\n\n");

        Author author = authorRepository.findById(registeredAuthor.getAuthorId())
            .orElseGet(() -> {
                Author newAuthor = new Author();
                newAuthor.setAuthorId(registeredAuthor.getAuthorId());
                newAuthor.setName(registeredAuthor.getName());
                newAuthor.setUserId(registeredAuthor.getUserId());
                return newAuthor;
            });

        // 여기서는 자동 승인 제거하고, 단순 등록만 처리
        authorRepository.save(author);
    }

    /**
     * [콘텐츠 작성됨] 이벤트 수신
     * - 관리자 시스템에서 콘텐츠 검열 또는 승인 처리 트리거 가능
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='WrittenContent'"
    )
    public void wheneverWrittenContent_HandleContentRegistrationRequest(
        @Payload WrittenContent writtenContent
    ) {
        System.out.println("\n\n##### listener HandleContentRegistrationRequest : " + writtenContent + "\n\n");

        EBook ebook = eBookRepository.findById(writtenContent.getEbookId()).orElseThrow();
        
        // 콘텐츠 승인 처리 (실제 검열 로직 대신 단순 승인 예시)
        ebook.approveContent();
        
        eBookRepository.save(ebook);
    }

    /**
     * [출간 요청] 이벤트 수신
     * - 관리자가 실제로 출간 승인/거부를 결정하는 Command를 보내는 흐름을 기대
     * - 이 Policy는 출간 요청 자체를 수신하고, 후속 로직 트리거 가능
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RequestPublish'"
    )
    public void wheneverRequestPublish_HandlePublishRequest(
        @Payload RequestPublish requestPublish
    ) {
        System.out.println("\n\n##### listener HandlePublishRequest : " + requestPublish + "\n\n");

        // 출간 요청 수신 후 필요한 후속 처리
        // 관리자 Command로 승인/거부를 결정하는 구조가 더 정석
    }
}

package labcqrssummarize.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import labcqrssummarize.config.kafka.KafkaProcessor;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Date;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    EBookPlatformRepository eBookPlatformRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 표지 이미지 생성 완료되었는지 확인
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GeneratedEBookCover'"
    )
    public void wheneverGeneratedEBookCover_CheckEBookStatus(
        @Payload GeneratedEBookCover generatedEBookCover
    ) {
        System.out.println("##### [GeneratedEBookCover] received: " + generatedEBookCover);

        // EBookId 받아와서 저장소에서 찾기, 없다면 함수 종료
        EBookPlatform ebook = eBookPlatformRepository.findById(
            Integer.parseInt(generatedEBookCover.getEbookId())
        ).orElse(null);
        if (ebook == null) return;
        
        // EBookPlatform 함수
        ebook.markCoverGenerated();
        if (ebook.isReadyForPublish()) {
            ebook.register();
        }

        eBookPlatformRepository.save(ebook);
    }

    // 전자책 내용 요약 완료되었는지 확인
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SummarizedContent'"
    )
    public void wheneverSummarizedContent_CheckEBookStatus(
        @Payload SummarizedContent summarizedContent
    ) {
        System.out.println("##### [SummarizedContent] received: " + summarizedContent);

        EBookPlatform ebook = eBookPlatformRepository.findById(
            Integer.parseInt(summarizedContent.getEbookId())
        ).orElse(null);
        if (ebook == null) return;

        ebook.markContentSummarized();
        if (ebook.isReadyForPublish()) {
            ebook.register();
        }

        eBookPlatformRepository.save(ebook);
    }

    // 가격과 카테고리 확인
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='EstimatedPriceAndCategory'"
    )
    public void wheneverEstimatedPriceAndCategory_CheckEBookStatus(
        @Payload EstimatedPriceAndCategory estimatedPriceAndCategory
    ) {
        System.out.println("##### [EstimatedPriceAndCategory] received: " + estimatedPriceAndCategory);

        EBookPlatform ebook = eBookPlatformRepository.findById(
            Integer.parseInt(estimatedPriceAndCategory.getEbookId())
        ).orElse(null);
        if (ebook == null) return;

        ebook.markPriceAndCategorySet();
        if (ebook.isReadyForPublish()) {
            ebook.register();
        }

        eBookPlatformRepository.save(ebook);
    }

    // 전자책 비공개
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ListOutEbookRequested'"
    )
    public void wheneverListOutEbookRequested_RequestPrivateStatus(
        @Payload ListOutEbookRequested listOutEbookRequested
    ) {
        System.out.println("##### [ListOutEbookRequested] received: " + listOutEbookRequested);

        // 삭제를 원하는 EBookId 받아오기
        Integer ebookId = Integer.parseInt(listOutEbookRequested.getEBookId());

        EBookPlatform ebook = eBookPlatformRepository.findById(ebookId).orElse(null);
        if (ebook == null) {
            System.out.println("해당 ID의 전자책이 존재하지 않습니다: " + ebookId);
            return;
        }

        // 상태 업데이트 및 저장
        ebook.updateStatus(EBookPlatform.EbookStatus.REMOVED);
        eBookPlatformRepository.save(ebook);

        System.out.println("전자책이 비공개 처리되었습니다. ID: " + ebookId);
    }

    // 전자책 열람 요청
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RequestOpenEBookAccept'"
    )
    public void wheneverRequestOpenEBookAccept_RequestOpenEBook(
        @Payload RequestOpenEBookAccept requestOpenEBookAccept
    ) {
        System.out.println("##### [RequestOpenEBookAccept] received: " + requestOpenEBookAccept);
        
        if (requestOpenEBookAccept.getEbookId() == null) {
            System.out.println("전자책 ID가 누락되었습니다.");
            return;
        }

        Integer ebookId = Integer.parseInt(requestOpenEBookAccept.getEbookId());

        EBookPlatform ebook = eBookPlatformRepository.findById(ebookId).orElse(null);
        if (ebook == null) {
            System.out.println("전자책 ID가 이벤트에 없습니다.");
            return;
        }

        ebook.openEBook(requestOpenEBookAccept);
        eBookPlatformRepository.save(ebook);
    }

    // 아래 이벤트는 클래스 정의가 없으므로 안전하게 로그만 출력
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='EBookPlatformOpened'"
    )
    public void wheneverEBookPlatformOpened_LogSuccess(@Payload String rawJson) {
        System.out.println("<< 전자책 열람 성공 처리 >>: " + rawJson + "\n\n");
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='EBookPlatformOpenFailed'"
    )
    public void wheneverEBookPlatformRegistered_LogFail(@Payload String rawJson) {
        System.out.println("<< 전자책 열람 실패 처리 >>: " + rawJson + "\n\n");
    }
}
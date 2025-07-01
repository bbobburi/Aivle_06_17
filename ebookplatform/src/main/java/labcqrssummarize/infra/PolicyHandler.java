package labcqrssummarize.infra;

import javax.transaction.Transactional;
import labcqrssummarize.config.kafka.KafkaProcessor;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import labcqrssummarize.domain.SubscriptionStatus;

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

        EBookPlatform ebook = eBookPlatformRepository.findById(
            Integer.parseInt(generatedEBookCover.getEbookId())
        ).orElse(null);
        if (ebook == null) return;
        
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

        Integer ebookId = Integer.parseInt(listOutEbookRequested.getEBookId());

        EBookPlatform ebook = eBookPlatformRepository.findById(ebookId).orElse(null);
        if (ebook == null) {
            System.out.println("해당 ID의 전자책이 존재하지 않습니다: " + ebookId);
            return;
        }

        ebook.updateStatus(EBookPlatform.EbookStatus.REMOVED);
        eBookPlatformRepository.save(ebook);

        System.out.println("전자책이 비공개 처리되었습니다. ID: " + ebookId);
    }

    // 전자책 열람 요청 처리 (구독 상태 및 포인트 차감 처리)

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

        SubscriptionStatus subscriptionStatus = requestOpenEBookAccept.getSubscriptionStatus();

        // 구독중이면 바로 열람 성공 처리
        if (subscriptionStatus == SubscriptionStatus.SUBSCRIBED) {
            ebook.openEBook(requestOpenEBookAccept);
            eBookPlatformRepository.save(ebook);

            HandleEBookViewed openedEvent = new HandleEBookViewed(ebook);
            openedEvent.setUserId(requestOpenEBookAccept.getUserId());
            openedEvent.publishAfterCommit();
            return;
        }

        // 구독중이 아니면 포인트 차감 요청 이벤트 발행
        int price = ebook.getPrice() != null ? ebook.getPrice() : 0;

        DeductPoint deductPoint = new DeductPoint();
        deductPoint.setUserId(requestOpenEBookAccept.getUserId());
        deductPoint.setPoint(price);
        deductPoint.setEbookId(requestOpenEBookAccept.getEbookId());
        deductPoint.publishAfterCommit();

        // 포인트 차감 성공/실패 이벤트 수신 후 별도 처리 필요
    }

    // 전자책 열람 성공 이벤트 수신 로그
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HandleEBookViewed'"
    )
    public void wheneverHandleEBookViewed_LogSuccess(@Payload HandleEBookViewed event) {
        System.out.println("<< 전자책 열람 성공 처리 >>: " + event + "\n\n");
    }

    // 전자책 열람 실패 이벤트 수신 로그
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HandleEBookViewFailed'"
    )
    public void wheneverHandleEBookViewFailed_LogFail(@Payload HandleEBookViewFailed event) {
        System.out.println("<< 전자책 열람 실패 처리 >>: " + event + "\n\n");
    }
}



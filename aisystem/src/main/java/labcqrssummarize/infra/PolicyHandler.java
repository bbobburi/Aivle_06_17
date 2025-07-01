package labcqrssummarize.infra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import labcqrssummarize.config.kafka.KafkaProcessor;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    EBookRepository eBookRepository;

    @Autowired
    OpenAIService openAIService;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRequestPublishApproved_GenerateContentWithAi(@Payload String payload) {
        System.out.println("\n##### [수신] Kafka 메시지 도착: " + payload + "\n");
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(payload);

            if (jsonNode.has("type") && "RequestPublishApproved".equals(jsonNode.get("type").asText())) {
                String ebookId = jsonNode.get("ebookId").asText();
                System.out.println("✅ AI 처리 시작: ebookId=" + ebookId + "\n");

                EBook ebook = eBookRepository.findById(ebookId).orElse(null);
                if (ebook == null) {
                    System.out.println("❌ EBook not found: " + ebookId);
                    return;
                }

                // 1) 전자책 요약
                System.out.println("▶️ 요약 생성 요청");
                String summary = openAIService.summarizeText(ebook.getContent());
                SummarizedContent summaryEvent = new SummarizedContent();
                summaryEvent.setEbookId(ebookId);
                summaryEvent.setTitle(ebook.getTitle());
                summaryEvent.setContent(ebook.getContent());
                summaryEvent.setSummary(summary);
                summaryEvent.publishAfterCommit();

                // 2) 표지 이미지 생성
                System.out.println("▶️ 표지 이미지 생성 요청");
                String coverImageUrl = openAIService.generateCoverImage(ebook.getTitle());
                ebook.setCoverImage(coverImageUrl);
                new GeneratedEBookCover(ebook).publishAfterCommit();

                // 3) PDF 저장
                try {
                    System.out.println("▶️ PDF 생성 시작");
                    byte[] pdfBytes = openAIService.generateSummaryPdf(ebook.getTitle(), summary);
                    Path pdfDir = Path.of("pdfs");
                    if (!Files.exists(pdfDir)) Files.createDirectories(pdfDir);
                    Path pdfPath = pdfDir.resolve(ebookId + ".pdf");
                    try (FileOutputStream fos = new FileOutputStream(pdfPath.toFile())) {
                        fos.write(pdfBytes);
                    }
                    System.out.println("✅ PDF 저장 완료: " + pdfPath.toAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 4) 카테고리 및 가격 추정
                System.out.println("▶️ 카테고리 및 가격 추정 시작");
                String category = openAIService.estimateCategory(summary);
                Integer price = openAIService.estimatePrice(summary);

                EstimatiedPriceAndCategory priceAndCategoryEvent = new EstimatiedPriceAndCategory();
                priceAndCategoryEvent.setEbookId(ebookId);
                priceAndCategoryEvent.setSummary(summary);
                priceAndCategoryEvent.setContent(ebook.getContent());
                priceAndCategoryEvent.setCategory(category);
                priceAndCategoryEvent.setPrice(price);
                priceAndCategoryEvent.publishAfterCommit();

                // 5) DB 최종 저장
                ebook.setSummary(summary);
                ebook.setCategory(category);
                ebook.setPrice(price);
                eBookRepository.save(ebook);

                System.out.println("✅ AI 처리 완료: " + ebookId + "\n");

                // ✅ Kafka 비동기 처리 대기 시간 확보
                Thread.sleep(3000);
            } else {
                System.out.println("⚠️ 메시지 타입이 RequestPublishApproved가 아님. 무시됨.");
            }
        } catch (Exception e) {
            System.err.println("❗ Kafka 메시지 처리 실패");
            e.printStackTrace();
        }
    }
}

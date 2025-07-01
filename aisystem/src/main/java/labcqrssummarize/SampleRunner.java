package labcqrssummarize;

import labcqrssummarize.domain.EBook;
import labcqrssummarize.domain.EBookRepository;
import labcqrssummarize.infra.OpenAIService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class SampleRunner implements CommandLineRunner {

    @Autowired
    private EBookRepository eBookRepository;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void run(String... args) {
        System.out.println("✅ SampleRunner 시작됨.");

        // 샘플 EBook 생성
        EBook ebook = new EBook();
        ebook.setEbookId(UUID.randomUUID().toString());
        ebook.setTitle("메리골드 마음 식물원");
        ebook.setAuthor("윤정은");
        ebook.setContent("신작 『메리골드 마음 식물원』은 앞선 ‘마음 세탁소’와 ‘마음 사진관’의 마법처럼 신비로운 서사와 따뜻한 정서를 잇는 동시에 ‘식물’이라는 새로운 은유로 치유와 성장의 의미를 한층 깊이 있게 풀어낸다. ‘마음 세탁소’에서 상처의 얼룩을 깨끗이 지우고, ‘마음 사진관’에서 놓치고 있던 행복을 사진에 담아 보여주었다면, ‘마음 식물원’에서는 지우지 못한 깊은 아픔과 슬픔을 꽃과 나무로 피워내는 과정을 통해 돌봄과 회복, 성장의 감동을 그려낸다.");

        // 1️⃣ 요약 생성
        String summary = openAIService.summarizeText(ebook.getContent());
        ebook.setSummary(summary);
        System.out.println("✅ GPT 요약 결과: " + summary);

        // 2️⃣ 가격/카테고리 추정
        Integer price = openAIService.estimatePrice(summary);
        String category = openAIService.estimateCategory(summary);
        ebook.setPrice(price);
        ebook.setCategory(category);
        System.out.println("✅ 가격: " + price + ", 카테고리: " + category);

        // 3️⃣ AI 표지 이미지 생성
        String coverImageUrl = openAIService.generateCoverImage("전자책 제목: " + ebook.getTitle());
        ebook.setCoverImage(coverImageUrl);
        System.out.println("✅ 표지 이미지 생성 완료: " + coverImageUrl);

        // 4️⃣ PDF 저장
        try {
            byte[] pdfBytes = openAIService.generateSummaryPdf(ebook.getTitle(), summary);
            Path outputPath = Paths.get("output", ebook.getEbookId() + ".pdf");
            Files.createDirectories(outputPath.getParent());
            Files.write(outputPath, pdfBytes);
            System.out.println("✅ PDF 저장 완료: " + outputPath.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("❌ PDF 저장 실패: " + e.getMessage());
        }

        // 5️⃣ 저장
        eBookRepository.save(ebook);
        System.out.println("✅ EBook 저장 완료. ID: " + ebook.getId());

        // 6️⃣ Kafka 발행
        String message = String.format(
            "{ \"type\": \"RequestPublishApproved\", \"ebookId\": \"%s\" }",
            ebook.getEbookId()
        );
        kafkaTemplate.send("labcqrssummarize", message);
        System.out.println("📤 Kafka 이벤트 발행 완료: " + message);
    }
}

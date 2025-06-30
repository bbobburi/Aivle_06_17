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
        ebook.setTitle("AI와 미래 사회");
        ebook.setAuthor("홍길동");
        ebook.setContent("인공지능이 세상을 어떻게 바꾸고 있는지에 대한 고찰");

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

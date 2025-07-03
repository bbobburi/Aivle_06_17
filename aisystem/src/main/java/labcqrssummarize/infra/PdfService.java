package labcqrssummarize.infra;

import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    // 이모지 제거 함수 (모든 특수 이모지 문자 제거)
    private String removeEmojis(String text) {
        return text.replaceAll("[\\p{So}\\p{Cn}]+", "");
    }

    /**
     * 주어진 제목과 본문 텍스트를 PDF로 변환하여 바이트 배열로 반환합니다.
     * NanumGothic 폰트는 classpath의 /fonts/NanumGothic.ttf에서 로드합니다.
     */
    public byte[] createPdfFromText(String title, String content) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // 나눔고딕 폰트 로드
            PDType0Font font = PDType0Font.load(document,
                PdfService.class.getResourceAsStream("/fonts/NanumGothic.ttf"));

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // 제목 출력
                contentStream.beginText();
                contentStream.setFont(font, 18);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText(removeEmojis(title));
                contentStream.endText();

                // 본문 출력
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(50, 670);
                contentStream.showText(removeEmojis(content));
                contentStream.endText();
            }

            // PDF를 바이트 배열로 저장
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                document.save(baos);
                return baos.toByteArray();
            }
        }
    }
}


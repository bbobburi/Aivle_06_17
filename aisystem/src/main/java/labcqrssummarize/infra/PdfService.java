package labcqrssummarize.infra;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class PdfService {

    // 이모지 제거 함수 (모든 특수 이모지 문자 제거)
    private String removeEmojis(String text) {
        return text.replaceAll("[\\p{So}\\p{Cn}]+", "");
    }

    public byte[] createPdfFromText(String title, String content) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // 나눔고딕 폰트 로드
            PDType0Font font = PDType0Font.load(document, new File("src/main/resources/fonts/NanumGothic.ttf"));

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(font, 18);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText(removeEmojis(title));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(50, 670);
                contentStream.showText(removeEmojis(content));
                contentStream.endText();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        }
    }
}

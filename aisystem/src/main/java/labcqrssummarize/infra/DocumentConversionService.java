package labcqrssummarize.infra;

import org.springframework.stereotype.Service;
import java.io.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

@Service
public class DocumentConversionService {

    // PDF 변환 (간단 텍스트 기반 PDF 생성)
    public String convertToPdf(String ebookId, String content) throws IOException {
        String pdfPath = System.getProperty("java.io.tmpdir") + "/" + ebookId + ".pdf";

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                PDType0Font font = PDType0Font.load(doc, getClass().getResourceAsStream("/fonts/NanumGothic.ttf"));

                contents.beginText();
                contents.setFont(font, 12);
                contents.setLeading(14f);
                contents.newLineAtOffset(25, 750);

                try (BufferedReader reader = new BufferedReader(new StringReader(content))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contents.showText(line);
                        contents.newLine();
                    }
                }

                contents.endText();
            }

            doc.save(pdfPath);
        }

        return pdfPath;
    }
}

package labcqrssummarize.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    @Autowired
    private PdfService pdfService;

    public OpenAIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public String summarizeText(String content) {
        System.out.println("🧠 [OpenAIService] 요약 요청 시작");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "다음 내용을 간결하고 명확하게 요약해줘:\n" + content);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", new Object[]{message});
        requestBody.put("max_tokens", 500);
        requestBody.put("temperature", 0.7);

        Map<String, Object> response = webClient
            .post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        System.out.println("📩 [OpenAIService] 요약 응답 수신 완료");

        if (response != null && response.containsKey("choices")) {
            var choices = (java.util.List<Map<String, Object>>) response.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> messageResp = (Map<String, Object>) choices.get(0).get("message");
                if (messageResp != null && messageResp.containsKey("content")) {
                    String summary = (String) messageResp.get("content");
                    System.out.println("✅ [OpenAIService] 요약 결과: " + summary);
                    return summary;
                }
            }
        }

        System.out.println("⚠️ [OpenAIService] 요약 실패, 빈 문자열 반환");
        return "";
    }

    public String generateCoverImage(String title) {
        System.out.println("🖼️ [OpenAIService] 표지 이미지 생성 요청: " + title);

        String prompt = String.format(
            "Create a soft, elegant, and emotionally resonant book cover illustration for a book titled \"%s\". " +
            "Use gentle color palettes like pastel tones and abstract shapes that suggest imagination, calmness, or emotional depth. " +
            "Do not include any text, letters, or characters in the image. Focus on creating a dreamy, minimalistic, and artistic atmosphere.",
            title
        );

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("n", 1);
        requestBody.put("size", "1024x1024");

        Map<String, Object> response = webClient
            .post()
            .uri("/images/generations")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        System.out.println("📩 [OpenAIService] 이미지 응답 수신 완료");

        if (response != null && response.containsKey("data")) {
            var dataList = (java.util.List<Map<String, Object>>) response.get("data");
            if (!dataList.isEmpty() && dataList.get(0).containsKey("url")) {
                String url = (String) dataList.get(0).get("url");
                System.out.println("✅ [OpenAIService] 이미지 URL: " + url);
                return url;
            }
        }

        System.out.println("⚠️ [OpenAIService] 이미지 생성 실패, 빈 문자열 반환");
        return "";
    }

    public String estimateCategory(String summary) {
        System.out.println("📚 [OpenAIService] 카테고리 추정 요청");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "다음 줄거리를 기반으로 적절한 전자책 카테고리를 한 단어로 추천해줘:\n" + summary);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", new Object[]{message});
        requestBody.put("max_tokens", 20);
        requestBody.put("temperature", 0.5);

        Map<String, Object> response = webClient
            .post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        System.out.println("📩 [OpenAIService] 카테고리 응답 수신 완료");

        if (response != null && response.containsKey("choices")) {
            var choices = (java.util.List<Map<String, Object>>) response.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> messageResp = (Map<String, Object>) choices.get(0).get("message");
                if (messageResp != null && messageResp.containsKey("content")) {
                    String category = ((String) messageResp.get("content")).trim();
                    System.out.println("✅ [OpenAIService] 추정 카테고리: " + category);
                    return category;
                }
            }
        }

        System.out.println("⚠️ [OpenAIService] 카테고리 추정 실패, Unknown 반환");
        return "Unknown";
    }

    public Integer estimatePrice(String summary) {
        System.out.println("💰 [OpenAIService] 가격 추정 요청");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "다음 전자책 줄거리 기반으로 적절한 구독료를 '숫자만' 한국 원화로 정수 형태로 추천해줘. 예: 1500\n" + summary);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", new Object[]{message});
        requestBody.put("max_tokens", 10);
        requestBody.put("temperature", 0.3);

        Map<String, Object> response = webClient
            .post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        System.out.println("📩 [OpenAIService] 가격 응답 수신 완료");

        if (response != null && response.containsKey("choices")) {
            var choices = (java.util.List<Map<String, Object>>) response.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> messageResp = (Map<String, Object>) choices.get(0).get("message");
                if (messageResp != null && messageResp.containsKey("content")) {
                    try {
                        String priceStr = ((String) messageResp.get("content")).replaceAll("[^0-9]", "");
                        if (!priceStr.isEmpty()) {
                            int price = Integer.parseInt(priceStr);
                            System.out.println("✅ [OpenAIService] 추정 가격: " + price);
                            return price;
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ [OpenAIService] 가격 파싱 실패: " + e.getMessage());
                    }
                }
            }
        }

        System.out.println("⚠️ [OpenAIService] 가격 추정 실패, 기본값 1000 반환");
        return 1000;
    }

    public byte[] generateSummaryPdf(String title, String summaryText) throws IOException {
        System.out.println("📄 [OpenAIService] PDF 생성 요청: 제목=" + title);
        byte[] pdfBytes = pdfService.createPdfFromText(title, summaryText);
        System.out.println("✅ [OpenAIService] PDF 생성 완료 (바이트 크기: " + pdfBytes.length + ")");
        return pdfBytes;
    }
}

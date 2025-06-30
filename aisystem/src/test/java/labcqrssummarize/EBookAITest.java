package labcqrssummarize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import labcqrssummarize.domain.RequestPublishApproved;
import labcqrssummarize.domain.EBook;
import labcqrssummarize.domain.EBookRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.annotation.DirtiesContext;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
public class EBookAITest {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Autowired
    private EBookRepository ebookRepository;

    private KafkaTemplate<String, byte[]> createKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, byte[]> factory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(factory);
    }

    @Test
    public void testCreateEBookAndCheckDB() throws Exception {
        KafkaTemplate<String, byte[]> kafkaTemplate = createKafkaTemplate();

        // 고유한 ebookId 생성
        String ebookId = "test-ebook-" + System.currentTimeMillis();

        // 이벤트 생성
        RequestPublishApproved event = new RequestPublishApproved();
        event.setEbookId(ebookId);
        event.setAuthorId("test-author-001");
        event.setPublicationStatus(true);

        // JSON 직렬화 및 type 필드 추가
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.valueToTree(event);
        jsonNode.put("type", "RequestPublishApproved");
        String payload = mapper.writeValueAsString(jsonNode);

        // Kafka 전송
        kafkaTemplate.send("labcqrssummarize", payload.getBytes(StandardCharsets.UTF_8)).get();
        System.out.println("✅ Kafka 메시지 전송 완료: " + payload);

        // 👉 DB 반영 대기 (비동기 AI 처리 → Kafka 이벤트 → 저장까지 지연 고려)
        sleep(5000);  // 5초 대기

        // ✅ DB 조회 및 검증
        EBook result = ebookRepository.findById(ebookId).orElse(null);
        assertNotNull(result, "❌ EBook DB에 저장되지 않았습니다.");
        assertNotNull(result.getSummary(), "❌ 요약 결과가 비어 있습니다.");
        assertNotNull(result.getCategory(), "❌ 카테고리가 비어 있습니다.");
        assertTrue(result.getPrice() > 0, "❌ 가격이 올바르게 설정되지 않았습니다.");

        System.out.println("✅ DB 저장 결과 확인 완료: " + result);
    }
}

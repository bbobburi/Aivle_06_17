package labcqrssummarize;

import labcqrssummarize.infra.PolicyHandler;
import labcqrssummarize.domain.RegisteredAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    private PolicyHandler policyHandler;

    @Test
    public void testHandleAuthorRegistration() {
        RegisteredAuthor event = new RegisteredAuthor();
        event.setAuthorId("test-author");
        event.setName("테스트 작가");
        event.setUserId("test-user");

        policyHandler.wheneverRegisteredAuthor_HandleAuthorRegistrationRequest(event);
    }
}

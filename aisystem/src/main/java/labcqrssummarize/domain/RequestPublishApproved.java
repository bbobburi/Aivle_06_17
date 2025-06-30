package labcqrssummarize.domain;

import labcqrssummarize.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RequestPublishApproved extends AbstractEvent {

    private String ebookId;
    private String authorId;
    private Object publicationStatus;

    @Override
    public void publish() {
        System.out.println("🔥 Kafka 이벤트 발행 직전: " + this.getEventType() + " => " + this.toJson());
        super.publish();
    }
}

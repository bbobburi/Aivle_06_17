package labcqrssummarize.domain;

import labcqrssummarize.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;
import labcqrssummarize.domain.publicationStatus;

@Data
@ToString
public class RequestPublishApproved extends AbstractEvent {

    private String ebookId;
    private String authorId;
    private publicationStatus publicationStatus;

    @Override
    public void publish() {
        System.out.println("🔥 Kafka 이벤트 발행 직전: " + this.getEventType() + " => " + this.toJson());
        super.publish();
    }
}

package labcqrssummarize.domain;

import labcqrssummarize.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public class SubscribeRequested extends AbstractEvent {
    private String subscribeId;
    private String subscriberId;
    private SubscriptionStatus subscriptionStatus;
    private SubscriptionType subscriptionType;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;

    public SubscribeRequested(Subscriber agg) {
        super(agg);
        this.subscribeId        = UUID.randomUUID().toString();
        this.subscriberId       = agg.getSubscriberId();
        this.subscriptionStatus = agg.getSubscriptionStatus();
        this.subscriptionType   = agg.getSubscriptionType();
        this.startedAt          = agg.getStartedAt();
        this.expiredAt          = agg.getExpiredAt();
    }

    public SubscribeRequested() {
        super();
    }
}

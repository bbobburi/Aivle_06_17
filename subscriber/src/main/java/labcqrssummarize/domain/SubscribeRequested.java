package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscribeRequested extends AbstractEvent {

    private String subscriberId;
    private String userId;
    private String subscriptionType;
    private Date startedAt;
    private Date expiredAt;
    private subscriptionStatus subscriptionStatus;
    private Email email;

    public SubscribeRequested(Subscriber aggregate) {
        super(aggregate);
    }

    public SubscribeRequested() {
        super();
    }
}
//>>> DDD / Domain Event

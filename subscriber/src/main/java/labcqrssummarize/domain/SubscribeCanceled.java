package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscribeCanceled extends AbstractEvent {

    private String subscriberId;
    private String userId;
    private String subscriptionType;
    private Date expiredAt;
    private subscriptionStatus subscriptionStatus;
    private Email email;

    public SubscribeCanceled(Subscriber aggregate) {
        super(aggregate);
    }

    public SubscribeCanceled() {
        super();
    }
}
//>>> DDD / Domain Event

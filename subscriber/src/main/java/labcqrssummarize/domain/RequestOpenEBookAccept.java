package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RequestOpenEBookAccept extends AbstractEvent {

    private String subscriberId;
    private String userId;
    private ViewHistory viewHistory;
    private membershipType membershipType;
    private subscriptionStatus subscriptionStatus;

    public RequestOpenEBookAccept(Subscriber aggregate) {
        super(aggregate);
    }

    public RequestOpenEBookAccept() {
        super();
    }
}
//>>> DDD / Domain Event

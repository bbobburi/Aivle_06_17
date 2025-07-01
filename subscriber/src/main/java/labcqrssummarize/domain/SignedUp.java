package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SignedUp extends AbstractEvent {

    private String userId;
    private String subscriptionType;
    private ViewHistory viewHistory;
    private membershipType membershipType;
    private subscriptionStatus subscriptionStatus;
    private String password;
    private Email email;
    private String subscriberId;

    public SignedUp(Subscriber aggregate) {
        super(aggregate);
    }

    public SignedUp() {
        super();
    }
}
//>>> DDD / Domain Event

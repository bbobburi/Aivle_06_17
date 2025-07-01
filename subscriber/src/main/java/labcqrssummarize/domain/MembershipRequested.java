package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class MembershipRequested extends AbstractEvent {

    private String userId;
    private membershipType membershipType;
    private Email email;

    public MembershipRequested(Subscriber aggregate) {
        super(aggregate);
    }

    public MembershipRequested() {
        super();
    }
}
//>>> DDD / Domain Event

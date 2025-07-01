package labcqrssummarize.domain;

import labcqrssummarize.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MembershipRequested extends AbstractEvent {

    private String subscriberId;
    private String userId;
    private MembershipType membershipType;
    private String email;

    public MembershipRequested(Subscriber aggregate) {
        super(aggregate);
        this.subscriberId   = aggregate.getSubscriberId();
        this.userId         = aggregate.getUserId();
        this.membershipType = aggregate.getMembershipType();
        this.email          = aggregate.getEmail();
    }

    public MembershipRequested() {
        super();
    }
}

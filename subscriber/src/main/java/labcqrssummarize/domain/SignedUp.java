package labcqrssummarize.domain;

import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

//<<< DDD / Domain Event
@Data
@ToString
public class SignedUp extends AbstractEvent {

    private String subscriberId;
    private String userId;
    private String password;
    private String email;
    private SubscriptionType subscriptionType;
    private MembershipType membershipType;
    private SubscriptionStatus subscriptionStatus;
    private LocalDateTime startedAt;
    private List<ViewHistory> viewHistory;

    public SignedUp(Subscriber aggregate) {
        super(aggregate);
        this.subscriberId = aggregate.getSubscriberId();
        this.userId = aggregate.getUserId();
        this.password = aggregate.getPassword();
        this.email = aggregate.getEmail();
        this.subscriptionType = aggregate.getSubscriptionType();
        this.membershipType = aggregate.getMembershipType();
        this.subscriptionStatus = aggregate.getSubscriptionStatus();
        this.startedAt = aggregate.getStartedAt();
        this.expiredAt = aggregate.getExpiredAt();
        this.viewHistory = aggregate.getViewHistory();

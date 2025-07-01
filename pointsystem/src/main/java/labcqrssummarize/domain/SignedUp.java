package labcqrssummarize.domain;

import labcqrssummarize.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignedUp extends AbstractEvent {

    private String subscriberId;     // 식별자
    private String userId;           // 공통 ID
    private String password;         // 사용 X (보안상 주의)
    private String email;            // 사용 안 함
    private SubscriptionType subscriptionType;
    private MembershipType membershipType;

    public SignedUp() {
        super();
    }
}


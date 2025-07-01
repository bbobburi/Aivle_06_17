package labcqrssummarize.domain;

import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class RequestOpenEBookAccept extends AbstractEvent {

    private String subscriberId;
    private String userId;
    private Object viewHistory;
    private Object membershipType;
    private Object subscriptionStatus;
}

package labcqrssummarize.domain;

import lombok.Data;

@Data
public class SubscriberStatusResponse {
    private String userId;
    private subscriptionStatus subscriptionStatus;
    private boolean success;
    private String message;
} 
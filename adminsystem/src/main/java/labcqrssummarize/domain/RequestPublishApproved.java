package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RequestPublishApproved extends AbstractEvent {

    private String ebookId;
    private String authorId;
    private PublicationStatus publicationStatus;

    public RequestPublishApproved(EBook aggregate) {
        super(aggregate);
        this.ebookId = aggregate.getEbookId();
        this.authorId = aggregate.getAuthorId();
        this.publicationStatus = aggregate.getPublicationStatus();
    }

    public RequestPublishApproved() {
        super();
    }
}
//>>> DDD / Domain Event

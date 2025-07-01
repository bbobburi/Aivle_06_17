package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RequestContentDenied extends AbstractEvent {

    private String ebookId;
    private PublicationStatus publicationStatus;
    private String authorId;

    public RequestContentDenied(EBook aggregate) {
        super(aggregate);
         // 변경될 속성만 this로 명시적으로 세팅
        this.ebookId = aggregate.getEbookId();
        this.authorId = aggregate.getAuthorId();
        this.publicationStatus = aggregate.getPublicationStatus();
    }

    public RequestContentDenied() {
        super();
    }
}
//>>> DDD / Domain Event

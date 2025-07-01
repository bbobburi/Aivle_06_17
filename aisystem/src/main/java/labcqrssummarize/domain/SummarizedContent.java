package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SummarizedContent extends AbstractEvent {

    private String ebookId;
    private String title;
    private String content;
    private String summary;

    public SummarizedContent(EBook aggregate) {
        super(aggregate);
    }

    public SummarizedContent() {
        super();
    }
}
//>>> DDD / Domain Event

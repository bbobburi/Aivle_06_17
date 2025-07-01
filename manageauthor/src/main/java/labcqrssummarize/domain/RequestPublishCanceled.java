package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RequestPublishCanceled extends AbstractEvent {

    private Long id;

    public RequestPublishCanceled(Author aggregate) {
        super(aggregate);
    }

    public RequestPublishCanceled() {
        super();
    }
}
//>>> DDD / Domain Event

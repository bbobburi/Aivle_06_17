package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class RequestPublish extends AbstractEvent {

    private Long id;

    public RequestPublish(Author aggregate) {
        super(aggregate);
    }

    public RequestPublish() {
        super();
    }
}
//>>> DDD / Domain Event

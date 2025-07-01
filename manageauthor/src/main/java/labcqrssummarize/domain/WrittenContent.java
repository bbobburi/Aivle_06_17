package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class WrittenContent extends AbstractEvent {

    private Long id;

    public WrittenContent(Author aggregate) {
        super(aggregate);
    }

    public WrittenContent() {
        super();
    }
}
//>>> DDD / Domain Event

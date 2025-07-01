package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ListOutEBook extends AbstractEvent {

    private Integer pid;
    private String ebooks;

    public ListOutEBook(EBookPlatform aggregate) {
        super(aggregate);
    }

    public ListOutEBook() {
        super();
    }
}
//>>> DDD / Domain Event

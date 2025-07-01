package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class HandleEBookViewFailed extends AbstractEvent {

    private Integer pid;
    private List<String> ebooks;
    private Date registeredAt;
    private Integer price;

    public HandleEBookViewFailed(EBookPlatform aggregate) {
        super(aggregate);
    }

    public HandleEBookViewFailed() {
        super();
    }
}
//>>> DDD / Domain Event

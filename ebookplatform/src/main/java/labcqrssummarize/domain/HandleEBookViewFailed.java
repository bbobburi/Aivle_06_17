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

    public HandleEBookViewFailed(EBookPlatform aggregate) {
        super(aggregate);
        this.pid = aggregate.getPid();
        this.ebooks = aggregate.getEbooks();
        this.registeredAt = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());
    }

    public HandleEBookViewFailed() {
        super();
    }
}

//>>> DDD / Domain Event

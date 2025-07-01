package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ListedUpEBook extends AbstractEvent {

    private Integer pid;
    private String ebooks;
    private String coverImage;
    private Date registeredAt;

    public ListedUpEBook(EBookPlatform aggregate) {
        super(aggregate);
    }

    public ListedUpEBook() {
        super();
    }
}
//>>> DDD / Domain Event

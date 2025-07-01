package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class HandleEBookViewed extends AbstractEvent {

    private Integer pid;
    private String ebooks;
    private Date registeredAt;
    private Integer price;

    public HandleEBookViewed(EBookPlatform aggregate) {
        super(aggregate);
    }

    public HandleEBookViewed() {
        super();
    }
}
//>>> DDD / Domain Event

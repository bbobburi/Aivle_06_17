package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GivenPoint extends AbstractEvent {

    private String userId;
    private Integer point;
    private PointHistory pointHistory;

    public GivenPoint(UserPoint aggregate) {
        super(aggregate);
    }

    public GivenPoint() {
        super();
    }
}
//>>> DDD / Domain Event

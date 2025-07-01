package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ReducedPoint extends AbstractEvent {

    private String userId;
    private Integer point;
    private PointHistory pointHistory;

    public ReducedPoint(UserPoint aggregate) {
        super(aggregate);
    }

    public ReducedPoint() {
        super();
    }
}
//>>> DDD / Domain Event

package labcqrssummarize.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import labcqrssummarize.PointsystemApplication;
import labcqrssummarize.domain.GivenPoint;
import labcqrssummarize.domain.ReducedPoint;
import lombok.Data;

@Entity
@Table(name = "UserPoint_table")
@Data
//<<< DDD / Aggregate Root
public class UserPoint {

    @Id
    private String userId;

    private Integer point;

    private PointHistory pointHistory;

    @PostPersist
    public void onPostPersist() {
        GivenPoint givenPoint = new GivenPoint(this);
        givenPoint.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        ReducedPoint reducedPoint = new ReducedPoint(this);
        reducedPoint.publishAfterCommit();
    }

    public static UserPointRepository repository() {
        UserPointRepository userPointRepository = PointsystemApplication.applicationContext.getBean(
            UserPointRepository.class
        );
        return userPointRepository;
    }
}
//>>> DDD / Aggregate Root

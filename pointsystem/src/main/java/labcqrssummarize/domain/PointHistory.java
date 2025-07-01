package labcqrssummarize.domain;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;


@Entity
@Data

public class PointHistory  {

    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String historyId;
    
    
    
    private String changeAmount;
    
    
    
    private String description;
    
    
    
    private String changedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    protected PointHistory() {
        
    }
    
    protected PointHistory(String changeAmount, String description, String changedAt, Subscriber subscriberSubscriber subscriber) {
        this.changeAmount = changeAmount;
        this.description = description;
        this.changedAt = changedAt;
        this.subscriber = subscriber;;this.subscriber = subscriber;;
    }

    public String getChangeAmount() {
        return changeAmount;
    }
    public String getDescription() {
        return description;
    }
    public String getChangedAt() {
        return changedAt;
    }


}

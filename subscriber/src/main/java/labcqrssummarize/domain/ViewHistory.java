package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Entity
@Data
public class ViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String historyId;

    private String ebookId;

    private String viewedAt;

    private String usedPoint;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id")
    private Subscriber subscriber;

    protected ViewHistory() {}

    protected ViewHistory(
        String historyId,
        String ebookId,
        String viewedAt,
        String usedPoint,
        Subscriber subscriber
    ) {
        this.historyId = historyId;
        this.ebookId = ebookId;
        this.viewedAt = viewedAt;
        this.usedPoint = usedPoint;
        this.subscriber = subscriber;
    }

    public String getHistoryId() {
        return historyId;
    }

    public String getEbookId() {
        return ebookId;
    }

    public String getViewedAt() {
        return viewedAt;
    }

    public String getUsedPoint() {
        return usedPoint;
    }
}

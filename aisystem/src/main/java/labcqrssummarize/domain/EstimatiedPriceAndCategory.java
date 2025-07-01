package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class EstimatiedPriceAndCategory extends AbstractEvent {

    private String ebookId;
    private String summary;
    private String content;
    private Integer price;
    private String category;

    public EstimatiedPriceAndCategory(EBook aggregate) {
        super(aggregate);
    }

    public EstimatiedPriceAndCategory() {
        super();
    }
}
//>>> DDD / Domain Event

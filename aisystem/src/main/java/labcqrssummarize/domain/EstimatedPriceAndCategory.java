package labcqrssummarize.domain;

import labcqrssummarize.domain.EBook;
import labcqrssummarize.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

//<<< DDD / Domain Event
@Data
@ToString
public class EstimatedPriceAndCategory extends AbstractEvent {

    private String ebookId;
    private String summary;
    private String content;
    private Integer price;
    private String category;

    public EstimatedPriceAndCategory(EBook aggregate) {
        super(aggregate);
    }

    public EstimatedPriceAndCategory() {
        super();
    }
}

//>>> DDD / Domain Event

package labcqrssummarize.domain;

import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class EstimatedPriceAndCategory extends AbstractEvent {

    private String ebookId;
    private String summary;
    private String content;
    private Integer price;
    private String category;
}

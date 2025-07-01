package labcqrssummarize.domain;

import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class SummarizedContent extends AbstractEvent {

    private String ebookId;
    private String title;
    private String content;
    private String summary;
}

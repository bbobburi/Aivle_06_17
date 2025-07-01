package labcqrssummarize.domain;

import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class HandleEBookViewed extends AbstractEvent {

    private Integer pid;
    private String ebooks;
    private Date registeredAt;
    private Integer price;
}

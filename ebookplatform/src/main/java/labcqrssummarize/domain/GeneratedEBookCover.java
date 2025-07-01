package labcqrssummarize.domain;

import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class GeneratedEBookCover extends AbstractEvent {

    private String ebookId;
    private String title;
    private String authorId;
    private String coverImage;
    private String content;
}

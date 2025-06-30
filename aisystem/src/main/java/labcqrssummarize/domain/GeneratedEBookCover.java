package labcqrssummarize.domain;

import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GeneratedEBookCover extends AbstractEvent {

    private String ebookId;
    private String title;
    private String authorId;
    private String coverImage;
    private String content;

    public GeneratedEBookCover(EBook aggregate) {
        super(aggregate);
        this.ebookId = aggregate.getEbookId();
        this.title = aggregate.getTitle();
        this.authorId = aggregate.getAuthorId();
        this.coverImage = aggregate.getCoverImage();
        this.content = aggregate.getContent();
    }

    public GeneratedEBookCover() {
        super();
    }
}
//>>> DDD / Domain Event

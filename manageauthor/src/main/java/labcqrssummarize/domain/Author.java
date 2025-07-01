package labcqrssummarize.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import labcqrssummarize.ManageauthorApplication;
import labcqrssummarize.domain.ListOutEbookRequested;
import labcqrssummarize.domain.RegisteredAuthor;
import labcqrssummarize.domain.RequestPublish;
import labcqrssummarize.domain.RequestPublishCanceled;
import labcqrssummarize.domain.WrittenContent;
import lombok.Data;

@Entity
@Table(name = "Author_table")
@Data
//<<< DDD / Aggregate Root
public class Author {

    @Id
    private String authorId;

    private String name;

    private Boolean isApproved;

    private String ebooks;

    private String userId;

    @PostPersist
    public void onPostPersist() {
        RegisteredAuthor registeredAuthor = new RegisteredAuthor(this);
        registeredAuthor.publishAfterCommit();

        WrittenContent writtenContent = new WrittenContent(this);
        writtenContent.publishAfterCommit();

        RequestPublish requestPublish = new RequestPublish(this);
        requestPublish.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        ListOutEbookRequested listOutEbookRequested = new ListOutEbookRequested(
            this
        );
        listOutEbookRequested.publishAfterCommit();

        RequestPublishCanceled requestPublishCanceled = new RequestPublishCanceled(
            this
        );
        requestPublishCanceled.publishAfterCommit();
    }

    public static AuthorRepository repository() {
        AuthorRepository authorRepository = ManageauthorApplication.applicationContext.getBean(
            AuthorRepository.class
        );
        return authorRepository;
    }
}
//>>> DDD / Aggregate Root

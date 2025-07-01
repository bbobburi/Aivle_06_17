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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Author_table")
@Data
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String authorId;     // 작가 식별 ID
    private String name;         // 작가 이름
    private Boolean isApproved;  // 관리자 승인 여부
    private String userId;       // 작가 유저 ID

    // 작가 등록 이벤트 자동 발행
    @PostPersist
    public void onPostPersist() {
        RegisteredAuthor event = new RegisteredAuthor(this);
        event.publishAfterCommit();
    }

    // 수동 이벤트 발행 메서드들 (이벤트만 발행하고 DB 저장은 안 함)
    public void writeContent(String title, String content) {
        WrittenContent event = new WrittenContent(this);
        event.setTitle(title);
        event.setContent(content);
        event.publishAfterCommit();
    }

    public void requestPublish() {
        RequestPublish event = new RequestPublish(this);
        event.publishAfterCommit();
    }

    public void cancelPublish() {
        RequestPublishCanceled event = new RequestPublishCanceled(this);
        event.publishAfterCommit();
    }

    public void listOutEbook() {
        ListOutEbookRequested event = new ListOutEbookRequested(this);
        event.publishAfterCommit();
    }

    // Repository 접근 (Context 통해 주입받는 방식 그대로 유지)
    public static AuthorRepository repository() {
        return ManageauthorApplication.applicationContext.getBean(AuthorRepository.class);
    }
}
//>>> DDD / Aggregate Root

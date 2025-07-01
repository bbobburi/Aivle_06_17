package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class WrittenContent extends AbstractEvent {

    private String authorId;
    private String title;
    private String content;

    public WrittenContent(Author aggregate) {
        super(aggregate);
        this.authorId = aggregate.getUserId();  // Author에서 userId가 authorId 의미로 사용
        this.title = "제목 없음";               // 기본값 (원하는 경우 event에서 재설정)
        this.content = "내용 없음";             // 기본값
    }

    public WrittenContent() {
        super();
    }
}
//>>> DDD / Domain Event

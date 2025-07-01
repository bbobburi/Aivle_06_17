package labcqrssummarize.domain;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Author_table")
@Data
//<<< DDD / Aggregate Root
public class Author {

    @Id
    private String authorId;

    private String name;

    private Boolean isApproved; // 승인 여부

    @ElementCollection
    private List<String> ebooks = new ArrayList<>(); // 해당 작가의 전자책 ID 목록

    private String userId; // 플랫폼 사용자 ID

    /**
     * 작가 승인 메서드
     * 상태 변경 + 이벤트 발행
     */
    public void approve() {
        if (Boolean.TRUE.equals(this.isApproved)) {
            throw new IllegalStateException("이미 승인된 작가입니다.");
        }
        this.isApproved = true;

        RequestAuthorApproved event = new RequestAuthorApproved(this);
        event.publishAfterCommit();
    }

    /**
     * 작가 거부 메서드
     * 상태 변경 + 이벤트 발행
     */
    public void deny() {
        if (Boolean.FALSE.equals(this.isApproved)) {
            throw new IllegalStateException("이미 거부된 작가입니다.");
        }
        this.isApproved = false;

        RequestAuthorDenied event = new RequestAuthorDenied(this);
        event.publishAfterCommit();
    }

    /**
     * 전자책 추가 메서드
     */
    public void addEbook(String ebookId) {
        this.ebooks.add(ebookId);
    }
}
//>>> DDD / Aggregate Root

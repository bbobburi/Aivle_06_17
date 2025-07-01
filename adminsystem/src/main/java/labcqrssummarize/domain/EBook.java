package labcqrssummarize.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import lombok.Data;
import labcqrssummarize.domain.PublicationStatus;
import labcqrssummarize.AdminsystemApplication;

@Entity
@Table(name = "EBook_table")
@Data
//<<< DDD / Aggregate Root
public class EBook {

    @Id
    private String ebookId;

    private String title;
    private String authorId;
    private String content;
    private String coverImage;
    private String summary;
    private Integer price;
    private String category;
    private Integer countViews;

    @Enumerated(EnumType.STRING)
    private PublicationStatus publicationStatus; // 출간 상태 (PENDING, APPROVED, DENIED 등)

    /**
     * 콘텐츠 작성 완료 후 승인 처리
     * 승인 이벤트 발행
     */
    public void approveContent() {
        RequestContentApporved event = new RequestContentApporved(this);
        event.publishAfterCommit();
    }

    /**
     * 출간 요청에 대한 승인 처리
     * 상태 변경 + 승인 이벤트 발행
     */
    public void approvePublish() {
        if (this.publicationStatus == PublicationStatus.APPROVED) {
            throw new IllegalStateException("이미 출간 승인된 전자책입니다.");
        }
        this.publicationStatus = PublicationStatus.APPROVED;

        RequestPublishApproved event = new RequestPublishApproved(this);
        event.publishAfterCommit();
    }

    /**
     * 출간 요청에 대한 거부 처리
     * 상태 변경 + 거부 이벤트 발행
     */
    public void denyPublish() {
        if (this.publicationStatus == PublicationStatus.DENIED) {
            throw new IllegalStateException("이미 출간 거부된 전자책입니다.");
        }
        this.publicationStatus = PublicationStatus.DENIED;

        RequestPublishDenied event = new RequestPublishDenied(this);
        event.publishAfterCommit();
    }

    /**
     * Repository 정적 접근 메서드
     */
    public static EBookRepository repository() {
        return AdminsystemApplication.applicationContext.getBean(EBookRepository.class);
    }
}
//>>> DDD / Aggregate Root

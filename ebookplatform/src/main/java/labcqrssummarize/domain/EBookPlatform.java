package labcqrssummarize.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import labcqrssummarize.EbookplatformApplication;

import lombok.Data;

@Entity
@Table(name = "EBookPlatform_table")
@Data
public class EBookPlatform {

    public enum EbookStatus {
        IN_PROGRESS,    // 출판 준비 중
        OPEN,           // 출판 준비 완료됨, 열람
        REMOVED         // 삭제됨
    }

    @Id
    private Integer pid;

    @ElementCollection
    private List<String> ebooks = new ArrayList<>();

    private LocalDateTime registeredAt;


    private String coverImage;

    private String summary;

    private Integer price;  // 단수형으로 변경

    @Enumerated(EnumType.STRING)
    private EbookStatus status = EbookStatus.IN_PROGRESS;

    private boolean coverGenerated = false;
    private boolean contentSummarized = false;
    private boolean priceAndCategorySet = false;

    // 상태 전이 메서드
    public void markCoverGenerated() {
        this.coverGenerated = true;
    }

    public void markContentSummarized() {
        this.contentSummarized = true;
    }

    public void markPriceAndCategorySet() {
        this.priceAndCategorySet = true;
    }

    public boolean isReadyForPublish() {
        return coverGenerated && contentSummarized && priceAndCategorySet;
    }

    public void updateStatus(EbookStatus newStatus) {
        this.status = newStatus;
    }

    public void register() {
        if (!isReadyForPublish()) {
            throw new IllegalStateException("아직 출판 준비가 완료되지 않았습니다.");
        }

        this.status = EbookStatus.OPEN;
        this.registeredAt = LocalDateTime.now();

        ListedUpEBook listedUp = new ListedUpEBook(this);
        listedUp.publishAfterCommit();

        System.out.println("<< 전자책 등록 완료됨 >>");
    }

    public boolean openEBook(RequestOpenEBookAccept event) {
        if (this.status != EbookStatus.OPEN) {
            HandleEBookViewFailed failEvent = new HandleEBookViewFailed(this);
            failEvent.setUserId(event.getUserId());  // userId 전달
            failEvent.publishAfterCommit();

            System.out.println("<< 전자책 열람 실패 >>");
            return false;
        }

        HandleEBookViewed successEvent = new HandleEBookViewed(this);
        successEvent.setUserId(event.getUserId());  // userId 전달
        successEvent.publishAfterCommit();

        System.out.println("<< 전자책 열람 성공 >>");
        return true;
    }

    public void listOutEBook(ListOutEBook event) {
        this.status = EbookStatus.REMOVED;

        ListOutEBook listOutEBook = new ListOutEBook(this);
        listOutEBook.publishAfterCommit();

        System.out.println("<< 전자책 비공개 처리됨 >>");
    }

    public static EBookPlatformRepository repository() {
        return EbookplatformApplication.applicationContext.getBean(EBookPlatformRepository.class);
    }
}

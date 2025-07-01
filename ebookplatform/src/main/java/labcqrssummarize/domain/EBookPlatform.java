package labcqrssummarize.domain;

import javax.persistence.*;
import labcqrssummarize.EbookplatformApplication;
import labcqrssummarize.domain.HandleEBookViewFailed;
import labcqrssummarize.domain.HandleEBookViewed;
import labcqrssummarize.domain.ListOutEBook;
import labcqrssummarize.domain.ListedUpEBook;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private List<String> ebooks;

    private LocalDateTime registeredAt;

    private String coverImage;

    private String summary;

    private Integer prices;

    @Enumerated(EnumType.STRING)
    private EbookStatus status = EbookStatus.IN_PROGRESS;

    private boolean coverGenerated = false;
    private boolean contentSummarized = false;
    private boolean priceAndCategorySet = false;

    // === 상태 전이 메서드 ===
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

    // 전자책 상태 변경
    public void updateStatus(EbookStatus newStatus) {
        this.status = newStatus;
    }

    // 출판 준비가 완료되었는지 확인
    public void register() {
        if (!isReadyForPublish()) {
            throw new IllegalStateException("아직 출판 준비가 완료되지 않았습니다.");
        }

        // 출판 준비 완료됨으로 상태 변경
        this.status = EbookStatus.OPEN;
        this.registeredAt = LocalDateTime.now();

        // 등록 이벤트
        ListedUpEBook listedUp = new ListedUpEBook(this);
        listedUp.publishAfterCommit();

        System.out.println("<< 전자책 등록 완료됨 >>");
    }

    // 전자책 열람 시도
    public boolean openEBook(RequestOpenEBookAccept event) {
        if (this.status != EbookStatus.OPEN) {
            HandleEBookViewFailed failEvent = new HandleEBookViewFailed(this);
            failEvent.publishAfterCommit();

            System.out.println("<< 전자책 열람 실패 >>");
            return false;  // 열람 실패
        }

        // 열람 성공 처리
        HandleEBookViewed successEvent = new HandleEBookViewed(this);
        successEvent.publishAfterCommit();

        System.out.println("<< 전자책 열람 성공 >>");
        return true;
    }

    // 전자책 비공개 처리
    public void listOutEBook(ListOutEBook event) {
        this.status = EbookStatus.REMOVED;

        ListOutEBook listOutEBook = new ListOutEBook(this);
        listOutEBook.publishAfterCommit();

        System.out.println("<< 전자책 비공개 처리됨 >>");
    }

    // Repository 접근
    public static EBookPlatformRepository repository() {
        return EbookplatformApplication.applicationContext.getBean(EBookPlatformRepository.class);
    }
}
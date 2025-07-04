package labcqrssummarize.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import labcqrssummarize.domain.*;
import labcqrssummarize.EbookplatformApplication;
import lombok.Data;

@Entity
@Table(name = "EBookPlatform_table")
@Data
public class EBookPlatform {

    @Id
    private Integer pid = 1;  // 고정값

    @ElementCollection
    private List<String> ebooks = new ArrayList<>();

    private LocalDateTime registeredAt;

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

    // 얘가 등록 상태 확인해서 올려줌.
    public void register(String ebookId) {
    if (!isReadyForPublish()) {
        throw new IllegalStateException("아직 출판 준비가 완료되지 않았습니다.");
    }

    this.registeredAt = LocalDateTime.now();

    if (this.ebooks == null) {
        this.ebooks = new ArrayList<>();
    }
    if (!this.ebooks.contains(ebookId)) {
        this.ebooks.add(ebookId);
    }

    ListedUpEBook listedUp = new ListedUpEBook(this);
    listedUp.publishAfterCommit();

    System.out.println("<< 전자책 등록 완료됨 >>");
    }

/* 
    public boolean openEBook(RequestOpenEBookAccefpt event) {
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
*/
}

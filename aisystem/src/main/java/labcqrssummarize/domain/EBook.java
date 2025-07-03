// src/main/java/labcqrssummarize/domain/EBook.java
package labcqrssummarize.domain;

import javax.persistence.*;
import lombok.Data;
import labcqrssummarize.AisystemApplication;
import labcqrssummarize.domain.GeneratedEBookCover;
import labcqrssummarize.domain.SummarizedContent;
import labcqrssummarize.domain.EstimatedPriceAndCategory;
import labcqrssummarize.domain.publicationStatus;

@Entity
@Table(name = "EBook_table")
@Data
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

    /** 출간 상태 */
    @Enumerated(EnumType.STRING)
    private publicationStatus publicationStatus;

    @PostPersist
    public void onPostPersist() {
        new GeneratedEBookCover(this).publishAfterCommit();
        new SummarizedContent(this).publishAfterCommit();
        new EstimatedPriceAndCategory(this).publishAfterCommit();
    }

    public static EBookRepository repository() {
        return AisystemApplication
            .applicationContext
            .getBean(EBookRepository.class);
    }

    // 커스텀 setter/getter
    public void setAuthor(String author) {
        this.authorId = author;
    }

    public String getId() {
        return this.ebookId;
    }
}





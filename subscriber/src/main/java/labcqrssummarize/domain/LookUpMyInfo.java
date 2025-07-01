package labcqrssummarize.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "LookUpMyInfo_table")
@Data
public class LookUpMyInfo {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
}

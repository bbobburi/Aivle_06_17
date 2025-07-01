package labcqrssummarize.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import labcqrssummarize.SubscriberApplication;
import labcqrssummarize.domain.MembershipRequested;
import labcqrssummarize.domain.RequestOpenEBookAccept;
import labcqrssummarize.domain.SignedUp;
import labcqrssummarize.domain.SubscribeCanceled;
import labcqrssummarize.domain.SubscribeRequested;
import lombok.Data;

@Entity
@Table(name = "Subscriber_table")
@Data
//<<< DDD / Aggregate Root
public class Subscriber {

    @Id
    private String subscriberId;

    private String userId;

    private String subscriptionType;

    private Date startedAt;

    private Date expiredAt;

    private ViewHistory viewHistory;

    private membershipType membershipType;

    private subscriptionStatus subscriptionStatus;

    private String password;

    @Embedded
    private Email email;

    @PostPersist
    public void onPostPersist() {
        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();

        SubscribeRequested subscribeRequested = new SubscribeRequested(this);
        subscribeRequested.publishAfterCommit();

        SubscribeCanceled subscribeCanceled = new SubscribeCanceled(this);
        subscribeCanceled.publishAfterCommit();

        MembershipRequested membershipRequested = new MembershipRequested(this);
        membershipRequested.publishAfterCommit();

        RequestOpenEBookAccept requestOpenEBookAccept = new RequestOpenEBookAccept(
            this
        );
        requestOpenEBookAccept.publishAfterCommit();
    }

    public static SubscriberRepository repository() {
        SubscriberRepository subscriberRepository = SubscriberApplication.applicationContext.getBean(
            SubscriberRepository.class
        );
        return subscriberRepository;
    }

    //<<< Clean Arch / Port Method
    public static void recommandKtMembership(SignedUp signedUp) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscriber subscriber = new Subscriber();
        repository().save(subscriber);

        */

        /** Example 2:  finding and process
        

        repository().findById(signedUp.get???()).ifPresent(subscriber->{
            
            subscriber // do something
            repository().save(subscriber);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root

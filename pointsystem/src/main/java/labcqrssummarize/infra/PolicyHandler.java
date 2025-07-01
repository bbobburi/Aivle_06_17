package labcqrssummarize.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import labcqrssummarize.config.kafka.KafkaProcessor;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    UserPointRepository userPointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp'"
    )
    public void wheneverSignedUp_CheckMembership(@Payload SignedUp signedUp) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener CheckMembership : " + signedUp + "\n\n"
        );

        // Comments //
        //KT 멤버쉽 가입했는지 확인

        // Sample Logic //

        GivePointCommand command = new GivePointCommand();
        UserPoint.givePoint(command);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='HandleEBookViewed'"
    )
    public void wheneverHandleEBookViewed_ReducePointRequested(
        @Payload HandleEBookViewed handleEBookViewed
    ) {
        HandleEBookViewed event = handleEBookViewed;
        System.out.println(
            "\n\n##### listener ReducePointRequested : " +
            handleEBookViewed +
            "\n\n"
        );

        // Sample Logic //

        ReducePointCommand command = new ReducePointCommand();
        UserPoint.reducePoint(command);
    }
}
//>>> Clean Arch / Inbound Adaptor

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
    SubscriberRepository subscriberRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp'"
    )
    public void wheneverSignedUp_RecommandKtMembership(
        @Payload SignedUp signedUp
    ) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener RecommandKtMembership : " + signedUp + "\n\n"
        );

        // Comments //
        //KT 멤버쉽 가입 플랜 메시지 띄우기

        // Sample Logic //
        Subscriber.recommandKtMembership(event);
    }
}
//>>> Clean Arch / Inbound Adaptor

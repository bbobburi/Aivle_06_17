package labcqrssummarize.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import labcqrssummarize.config.kafka.KafkaProcessor;
import labcqrssummarize.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class LookUpSubscriberViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private LookUpSubscriberRepository lookUpSubscriberRepository;
    //>>> DDD / CQRS
}

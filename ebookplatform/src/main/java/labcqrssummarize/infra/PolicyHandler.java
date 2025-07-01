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
    EBookPlatformRepository eBookPlatformRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GeneratedEBookCover'"
    )
    public void wheneverGeneratedEBookCover_CheckEBookStatus(
        @Payload GeneratedEBookCover generatedEBookCover
    ) {
        GeneratedEBookCover event = generatedEBookCover;
        System.out.println(
            "\n\n##### listener CheckEBookStatus : " +
            generatedEBookCover +
            "\n\n"
        );
        // Comments //
        //AI 표지 생성과 전자책 요약이 모두 된 상태인지 체크

        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SummarizedContent'"
    )
    public void wheneverSummarizedContent_CheckEBookStatus(
        @Payload SummarizedContent summarizedContent
    ) {
        SummarizedContent event = summarizedContent;
        System.out.println(
            "\n\n##### listener CheckEBookStatus : " +
            summarizedContent +
            "\n\n"
        );
        // Comments //
        //AI 표지 생성과 전자책 요약이 모두 된 상태인지 체크

        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='EstimatiedPriceAndCategory'"
    )
    public void wheneverEstimatiedPriceAndCategory_CheckEBookStatus(
        @Payload EstimatiedPriceAndCategory estimatiedPriceAndCategory
    ) {
        EstimatiedPriceAndCategory event = estimatiedPriceAndCategory;
        System.out.println(
            "\n\n##### listener CheckEBookStatus : " +
            estimatiedPriceAndCategory +
            "\n\n"
        );
        // Comments //
        //AI 표지 생성과 전자책 요약이 모두 된 상태인지 체크

        // Sample Logic //

    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ListOutEbookRequested'"
    )
    public void wheneverListOutEbookRequested_RequestPrivateStatus(
        @Payload ListOutEbookRequested listOutEbookRequested
    ) {
        ListOutEbookRequested event = listOutEbookRequested;
        System.out.println(
            "\n\n##### listener RequestPrivateStatus : " +
            listOutEbookRequested +
            "\n\n"
        );

        // Sample Logic //

        ListOutEBookCommand command = new ListOutEBookCommand();
        EBookPlatform.listOutEBook(command);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RequestOpenEBookAccept'"
    )
    public void wheneverRequestOpenEBookAccept_RequestOpenEBook(
        @Payload RequestOpenEBookAccept requestOpenEBookAccept
    ) {
        RequestOpenEBookAccept event = requestOpenEBookAccept;
        System.out.println(
            "\n\n##### listener RequestOpenEBook : " +
            requestOpenEBookAccept +
            "\n\n"
        );

        // Sample Logic //

        OpenEBookCommand command = new OpenEBookCommand();
        EBookPlatform.openEBook(command);
    }
}
//>>> Clean Arch / Inbound Adaptor

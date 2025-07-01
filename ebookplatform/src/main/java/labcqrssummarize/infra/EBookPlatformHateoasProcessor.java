package labcqrssummarize.infra;

import labcqrssummarize.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class EBookPlatformHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<EBookPlatform>> {

    @Override
    public EntityModel<EBookPlatform> process(
        EntityModel<EBookPlatform> model
    ) {
        return model;
    }
}

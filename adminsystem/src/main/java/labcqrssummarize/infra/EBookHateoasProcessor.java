package labcqrssummarize.infra;

import labcqrssummarize.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class EBookHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<EBook>> {

    @Override
    public EntityModel<EBook> process(EntityModel<EBook> model) {
        return model;
    }
}

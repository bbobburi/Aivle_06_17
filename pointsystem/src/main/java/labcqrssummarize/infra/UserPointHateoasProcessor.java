package labcqrssummarize.infra;

import labcqrssummarize.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserPointHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<UserPoint>> {

    @Override
    public EntityModel<UserPoint> process(EntityModel<UserPoint> model) {
        return model;
    }
}

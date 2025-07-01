package labcqrssummarize.infra;

import java.util.List;
import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "lookUpSubscribers",
    path = "lookUpSubscribers"
)
public interface LookUpSubscriberRepository
    extends PagingAndSortingRepository<LookUpSubscriber, Long> {}

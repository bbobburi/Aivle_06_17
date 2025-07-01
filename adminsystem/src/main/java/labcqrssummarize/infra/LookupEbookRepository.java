package labcqrssummarize.infra;

import java.util.List;
import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "lookupEbooks",
    path = "lookupEbooks"
)
public interface LookupEbookRepository
    extends PagingAndSortingRepository<LookupEbook, Long> {}

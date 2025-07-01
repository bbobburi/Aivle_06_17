package labcqrssummarize.infra;

import java.util.List;
import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "lookupAuthors",
    path = "lookupAuthors"
)
public interface LookupAuthorRepository
    extends PagingAndSortingRepository<LookupAuthor, Long> {}

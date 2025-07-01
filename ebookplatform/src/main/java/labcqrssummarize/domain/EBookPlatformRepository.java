package labcqrssummarize.domain;

import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "eBookPlatforms",
    path = "eBookPlatforms"
)
public interface EBookPlatformRepository
    extends PagingAndSortingRepository<EBookPlatform, Integer> {}

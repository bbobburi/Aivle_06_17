package labcqrssummarize.domain;

import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "eBookPlatforms",
    path = "eBookPlatforms"
)
public interface EBookPlatformRepository
    extends PagingAndSortingRepository<EBookPlatform, Integer> {

    @Query("SELECT e FROM EBookPlatform e WHERE :ebookId MEMBER OF e.ebooks")
    Optional<EBookPlatform> findByEbookId(@Param("ebookId") String ebookId);

}
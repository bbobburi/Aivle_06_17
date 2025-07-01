package labcqrssummarize.domain;

import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "eBooks", path = "eBooks")
public interface EBookRepository
    extends PagingAndSortingRepository<EBook, String> {}

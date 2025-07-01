package labcqrssummarize.domain;

import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "authors", path = "authors")
public interface AuthorRepository
    extends PagingAndSortingRepository<Author, Long> {
    Optional<Author> findByAuthorId(String authorId);
}

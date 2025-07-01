package labcqrssummarize.infra;

import java.util.List;
import labcqrssummarize.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "lookUpMyInfos",
    path = "lookUpMyInfos"
)
public interface LookUpMyInfoRepository
    extends PagingAndSortingRepository<LookUpMyInfo, Long> {}

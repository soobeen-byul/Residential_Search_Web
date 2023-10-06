package beaver.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import beaver.backend.domain.Trans;
public interface TransRepository extends JpaRepository<Trans, Long>, JpaSpecificationExecutor<Trans>{
}

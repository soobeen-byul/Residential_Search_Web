package beaver.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import beaver.backend.domain.Location;
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location>{
}

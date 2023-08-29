package beaver.backend.repository;
import beaver.backend.domain.Trans;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TransRepository extends JpaRepository<Trans, Long>{
}

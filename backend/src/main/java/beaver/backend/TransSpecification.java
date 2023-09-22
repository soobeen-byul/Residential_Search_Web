package beaver.backend;

import org.springframework.data.jpa.domain.Specification;

import beaver.backend.domain.Trans;
import jakarta.persistence.criteria.Predicate;

public class TransSpecification {

    public static Specification<Trans> findByCriteria(SearchCriteria criteria) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (criteria.getHouseType() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("건물용도"), criteria.getHouseType()));
            }

            if (criteria.getMinArea() != null && criteria.getMaxArea() != null) {
                predicate = builder.and(predicate, builder.between(root.get("평수"), criteria.getMinArea(), criteria.getMaxArea()));
            }

            if (criteria.getUserSale() != null) {
                predicate = builder.and(predicate, builder.and(predicate, builder.lessThanOrEqualTo(root.get("거래금액"), criteria.getUserSale())));
            }
   
            return predicate;
        };
    }
}

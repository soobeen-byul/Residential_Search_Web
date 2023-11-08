package beaver.backend.specification;

// import java.beans.Expression;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import beaver.backend.domain.Location;
import beaver.backend.domain.Trans;
import beaver.backend.dto.SearchCriteria;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Expression;

public class TransSpecification {

    public static Specification<Trans> findByCriteria(SearchCriteria criteria, List<Location> criteria_loc) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (criteria.getHouseType() != null) {
                predicate = builder.and(predicate, builder.equal(root.get("건물용도"), criteria.getHouseType()));
            }

            if (criteria.getMinArea() != null && criteria.getMaxArea() != null) {
                predicate = builder.and(predicate, builder.between(root.get("평수"), criteria.getMinArea(), criteria.getMaxArea()));
            }

            if (criteria.getMinSale() != null && criteria.getMaxSale() != null) {
                predicate = builder.and(predicate, builder.and(predicate,  builder.between(root.get("거래금액"), criteria.getMinSale(), criteria.getMaxSale())));
            }

            System.out.println("11"); 
            // criteria_loc의 지역코드 컬럼 리스트에 데이터가 있는 행만 가져오는 조건
            if (!criteria_loc.isEmpty()) {
                List<String> locationCodes = criteria_loc.stream().map(Location::get지역코드).collect(Collectors.toList());
                System.out.println(locationCodes); 
                Expression<String> locationCodeExpression = root.get("지역코드");
                predicate = builder.and(predicate, locationCodeExpression.in(locationCodes));
            }
            else {
                System.out.println("33"); 
            }

   
            return predicate;
        };
    }
}

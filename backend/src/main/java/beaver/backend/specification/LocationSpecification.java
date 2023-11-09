package beaver.backend.specification;

import org.springframework.data.jpa.domain.Specification;

import beaver.backend.domain.Location;
import beaver.backend.dto.Coordinates;
import beaver.backend.dto.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

public class LocationSpecification {

    public static Specification<Location> findByLocation(Coordinates coordinates, SearchCriteria searchCriteria) {
        return (root, query, builder) -> {
            CriteriaBuilder cb = builder;
            Predicate predicate = cb.conjunction();

            if (coordinates.getX() != null && coordinates.getY() != null) {
                // 기준 좌표의 위도 및 경도
                double searchLongitude = coordinates.getX();
                double searchLatitude = coordinates.getY();
                Integer searhDist = searchCriteria.getUserDist();
                

                // 데이터베이스 좌표의 위도 및 경도
                jakarta.persistence.criteria.Expression<Double> dbLatitude = root.get("위도");
                jakarta.persistence.criteria.Expression<Double> dbLongitude = root.get("경도");

                // Haversine 공식을 사용하여 거리 계산
                jakarta.persistence.criteria.Expression<Double> distanceExpression = cb.function(
                        "haversine", Double.class,
                        dbLatitude, dbLongitude, cb.literal(searchLatitude), cb.literal(searchLongitude)
                );

                // 거리가 100m 이하인 데이터 검색
                predicate = cb.and(predicate, cb.le(distanceExpression, searhDist)); // 0.1 킬로미터(100m)

                // 필요에 따라 추가적인 조건을 predicate에 추가할 수 있습니다.
            }

            return predicate;
        };
    }
}
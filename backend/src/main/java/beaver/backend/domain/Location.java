package beaver.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="seoul_loc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location {
    @Id
    private String 지역코드;
    private Double 위도;
    private Double 경도;
    private String 자치구코드;
    private String 자치구명;
    private String 행정동코드;
    private String 행정동명;
}

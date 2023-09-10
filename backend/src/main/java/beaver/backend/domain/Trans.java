package beaver.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="trans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Trans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer 접수연도;
    private String 자치구코드;
    private String 자치구명;
    private String 법정동코드;
    private String 법정동명;
    private String 건물명;
    private String 계약일;
    private Long 거래금액;
    private Long 층;
    private Integer 건축년도;
    private String 건물용도;
    private String 주소;
    private Long 평수;

    @Builder
    public Trans(Long id, Integer 접수연도, String 자치구코드, String 자치구명, String 법정동코드, String 법정동명, String 건물명, String 계약일,
                 Long 거래금액, Long 층, Integer 건축년도, String 건물용도, String 주소, Long 평수) {

        this.id = id;
        this.접수연도 = 접수연도;
        this.자치구코드 = 자치구코드;
        this.자치구명 = 자치구명;
        this.법정동코드 = 법정동코드;
        this.법정동명 = 법정동명;
        this.건물명 = 건물명;
        this.계약일 = 계약일;
        this.거래금액 = 거래금액;
        this.층 = 층;
        this.건축년도 = 건축년도;
        this.건물용도 = 건물용도;
        this.주소 = 주소;
        this.평수= 평수;
    }

}

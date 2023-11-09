package beaver.backend.domain;

import java.time.Year;

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
    private Year 접수연도;
    private String 자치구코드;
    private String 자치구명;
    private String 행정동코드;
    private String 행정동명;
    private String 건물명;
    private String 계약일;
    private Long 거래금액;
    private Long 층;
    private Year 건축년도;
    private String 건물용도;
    private String 주소;
    private Double 평수;
    private String 지역코드;

    @Builder
    public Trans(Long id, Year 접수연도, String 자치구코드, String 자치구명, String 행정동코드, String 행정동명, String 건물명, String 계약일,
                 Long 거래금액, Long 층, Year 건축년도, String 건물용도, String 주소, Double 평수, String 지역코드) {

        this.id = id;
        this.접수연도 = 접수연도;
        this.자치구코드 = 자치구코드;
        this.자치구명 = 자치구명;
        this.행정동코드 = 행정동코드;
        this.행정동명 = 행정동명;
        this.건물명 = 건물명;
        this.계약일 = 계약일;
        this.거래금액 = 거래금액;
        this.층 = 층;
        this.건축년도 = 건축년도;
        this.건물용도 = 건물용도;
        this.주소 = 주소;
        this.평수= 평수;
        this.지역코드=지역코드;
    }

}

package beaver.backend.dto;

import java.time.Year;

import beaver.backend.domain.Trans;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TransResponse {
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

    public TransResponse(Trans trans) {

        this.id = trans.getId();
        this.접수연도 = trans.get접수연도();
        this.자치구코드 = trans.get자치구코드();
        this.자치구명 = trans.get자치구명();
        this.행정동코드 = trans.get행정동코드();
        this.행정동명 = trans.get행정동명();
        this.건물명 = trans.get건물명();
        this.계약일 = trans.get계약일();
        this.거래금액 = trans.get거래금액();
        this.층 = trans.get층();
        this.건축년도 = trans.get건축년도();
        this.건물용도 = trans.get건물용도();
        this.주소 = trans.get주소();
        this.평수= trans.get평수();
        this.지역코드=trans.get지역코드();
    }
    
}

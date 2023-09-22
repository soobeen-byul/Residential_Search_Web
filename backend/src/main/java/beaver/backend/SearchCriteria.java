package beaver.backend;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class SearchCriteria {
    // private Long id;
    private String houseType;
    private Long userSale;
    private Long minArea;
    private Long maxArea;
    private String postCode;
    private String address; 
    
    private Integer userDist;
    

    // private String 자치구코드;
    // private String 자치구명;
    // private String 법정동코드;
    // private String 법정동명;
    // private String 계약일;
    // private Long 거래금액;
    // private Long 층;
    // private Integer 건축년도;
    // private String 건물용도;
    // // private String 주소;
    // private Long 평수;
}
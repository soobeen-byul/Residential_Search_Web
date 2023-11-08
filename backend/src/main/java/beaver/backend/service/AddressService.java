package beaver.backend.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import beaver.backend.dto.Coordinates;

@Service
public class AddressService {
    
    private final String uri = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${kakao.local.key}")
    private String kakaoLocalKey;

    public Coordinates getCoordinate(String address){
        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "KakaoAK " + kakaoLocalKey;
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("query",address)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        String body = response.getBody();
        JSONObject json = new JSONObject(body);

        JSONArray documents = json.getJSONArray("documents");
        String xString = documents.getJSONObject(0).getString("x");
        String yString = documents.getJSONObject(0).getString("y");

        // 문자열을 long으로 변환
        Double x = Double.parseDouble(xString);
        Double y = Double.parseDouble(yString);

        return new Coordinates(x, y);
    }
}
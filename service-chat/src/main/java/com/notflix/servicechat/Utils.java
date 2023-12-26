package com.notflix.servicechat;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;


import com.google.common.net.HttpHeaders;
public class Utils {
  public static <ResponseType> ResponseType getRequestWithAuth(String url, String user) {
    RestTemplate restTemplate = new RestTemplate();
    RequestEntity<Void> requestEntity = RequestEntity
        .get(url)
        .header(HttpHeaders.AUTHORIZATION, user)
        .build();

    ParameterizedTypeReference<ResponseType> responseType = new ParameterizedTypeReference<ResponseType>() {};
    
    return restTemplate.exchange(
        url,
        HttpMethod.GET,
        requestEntity,
        responseType
    ).getBody();
  }

  public static <ResponseType> ResponseType postRequestWithAuth(String url, String user, Object... args) {
    RestTemplate restTemplate = new RestTemplate();
    RequestEntity<Void> requestEntity = RequestEntity
        .post(url)
        .header(HttpHeaders.AUTHORIZATION, user)
        .build();

    ParameterizedTypeReference<ResponseType> responseType = new ParameterizedTypeReference<ResponseType>() {};
    
    return restTemplate.exchange(
      url,
      HttpMethod.POST,
      requestEntity,
      responseType,
      args
    ).getBody();
  }
}
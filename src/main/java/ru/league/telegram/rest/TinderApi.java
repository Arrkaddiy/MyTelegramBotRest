package ru.league.telegram.rest;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public interface TinderApi {

    default RestTemplate getRestTemplate() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }

    default String getUri(String uri, String... requestParam) {
        StringBuilder builder = new StringBuilder(uri);
        for (String request : requestParam) {
            builder.append("/").append(request);
        }
        return builder.toString();
    }
}

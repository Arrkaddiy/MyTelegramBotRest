package ru.league.telegram.service;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.league.telegram.model.Answer;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TinderApi {

    private HttpClient httpClient = HttpClientBuilder.create().build();
    private ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    private RestTemplate restTemplate = new RestTemplate(requestFactory);

    public String getUpdate(String chatId, String input) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder uri = null;
        try {
            uri = UriComponentsBuilder.fromUri(new URI("http://localhost:8080/bot"))
                    .queryParam("chatId", chatId)
                    .queryParam("input", input);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(uri.toUriString());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}

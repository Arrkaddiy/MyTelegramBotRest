package ru.league.telegram.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.league.telegram.entity.User;

@Service
public class TinderStartStateApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderStartStateApi.class);
    private static final String URI = "http://localhost:8080/tinder/user";

    public String startMethod(User user) {
        log.info("Передача в Tinder команды - /start от пользователя - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "start");
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, String.class);
    }
}

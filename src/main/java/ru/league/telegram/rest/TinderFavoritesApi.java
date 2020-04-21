package ru.league.telegram.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.league.telegram.entity.User;

@Service
public class TinderFavoritesApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderMethodApi.class);
    private static final String URI = "http://localhost:8080/tinder/user";

    public String favoritesMethod(User user) {
        log.info("Передача в Tinder команды - /favorites от пользователя - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "favorites");
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, String.class);
    }

    public String favoriteNumberMethod(User user, String number) {
        log.info("Передача в Tinder команды - /favoritesNumber от пользователя - '{}', с номером анкеты - '{}'", user, number);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "favorites", number);
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, String.class);
    }

    public String exitMethod(User user) {
        log.info("Передача в Tinder команды - /exit от пользователя - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "favorites", "exit");
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, String.class);
    }
}

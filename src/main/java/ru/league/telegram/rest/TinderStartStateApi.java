package ru.league.telegram.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import ru.league.telegram.entity.User;
import ru.league.telegram.model.UserModel;

import java.util.Optional;

public class TinderStartStateApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderStartStateApi.class);
    private static final String URI = "http://localhost:8080/tinder/start/";

    public UserModel startMethod(User user) {
        log.info("Передача в Tinder команды - /start");
        RestTemplate restTemplate = getRestTemplate();
        Optional<UserModel> answerModel = Optional.ofNullable(restTemplate.getForObject(URI + user.getTinderId(), UserModel.class));
        return answerModel.orElseThrow(IllegalArgumentException::new);
    }
}

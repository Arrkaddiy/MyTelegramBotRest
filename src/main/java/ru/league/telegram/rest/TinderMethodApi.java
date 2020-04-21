package ru.league.telegram.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.league.telegram.entity.User;
import ru.league.telegram.model.StateModel;

@Service
public class TinderMethodApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderMethodApi.class);
    private static final String URI = "http://localhost:8080/tinder/user";

    public StateModel getPossibleMethods(User user) {
        log.info("Получение доступных переходов для пользователя, по Id сторонней системы - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        return restTemplate.getForObject(getUri(URI, user.getTinderId().toString(), "methods"), StateModel.class);
    }
}

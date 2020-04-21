package ru.league.telegram.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.league.telegram.entity.User;
import ru.league.telegram.model.UserModel;

import java.util.Optional;

@Component
public class TinderUserApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderUserApi.class);
    private static final String URI = "http://localhost:8080/tinder/user/";

    public UserModel get(User user) {
        log.info("Получение пользователя по Id сторонней системы - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        Optional<UserModel> userModel = Optional.ofNullable(restTemplate.getForObject(URI + user.getTinderId(), UserModel.class));
        return userModel.orElseThrow(IllegalArgumentException::new);
    }

    public UserModel create() {
        log.info("Создание пользователя в сторонней системе");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = getRestTemplate();
        Optional<UserModel> userModel = Optional.ofNullable(restTemplate.postForObject(URI, entity, UserModel.class));
        return userModel.orElseThrow(IllegalArgumentException::new);
    }

    public void delete(User user) {
        log.info("Удаление пользователя по Id сторонней системы - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.delete(URI + user.getTinderId());
    }
}

package ru.league.telegram.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.league.telegram.entity.User;
import ru.league.telegram.model.ProfileModel;

import java.util.Optional;

@Service
public class TinderUserApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderUserApi.class);
    private static final String URI = "http://localhost:8080/tinder/user";

    public ProfileModel getLastLookProfile(User user) {
        log.info("Получение крайней просмотренной анкеты пользователя по Id сторонней системы - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "lastlook");
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, ProfileModel.class);
    }

    public Long createUser() {
        log.info("Создание пользователя в сторонней системе");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, "create");
        log.debug("Получен запрос - '{}'", request);
        Optional<Long> tinderId = Optional.ofNullable(restTemplate.postForObject(request, entity, Long.class));
        return tinderId.orElseThrow(IllegalArgumentException::new);
    }

    public void deleteUser(User user) {
        log.info("Удаление пользователя по Id сторонней системы - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.delete(getUri(URI, user.getTinderId().toString()));
    }
}

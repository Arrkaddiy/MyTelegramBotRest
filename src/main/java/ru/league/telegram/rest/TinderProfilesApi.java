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

@Service
public class TinderProfilesApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderProfilesApi.class);
    private static final String URI = "http://localhost:8080/tinder/user";

    public ProfileModel leftMethod(User user) {
        log.info("Передача в Tinder команды - /left от пользователя - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "profiles", "left");
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, ProfileModel.class);
    }

    public String rightMethod(User user, ProfileModel profileModel) {
        log.info("Match профиля - '{}', пользователем - '{}'", profileModel, user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "profiles", "right");

        request = request + "?" + "id=" + profileModel.getId();

        log.debug("Получен запрос - '{}'", request);
        return restTemplate.postForObject(request, entity, String.class);
    }
}

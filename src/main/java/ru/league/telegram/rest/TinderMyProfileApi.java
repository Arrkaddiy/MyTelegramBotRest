package ru.league.telegram.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.league.telegram.entity.User;

@Service
public class TinderMyProfileApi implements TinderApi {

    private static final Logger log = LoggerFactory.getLogger(TinderMyProfileApi.class);
    private static final String URI = "http://localhost:8080/tinder/user";

    public String profileMethod(User user) {
        log.info("Передача в Tinder команды - /profile от пользователя - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "my_profile");
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, String.class);
    }

    public String singInMethod(User user, String name, String pass) {
        log.info("Передача в Tinder команды - /singIn от пользователя - '{}'", user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "my_profile", "sing_in");

        request = request + "?" + "name=" + name + "&" + "pass=" + pass;

        log.debug("Получен запрос - '{}'", request);
        return restTemplate.postForObject(request, entity, String.class);
    }

    public String singUpMethod(User user, String name, String pass, String sex) {
        log.info("Передача в Tinder команды - /singUp от пользователя - '{}'", user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "my_profile", "sing_up");

        request = request + "?" + "name=" + name + "&" + "pass=" + pass + "&" + "sex=" + sex;

        log.debug("Получен запрос - '{}'", request);
        return restTemplate.postForObject(request, entity, String.class);
    }

    public String singOutMethod(User user) {
        log.info("Передача в Tinder команды - /singOut от пользователя - '{}'", user);
        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "my_profile", "sing_out");
        log.debug("Получен запрос - '{}'", request);
        return restTemplate.getForObject(request, String.class);
    }

    public String updateMethod(User user, String about) {
        log.info("Передача в Tinder команды - /update от пользователя - '{}'", user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = getRestTemplate();
        String request = getUri(URI, user.getTinderId().toString(), "my_profile", "update");

        request = request + "?" + "about=" + about;

        log.debug("Получен запрос - '{}'", request);
        return restTemplate.postForObject(request, entity, String.class);
    }
}

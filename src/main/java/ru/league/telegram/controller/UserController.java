package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.league.telegram.entity.User;
import ru.league.telegram.rest.TinderUserApi;
import ru.league.telegram.service.UserService;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TinderUserApi tinderUserApi;

    public User rebaseBot(User user) {
        log.debug("Перезапуск бота!");
        deleteUser(user);
        return updateUserByTinder(createNewUser(user.getChatId()), tinderUserApi.createUser());
    }

    public void deleteUser(User user) {
        log.debug("Удаление данных по пользователю - '{}'", user);
        tinderUserApi.deleteUser(user);
        userService.delete(user);
    }

    public User getUser(Long chatId) {
        log.debug("Получение пользователя из Tinder");
        User user = userService.getUserByChatId(chatId);

        if (user == null) {
            log.debug("Пользователь с данным Chat Id - '{}' не найден", chatId);
            user = createNewUser(chatId);
            log.debug("Создан новый пользователь - '{}'", user);
            return updateUserByTinder(user, tinderUserApi.createUser());

        } else {
            log.debug("Найден пользователь - '{}'", user);
            if (user.getTinderId() == null) {
                log.debug("Пользователь - '{}' не связан с Tinder, создаем новую связь", user);
                return updateUserByTinder(user, tinderUserApi.createUser());

            } else {
                log.debug("Получен пользователь - '{}'", user);
                return user;
            }
        }
    }

    public void updateUserWait(User user, Method wait) {
        log.debug("Обновление пользователя - '{}'", user);
        user.setWait(wait);
        userService.update(user);
    }

    private User createNewUser(Long chatId) {
        log.debug("Создание нового пользователя с Chat Id - '{}'", chatId);
        return userService.create(new User(chatId));
    }

    private User updateUserByTinder(User user, Long tinderId) {
        log.debug("Обновление пользователя - '{}', данными из Tinder - '{}'", user, tinderId);
        user.setTinderId(tinderId);
        log.debug("Сохранение обновлений - '{}'", user);
        return userService.update(user);
    }
}

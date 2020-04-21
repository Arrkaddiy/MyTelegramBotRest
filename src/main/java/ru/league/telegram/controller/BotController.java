package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.league.telegram.bot.Bot;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.entity.User;
import ru.league.telegram.model.UserModel;
import ru.league.telegram.rest.TinderUserApi;
import ru.league.telegram.service.UserService;

@Controller
public class BotController {

    private static final Logger log = LoggerFactory.getLogger(BotController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TinderUserApi tinderUserApi;

    @Autowired
    private StateController stateController;


    public void of(Bot bot, Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;

        log.debug("Новый запрос:");
        final String input = update.getMessage().getText();
        log.debug("Получено тело сообщения - '{}'", input);
        final long chatId = update.getMessage().getChatId();
        log.debug("Получен код чата - '{}'", chatId);

        User user = getUser(chatId);
        log.debug("Получен пользователь - '{}'", user);

        if (input.equalsIgnoreCase("/start")) {
            user = rebaseBot(user);
        }

        stateController.by(BotContext.of(bot, user, input));
    }

    private User rebaseBot(User user) {
        log.debug("Перезапуск бота!");
        deleteUser(user);
        return updateUserByUserModel(createNewUser(user.getChatId()), tinderUserApi.create());
    }

    private void deleteUser(User user) {
        log.debug("Удаление данных по пользователю - '{}'", user);
        tinderUserApi.delete(user);
        userService.delete(user);
    }

    private User getUser(Long chatId) {
        log.debug("Получение пользователя из Tinder");
        UserModel userModel;
        User user = userService.getUserByChatId(chatId);

        if (user == null) {
            log.debug("Пользователь с данным Chat Id - '{}' не найден!", chatId);
            user = createNewUser(chatId);
            userModel = tinderUserApi.create();
            log.debug("Создан новый пользователь - '{}'!", user);
        } else {
            log.debug("Найден пользователь - '{}'", user);
            userModel = tinderUserApi.get(user);
        }

        return updateUserByUserModel(user, userModel);
    }

    private User createNewUser(Long chatId) {
        log.debug("Создание нового пользователя с Chat Id - '{}'", chatId);
        return userService.create(new User(chatId));
    }

    private User updateUserByUserModel(User user, UserModel userModel) {
        log.debug("Обновление пользователя - '{}', данными из Tinder - '{}'", user, userModel);
        User updateUser = user.updateByUserModel(userModel);
        log.debug("Сохранение обновлений - '{}'", updateUser);
        return userService.update(user);
    }

}

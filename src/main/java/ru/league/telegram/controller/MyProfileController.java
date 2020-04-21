package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.bot.BotSendMessage;
import ru.league.telegram.entity.User;
import ru.league.telegram.rest.TinderMethodApi;
import ru.league.telegram.rest.TinderMyProfileApi;
import ru.league.telegram.rest.TinderProfilesApi;

import java.util.List;

@Controller
public class MyProfileController implements BotSendMessage {

    private static final Logger log = LoggerFactory.getLogger(MyProfileController.class);

    @Autowired
    private TinderMyProfileApi myProfileApi;

    @Autowired
    private ProfilesController profilesController;

    @Autowired
    private TinderMethodApi methodApi;

    @Autowired
    private UserController userController;

    public void profileMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String response = myProfileApi.profileMethod(context.getUser());
        List<String> methods = methodApi.getPossibleMethods(context.getUser()).getPossibleMethods();
        sendTextMessageWithKeyboard(context, response, methods);
    }

    public void singInMethod(BotContext context) {
        log.debug("Обработка запроса по контексту - '{}'", context);
        userController.updateUserWait(context.getUser(), Method.SING_IN);
        sendTextMessage(context, "Сударь иль сударыня, введите логинъ и пароль черезъ пробѣлъ:");
    }

    public void singInWaitMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String[] params = context.getInput().split("\\s");
        if (params.length == 2) {
            String name = params[0];
            String pass = params[1];
            String response = myProfileApi.singInMethod(context.getUser(), name, pass);

            if (response.equalsIgnoreCase("Успехъ")) {
                User user = context.getUser();
                userController.updateUserWait(user, null);
                sendTextMessage(context, response);
                profilesController.leftMethod(context);

            } else {
                sendTextMessage(context, response);
            }

        } else {
            sendTextMessage(context, "Неудача, попробуйте снова");
        }
    }

    public void singUpMethod(BotContext context) {
        log.debug("Обработка запроса по контексту - '{}'", context);
        userController.updateUserWait(context.getUser(), Method.SING_UP);
        sendTextMessage(context, "Вы сударь иль сударыня? Как вас величать? Ваш секретный шифръ?");
    }

    public void singUpWaitMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String[] params = context.getInput().split("\\s");
        if (params.length == 3) {
            String name = params[1];
            String pass = params[2];
            String sex = params[0];
            String response = myProfileApi.singUpMethod(context.getUser(), name, pass, sex);

            if (response.equalsIgnoreCase("Успехъ")) {
                User user = context.getUser();
                userController.updateUserWait(context.getUser(), null);
                sendTextMessage(context, response);
                profilesController.leftMethod(context);

            } else {
                sendTextMessage(context, response);
            }

        } else {
            sendTextMessage(context, "Неудача, попробуйте снова");
        }
    }

    public void singOutMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String response = myProfileApi.singOutMethod(context.getUser());
        List<String> methods = methodApi.getPossibleMethods(context.getUser()).getPossibleMethods();
        sendTextMessageWithKeyboard(context, response, methods);
    }

    public void updateMethod(BotContext context) {
        log.debug("Обработка запроса по контексту - '{}'", context);
        userController.updateUserWait(context.getUser(), Method.UPDATE);
        sendTextMessage(context, "Опишите себя");
    }

    public void updateWaitMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String response = myProfileApi.updateMethod(context.getUser(), context.getInput());
        userController.updateUserWait(context.getUser(), null);
        sendTextMessage(context, response);
    }
}

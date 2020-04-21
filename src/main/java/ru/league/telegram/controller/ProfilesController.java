package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.bot.BotSendMessage;
import ru.league.telegram.model.ProfileModel;
import ru.league.telegram.rest.TinderProfilesApi;
import ru.league.telegram.rest.TinderMethodApi;
import ru.league.telegram.rest.TinderUserApi;

import java.util.List;

@Controller
public class ProfilesController implements BotSendMessage {

    private static final Logger log = LoggerFactory.getLogger(ProfilesController.class);

    @Autowired
    private TinderProfilesApi profilesApi;

    @Autowired
    private TinderMethodApi methodApi;

    @Autowired
    private TinderUserApi userApi;

    public void leftMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        ProfileModel profileModel = profilesApi.leftMethod(context.getUser());
        log.debug("Получна Анкета - '{}'", profileModel);
        List<String> methods = methodApi.getPossibleMethods(context.getUser()).getPossibleMethods();
        String response = profileModel.getProfile();
        sendTextMessageWithKeyboard(context, response, methods);
    }

    public void rightMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        ProfileModel profileModel = userApi.getLastLookProfile(context.getUser());
        log.debug("Получена крайняя Анкета - '{}'", profileModel);
        String response = profilesApi.rightMethod(context.getUser(), profileModel);
        if (response != null && !response.isEmpty()) {
            sendTextMessage(context, response);
        }
        leftMethod(context);
    }
}

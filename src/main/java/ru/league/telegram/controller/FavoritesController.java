package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.bot.BotSendMessage;
import ru.league.telegram.rest.TinderFavoritesApi;
import ru.league.telegram.rest.TinderMethodApi;

import java.util.List;

@Controller
public class FavoritesController implements BotSendMessage {

    private static final Logger log = LoggerFactory.getLogger(FavoritesController.class);

    @Autowired
    private TinderFavoritesApi favoritesApi;

    @Autowired
    private TinderMethodApi methodApi;

    @Autowired
    private UserController userController;

    @Autowired
    private ProfilesController profilesController;

    public void favoritesMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String response = favoritesApi.favoritesMethod(context.getUser());
        userController.updateUserWait(context.getUser(), Method.FAVORITES);
        List<String> methods = methodApi.getPossibleMethods(context.getUser()).getPossibleMethods();
        sendTextMessageWithKeyboard(context, response, methods);
    }

    public void favoritesWaitMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String response = favoritesApi.favoriteNumberMethod(context.getUser(), context.getInput());
        if (response == null || response.isEmpty()) {
            response = "<Здесь могла быть ваша реклама>";
        }
        sendTextMessage(context, response);
    }

    public void exitMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String response = favoritesApi.exitMethod(context.getUser());
        userController.updateUserWait(context.getUser(), null);
        List<String> methods = methodApi.getPossibleMethods(context.getUser()).getPossibleMethods();
        sendTextMessageWithKeyboard(context, response, methods);
        profilesController.leftMethod(context);
    }
}

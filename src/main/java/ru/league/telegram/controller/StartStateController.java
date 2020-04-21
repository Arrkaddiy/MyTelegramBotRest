package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.bot.BotSendMessage;
import ru.league.telegram.rest.TinderMethodApi;
import ru.league.telegram.rest.TinderStartStateApi;

import java.util.List;

@Controller
public class StartStateController implements BotSendMessage {

    private static final Logger log = LoggerFactory.getLogger(StartStateController.class);

    @Autowired
    private TinderStartStateApi startStateApi;

    @Autowired
    private TinderMethodApi methodApi;

    public void startMethod(BotContext context) {
        log.debug("Отправка запроса по контексту - '{}'", context);
        String response = startStateApi.startMethod(context.getUser());
        List<String> methods = methodApi.getPossibleMethods(context.getUser()).getPossibleMethods();
        sendTextMessageWithKeyboard(context, response, methods);
    }
}

package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.config.StateConfig;
import ru.league.telegram.state.State;

@Component
public class StateController {

    private static final Logger log = LoggerFactory.getLogger(StateController.class);

    @Autowired
    private StateConfig config;

    public void by(BotContext context) {
        log.debug("Передача запроса на состояние по контексту - '{}'", context);
        State state = config.getState(context.getUser().getStateName());
    }
}

package ru.league.telegram.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.league.telegram.controller.StateController;
import ru.league.telegram.state.StartState;
import ru.league.telegram.state.State;

@Component
public class StateConfig {

    private static final Logger log = LoggerFactory.getLogger(StateController.class);

    @Autowired
    private StartState startState;

    public State getState(String stateName) {
        log.debug("Определение состояния по наименованию состояния - '{}'", stateName);
        StateType type = StateType.valueOf(stateName.replaceFirst("/", "").toUpperCase());
        log.debug("Определено состояние - '{}'", type);

        switch (type) {
            case START: {
                return startState;
            }

            default: {
                throw new IllegalArgumentException("Для данного типа - '{" + type + "}', не найдено совподение по состоянию!");
            }
        }
    }

    private enum StateType {
        START
    }
}

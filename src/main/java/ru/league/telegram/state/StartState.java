package ru.league.telegram.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.model.UserModel;
import ru.league.telegram.rest.TinderStartStateApi;

import java.util.ArrayList;
import java.util.List;

public class StartState implements State, StateMessage {

    private static final Logger log = LoggerFactory.getLogger(StartState.class);

    @Autowired
    private TinderStartStateApi startStateApi;

    @Override
    public void execute(BotContext context) {
        String input = context.getInput();
        log.debug("Определение метода API по входному параметру - '{}'", input);
        Method method = Method.valueOf(input.replaceFirst("/", "").toUpperCase());
        log.debug("Определен метод API - '{}'", method);
    }

    private void startMethod(BotContext context) {
        log.debug("Выполнение метода - /start");
        UserModel response = startStateApi.startMethod(context.getUser());
        sendTextMessageWithKeyboard(context, response.getResponse(), getKeyboard());
    }

    private void leftMethod(BotContext context) {
        log.debug("Выполнение метода - /left");
        UserModel response = startStateApi.startMethod(context.getUser());
        sendTextMessageWithKeyboard(context, response.getResponse(), getKeyboard());
    }

    private ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        keyboardRowList.add(getKeyLeft());
        keyboardRowList.add(getKeyProfile());
        keyboardRowList.add(getKeyFavorites());

        keyboardMarkup.setKeyboard(keyboardRowList);
        return keyboardMarkup;
    }

    private KeyboardRow getKeyLeft() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("/left"));
        return keyboardRow;
    }

    private KeyboardRow getKeyProfile() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("/profile"));
        return keyboardRow;
    }

    private KeyboardRow getKeyFavorites() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("/favorites"));
        return keyboardRow;
    }

    private enum Method {
        START,
        LEFT,
        PROFILE,
        FAVORITES
    }
}

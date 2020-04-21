package ru.league.telegram.bot;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public interface BotSendMessage {

    default void sendTextMessage(BotContext context, String text) {
        SendMessage message = new SendMessage()
                .setChatId(context.getUser().getChatId())
                .setText(text);

        try {
            context.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    default void sendTextMessageWithKeyboard(BotContext context, String text, List<String> possibleMethods) {
        ReplyKeyboardMarkup keyboard = getKeyboard(possibleMethods);

        SendMessage message = new SendMessage()
                .setChatId(context.getUser().getChatId())
                .setReplyMarkup(keyboard)
                .setText(text);

        try {
            context.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    default ReplyKeyboardMarkup getKeyboard(List<String> possibleMethods) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        possibleMethods.forEach(method -> keyboardRowList.add(getKeyboardRow(method)));

        keyboardMarkup.setKeyboard(keyboardRowList);
        return keyboardMarkup;
    }

    default KeyboardRow getKeyboardRow(String method) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(method));
        return keyboardRow;
    }
}

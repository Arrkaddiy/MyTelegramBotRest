package ru.league.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.league.telegram.bot.Bot;
import ru.league.telegram.bot.BotContext;
import ru.league.telegram.entity.User;

@Controller
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private UserController userController;

    @Autowired
    private StartStateController startStateController;

    @Autowired
    private ProfilesController profilesController;

    @Autowired
    private MyProfileController myProfileController;

    @Autowired
    private FavoritesController favoritesController;

    public void execute(Bot bot, Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;

        log.debug("Новый запрос:");
        final String input = update.getMessage().getText();
        log.debug("Получено тело сообщения - '{}'", input);
        final long chatId = update.getMessage().getChatId();
        log.debug("Получен код чата - '{}'", chatId);

        User user = userController.getUser(chatId);
        log.debug("Получен пользователь - '{}'", user);

        if (input.equalsIgnoreCase("/start")) {
            user = userController.rebaseBot(user);
        }

        BotContext context = BotContext.of(bot, user, input);

        if (user.getWait() == null) {
            matchByMethod(context);
        } else {
            matchByWaitMethod(context);
        }
    }

    private void matchByMethod(BotContext context) {
        log.debug("Определение API согласно запросу - '{}'", context.getInput());
        Method method = Method.valueOf(context.getInput().toUpperCase().replaceFirst("/", ""));
        log.debug("Определен метод - '{}'", method);
        switch (method) {
            case START: {
                startStateController.startMethod(context);
                break;
            }

            case LEFT: {
                profilesController.leftMethod(context);
                break;
            }

            case RIGHT: {
                profilesController.rightMethod(context);
                break;
            }

            case PROFILE: {
                myProfileController.profileMethod(context);
                break;
            }

            case SING_IN: {
                myProfileController.singInMethod(context);
                break;
            }

            case SING_UP: {
                myProfileController.singUpMethod(context);
                break;
            }

            case SING_OUT: {
                myProfileController.singOutMethod(context);
                break;
            }

            case UPDATE: {
                myProfileController.updateMethod(context);
                break;
            }

            case FAVORITES: {
                favoritesController.favoritesMethod(context);
                break;
            }

            case EXIT: {
                favoritesController.exitMethod(context);
                break;
            }

            default: {
                throw new IllegalArgumentException("Не определн API для метода!");
            }
        }
    }

    private void matchByWaitMethod(BotContext context) {
        log.debug("Определение API c ожиданием согласно запросу - '{}'", context.getInput());
        if (context.getInput().equalsIgnoreCase("/exit")) {
            favoritesController.exitMethod(context);
            return;
        }

        switch (context.getUser().getWait()) {
            case SING_IN: {
                myProfileController.singInWaitMethod(context);
                break;
            }

            case SING_UP: {
                myProfileController.singUpWaitMethod(context);
                break;
            }

            case UPDATE: {
                myProfileController.updateWaitMethod(context);
                break;
            }

            case FAVORITES: {
                favoritesController.favoritesWaitMethod(context);
                break;
            }
        }
    }

}

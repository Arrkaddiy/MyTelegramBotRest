package ru.league.telegram.state;

import ru.league.telegram.bot.BotContext;

public interface State {

    void execute(BotContext context);
}

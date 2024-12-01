package pab.ta.handler.tgbot.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateDispatcher {

    boolean canHandle(Update update);

    void dispatch(Update update);
}

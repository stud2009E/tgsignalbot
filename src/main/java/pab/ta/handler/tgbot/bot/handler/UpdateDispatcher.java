package pab.ta.handler.tgbot.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface UpdateDispatcher {

    boolean canHandle(Update update);

    void dispatch(Update update) throws TelegramApiException;
}

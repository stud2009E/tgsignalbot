package pab.ta.handler.tgbot.bot.handler.button;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import pab.ta.handler.tgbot.bot.handler.ActionHandler;

import java.util.List;

public interface ButtonHandler<T> extends ActionHandler {

    /**
     * Return messages with buttons for current button handler
     *
     * @param data   required data for callback
     * @param chatId chat to answer
     * @return messages
     */
    List<SendMessage> getButtonsForMe(T data, Long chatId);



}

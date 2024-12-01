package pab.ta.handler.tgbot.helpers;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;

public class Utils {

    public static UUID uuid(Update update) {
        return new UUID(chatId(update), userId(update));
    }

    public static UUID uuid(Long userId, Long chatId) {
        return new UUID(userId, chatId);
    }

    public static Long chatId(Update update) {

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else {
            return update.getMessage().getChat().getId();
        }
    }

    public static Long userId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        } else {
            return update.getMessage().getFrom().getId();
        }
    }

}

package pab.ta.handler.tgbot.bot.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class StateStore {

    private final Map<UUID, StateRecord> store = new HashMap<>();

    public void set(Update update, String scenarioId, String stepId) {
        Long userId = Utils.userId(update);
        Long chatId = Utils.chatId(update);

        StateRecord record = new StateRecord(userId, chatId, scenarioId, stepId);

        store.put(Utils.uuid(userId, chatId), record);
    }

    public void setOrRemove(Update update, String scenarioId, String stepId) {
        if (Objects.isNull(stepId)) {
            remove(update);
            return;
        }

        set(update, scenarioId, stepId);
    }

    public StateRecord remove(Long userId, Long chatId) {
        return store.remove(Utils.uuid(userId, chatId));

    }

    public void remove(Update update) {
        Long userId = Utils.userId(update);
        Long chatId = Utils.chatId(update);

        store.remove(Utils.uuid(userId, chatId));
    }

    public StateRecord get(Long userId, Long chatId) {
        return store.get(Utils.uuid(userId, chatId));
    }

    public StateRecord get(Update update) {
        Long userId = Utils.userId(update);
        Long chatId = Utils.chatId(update);

        return store.get(Utils.uuid(userId, chatId));
    }
}

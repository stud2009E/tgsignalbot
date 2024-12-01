package pab.ta.handler.tgbot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import pab.ta.handler.tgbot.bot.handler.UpdateDispatcher;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final Map<String, UpdateDispatcher> dispatcherMap;

    @Override
    public void consume(Update update) {
        for (UpdateDispatcher dispatcher : dispatcherMap.values()) {
            if (dispatcher.canHandle(update)) {
                dispatcher.dispatch(update);
                return;
            }
        }
    }
}
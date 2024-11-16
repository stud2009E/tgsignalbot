package pab.ta.handler.tgbot.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@RequiredArgsConstructor
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient client;

    @Override
    public void consume(Update update) {


    }
}
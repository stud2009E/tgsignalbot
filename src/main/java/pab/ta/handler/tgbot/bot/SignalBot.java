package pab.ta.handler.tgbot.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@RequiredArgsConstructor
public class SignalBot implements SpringLongPollingBot {

    private final String token;
    private final LongPollingUpdateConsumer consumer;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return consumer;
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession session) {
        System.out.println(session);
    }
}
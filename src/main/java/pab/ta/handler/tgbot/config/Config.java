package pab.ta.handler.tgbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.SignalBot;
import pab.ta.handler.tgbot.bot.UpdateConsumer;

@Configuration
@PropertySource("classpath:secret.properties")
public class Config {

    @Value("${telegram.api.token}")
    private String token;

    @Bean
    public TelegramClient tgClient() {
        return new OkHttpTelegramClient(token);
    }

    @Bean
    public SignalBot signalBot(LongPollingUpdateConsumer consumer) {
        return new SignalBot(token, consumer);
    }

    @Bean
    public LongPollingUpdateConsumer consumer(TelegramClient client) {
        return new UpdateConsumer(client);
    }

}

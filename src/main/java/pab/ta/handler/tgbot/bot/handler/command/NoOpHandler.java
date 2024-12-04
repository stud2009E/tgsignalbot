package pab.ta.handler.tgbot.bot.handler.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.scenario.Step;

@Setter
@Getter
@RequiredArgsConstructor
@Component("/noop")
public class NoOpHandler implements CommandHandler {

    private Step step;

    private final TelegramClient client;

    @Override
    public String process(Update update) throws TelegramApiException {
        SendMessage msg = SendMessage.builder()
                .text("Command is in development!")
                .build();

        client.execute(msg);

        return null;
    }

}

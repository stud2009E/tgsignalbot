package pab.ta.handler.tgbot.bot.scenario.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.handler.command.CommandHandler;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.helpers.Utils;


@Setter
@Getter
@Component("SearchCommandHandler")
@RequiredArgsConstructor
public class SearchCommandHandler implements CommandHandler {

    private Step step;

    private final TelegramClient client;

    @Override
    public String process(Update update) throws TelegramApiException {

        SendMessage msg = SendMessage.builder()
                .chatId(Utils.chatId(update))
                .text("Input ticker for search:")
                .build();

        client.execute(msg);

        return step.getStepTrue();
    }
}
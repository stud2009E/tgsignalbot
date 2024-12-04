package pab.ta.handler.tgbot.bot.scenario.reset;

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
@Component("ResetCommandHandler")
@RequiredArgsConstructor
public class ResetCommandHandler implements CommandHandler {

    private Step step;
    private final TelegramClient client;

    @Override
    public String process(Update update) throws TelegramApiException {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(Utils.chatId(update))
                .text("Scenario processing has been interrupted! To continue, select the command.")
                .build();

        client.execute(sendMessage);

        return step.getStepTrue();
    }
}
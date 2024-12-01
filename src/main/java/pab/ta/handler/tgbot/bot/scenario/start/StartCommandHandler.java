package pab.ta.handler.tgbot.bot.scenario.start;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pab.ta.handler.tgbot.bot.handler.command.CommandHandler;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.List;


@Setter
@Getter
@Component("StartCommandHandler")
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {

    private Step step;

    @Override
    public ActionMessage process(Update update) {

        String name = update.getMessage().getFrom().getFirstName();
        if (name.isEmpty() || name.isBlank()) {
            name = "incognito";
        }

        SendMessage msg = SendMessage.builder()
                .chatId(Utils.chatId(update))
                .text(String.format("Hello, %s!", name))
                .build();

        return new ActionMessage(List.of(msg), step.getStepTrue());
    }
}
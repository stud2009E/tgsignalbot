package pab.ta.handler.tgbot.bot.scenario.search;

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
@Component("SearchCommandHandler")
@RequiredArgsConstructor
public class SearchCommandHandler implements CommandHandler {

    private Step step;

    @Override
    public ActionMessage process(Update update) {

        SendMessage msg = SendMessage.builder()
                .chatId(Utils.chatId(update))
                .text("Input ticker for search:")
                .build();

        return new ActionMessage(List.of(msg), step.getStepTrue());
    }
}
package pab.ta.handler.tgbot.bot.handler.command;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pab.ta.handler.tgbot.bot.scenario.Step;

import java.util.List;

@Setter
@Getter
@Component("/noop")
public class NoOpHandler implements CommandHandler {

    private Step step;

    @Override
    public ActionMessage process(Update update) {
        SendMessage msg = SendMessage.builder()
                .text("Command is in development!")
                .build();

        return new ActionMessage(List.of(msg), null);
    }

}

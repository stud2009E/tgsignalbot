package pab.ta.handler.tgbot.bot.handler.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pab.ta.handler.tgbot.bot.handler.UpdateDispatcher;
import pab.ta.handler.tgbot.bot.scenario.Scenario;
import pab.ta.handler.tgbot.bot.scenario.ScenarioFactory;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.bot.state.StateStore;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommandDispatcher implements UpdateDispatcher {

    private final ScenarioFactory factory;
    private final StateStore store;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.getMessage();

        try {
            factory.getInstance(update.getMessage().getText());
        } catch (Exception ignored) {
            return false;
        }

        return Objects.nonNull(message)
                && message.isCommand()
                && !update.hasCallbackQuery();
    }

    public void dispatch(Update update) throws TelegramApiException {
        if (!canHandle(update)) {
            throw new IllegalArgumentException("Not a command!");
        }
        
        Scenario scenario = factory.getInstance(update.getMessage().getText());

        Step startStep = scenario.getStartStep();

        CommandHandler handler = (CommandHandler) startStep.getAction();
        String stepId = handler.process(update);

        store.setOrRemove(update, scenario.getId(), stepId);
    }
}

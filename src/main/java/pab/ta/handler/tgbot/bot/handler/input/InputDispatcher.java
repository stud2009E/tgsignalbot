package pab.ta.handler.tgbot.bot.handler.input;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pab.ta.handler.tgbot.bot.handler.HandlerType;
import pab.ta.handler.tgbot.bot.handler.UpdateDispatcher;
import pab.ta.handler.tgbot.bot.scenario.Scenario;
import pab.ta.handler.tgbot.bot.scenario.ScenarioFactory;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.bot.state.StateRecord;
import pab.ta.handler.tgbot.bot.state.StateStore;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class InputDispatcher implements UpdateDispatcher {

    private final StateStore store;
    private final ScenarioFactory factory;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.getMessage();
        StateRecord state = store.get(update);

        if (Objects.isNull(state)) {
            return false;
        }

        Scenario scenario;
        try {
            scenario = factory.createInstance(state.getScenarioId());
        } catch (Exception ignored) {
            return false;
        }

        Step step = scenario.getStep(state.getStepId());
        if (Objects.isNull(step) || !step.getTrigger().equals(HandlerType.INPUT)) {
            return false;
        }

        return Objects.nonNull(message)
                && !message.isCommand()
                && !update.hasCallbackQuery();
    }

    @Override
    public void dispatch(Update update) throws TelegramApiException {
        if (!canHandle(update)) {
            throw new IllegalArgumentException("Not a text input!");
        }

        StateRecord state = store.get(update);
        Scenario scenario = factory.createInstance(state.getScenarioId());
        Step step = scenario.getStep(state.getStepId());

        String stepId = step.getActionHandler().process(update);

        store.setOrRemove(update, scenario.getId(), stepId);
    }
}

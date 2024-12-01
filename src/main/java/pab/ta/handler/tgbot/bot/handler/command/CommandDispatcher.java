package pab.ta.handler.tgbot.bot.handler.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.handler.ActionHandler.ActionMessage;
import pab.ta.handler.tgbot.bot.handler.UpdateDispatcher;
import pab.ta.handler.tgbot.bot.scenario.Scenario;
import pab.ta.handler.tgbot.bot.scenario.ScenarioFactory;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.bot.state.StateStore;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommandDispatcher implements UpdateDispatcher {

    private final TelegramClient client;
    private final ScenarioFactory factory;
    private final StateStore store;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.getMessage();

        return Objects.nonNull(message)
                && message.isCommand()
                && !update.hasCallbackQuery();
    }

    public void dispatch(Update update) {
        if (!canHandle(update)) {
            throw new IllegalArgumentException("Not a command!");
        }

        Scenario scenario = factory.createInstance("scenario" + update.getMessage().getText() + ".yaml");

        if (Objects.isNull(scenario)) {
            throw new RuntimeException("Scenario loading error!");
        }
        Step startStep = scenario.getStartStep();

        CommandHandler handler = (CommandHandler) startStep.getActionHandler();
        ActionMessage actionMessage = handler.process(update);

        try {
            for (SendMessage sendMessage : actionMessage.messages()) {
                client.execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        store.setOrRemove(update, scenario.getId(), actionMessage.nextStepId());
    }
}

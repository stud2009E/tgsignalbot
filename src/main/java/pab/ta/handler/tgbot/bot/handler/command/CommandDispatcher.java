package pab.ta.handler.tgbot.bot.handler.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.handler.UpdateDispatcher;
import pab.ta.handler.tgbot.bot.scenario.Scenario;
import pab.ta.handler.tgbot.bot.scenario.ScenarioFactory;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.bot.state.StateStore;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommandDispatcher implements UpdateDispatcher {

    private final ScenarioFactory factory;
    private final StateStore store;
    private final TelegramClient client;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.getMessage();

        return Objects.nonNull(message)
                && message.isCommand()
                && !update.hasCallbackQuery();
    }

    public void dispatch(Update update) throws TelegramApiException {
        if (!canHandle(update)) {
            throw new IllegalArgumentException("Not a command!");
        }
        Scenario scenario;
        try {
            scenario = factory.createInstance("scenario" + update.getMessage().getText() + ".yaml");
        } catch (Exception ignored) {
            client.execute(
                    SendMessage.builder()
                            .chatId(Utils.chatId(update))
                            .text("Command is not implemented!")
                            .build()
            );
            return;
        }

        Step startStep = scenario.getStartStep();

        CommandHandler handler = (CommandHandler) startStep.getActionHandler();
        String stepId = handler.process(update);

        store.setOrRemove(update, scenario.getId(), stepId);
    }
}

package pab.ta.handler.tgbot.bot.handler.button;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.handler.ActionHandler;
import pab.ta.handler.tgbot.bot.handler.ActionHandler.ActionMessage;
import pab.ta.handler.tgbot.bot.handler.HandlerType;
import pab.ta.handler.tgbot.bot.handler.UpdateDispatcher;
import pab.ta.handler.tgbot.bot.scenario.Scenario;
import pab.ta.handler.tgbot.bot.scenario.ScenarioFactory;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.bot.state.StateRecord;
import pab.ta.handler.tgbot.bot.state.StateStore;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ButtonDispatcher implements UpdateDispatcher {
    private final TelegramClient client;
    private final ScenarioFactory factory;
    private final StateStore store;

    @Override
    public boolean canHandle(Update update) {
        StateRecord state = store.get(update);

        if (Objects.isNull(state)) {
            return false;
        }

        Scenario scenario = factory.createInstance("scenario" + state.getScenarioId() + ".yaml");
        if (Objects.isNull(scenario)) {
            return false;
        }

        Step step = scenario.getStep(state.getStepId());
        if (Objects.isNull(step) || !step.getTrigger().equals(HandlerType.BUTTON)) {
            return false;
        }

        return update.hasCallbackQuery();
    }

    @Override
    public void dispatch(Update update) {
        if (!canHandle(update)) {
            throw new IllegalArgumentException("Not a callback query!");
        }

        StateRecord state = store.get(update);
        Scenario scenario = factory.createInstance("scenario" + state.getScenarioId() + ".yaml");
        Step step = scenario.getStep(state.getStepId());

        CallbackQuery query = update.getCallbackQuery();

        ButtonHandler handler = (ButtonHandler) step.getActionHandler();
        ActionHandler.ParsedCallbackData buttonData = handler.parseCallBackData(query.getData());

        List<SendMessage> resultMessages = new ArrayList<>();
        ActionMessage actionMessage = null;

        if (!buttonData.scenarioId().equals(scenario.getId()) || !buttonData.stepId().equals(step.getId())) {
            resultMessages.add(SendMessage.builder()
                    .chatId(Utils.chatId(update))
                    .text("Waiting button press from message!")
                    .build());
        } else {
            actionMessage = handler.process(update);
            resultMessages.addAll(actionMessage.messages());
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(query.getId()).build();

        try {
            client.execute(close);

            for (SendMessage resultMessage : resultMessages) {
                client.execute(resultMessage);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        if (Objects.nonNull(actionMessage)) {
            store.setOrRemove(update, scenario.getId(), actionMessage.nextStepId());
        }
    }
}

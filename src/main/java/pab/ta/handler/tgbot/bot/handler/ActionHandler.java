package pab.ta.handler.tgbot.bot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pab.ta.handler.tgbot.bot.scenario.Step;

import java.util.List;

public interface ActionHandler {
    String SEPARATOR = "#";

    void setStep(Step step);

    Step getStep();

    ActionMessage process(Update update);

    default String formatCallBackData(String scenarioId, String stepId, String data) {
        return scenarioId
                + SEPARATOR
                + stepId
                + SEPARATOR
                + data;
    }

    default ParsedCallbackData parseCallBackData(String data) {
        String[] parts = data.split(SEPARATOR);

        return new ParsedCallbackData(parts[0], parts[1], parts[2]);
    }

    record ParsedCallbackData(String scenarioId, String stepId, String data) {
    }

    record ActionMessage(List<SendMessage> messages, String nextStepId) {
    }
}

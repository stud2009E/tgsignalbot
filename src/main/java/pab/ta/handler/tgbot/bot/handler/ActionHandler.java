package pab.ta.handler.tgbot.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pab.ta.handler.tgbot.bot.scenario.Step;

public interface ActionHandler {
    String SEPARATOR = "#";

    void setStep(Step step);

    Step getStep();

    String process(Update update) throws TelegramApiException;

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

}

package pab.ta.handler.tgbot.bot.scenario.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import pab.ta.handler.tgbot.bot.handler.button.ButtonHandler;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.List;

@Setter
@Getter
@Component("SearchButtonSaveHandler")
@RequiredArgsConstructor
public class SearchButtonSaveHandler implements ButtonHandler {

    private Step step;

    @Override
    public ActionMessage process(Update update) {
        CallbackQuery query = update.getCallbackQuery();

        Long chatId = Utils.chatId(update);
        ParsedCallbackData parsedQueryData = parseCallBackData(query.getData());

        String data = parsedQueryData.data();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Ticker <b>" + data + "</b> saved")
                .build();

        return new ActionMessage(List.of(sendMessage), step.getStepTrue());
    }

}

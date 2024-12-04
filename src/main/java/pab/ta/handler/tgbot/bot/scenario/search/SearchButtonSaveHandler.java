package pab.ta.handler.tgbot.bot.scenario.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.handler.button.ButtonHandler;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.helpers.Utils;

@Setter
@Getter
@Component("SearchButtonSaveHandler")
@RequiredArgsConstructor
public class SearchButtonSaveHandler implements ButtonHandler {

    private Step step;
    private final TelegramClient client;


    @Override
    public String process(Update update) throws TelegramApiException {
        CallbackQuery query = update.getCallbackQuery();

        Long chatId = Utils.chatId(update);
        ParsedCallbackData parsedQueryData = parseCallBackData(query.getData());

        String data = parsedQueryData.data();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Ticker <b>#" + data.toUpperCase() + "</b> saved")
                .parseMode("HTML")
                .build();

        client.execute(sendMessage);

        return step.getId();
    }

}

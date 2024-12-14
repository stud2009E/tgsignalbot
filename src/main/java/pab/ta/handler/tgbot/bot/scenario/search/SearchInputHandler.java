package pab.ta.handler.tgbot.bot.scenario.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.handler.input.InputHandler;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.List;

@Setter
@Getter
@Component("SearchInputHandler")
@RequiredArgsConstructor
public class SearchInputHandler implements InputHandler {

    private Step step;
    private final TelegramClient client;
    private final MockTickerSearch mockSearch;

    private final SearchButtonSaveHandler buttonSaveHandler;

    @Override
    public String process(Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String input = message.getText();

        if (input.length() < 3) {
            var sendMessage = SendMessage.builder()
                    .chatId(Utils.chatId(update))
                    .text("Input ticker, minimum 3 symbols:").build();

            client.execute(sendMessage);

            return step.getId();
        }

        List<MockTickerSearch.TickerInfo> tickers = mockSearch.getFor(message.getText());
        if (tickers.isEmpty()) {
            var sendMessage = SendMessage.builder()
                    .chatId(Utils.chatId(update))
                    .text("Nothing found. Try again!").build();

            client.execute(sendMessage);

            return step.getId();
        }

        Long chatId = Utils.chatId(update);
        List<SendMessage> messages = buttonSaveHandler.getButtonsForMe(tickers, chatId);

        for (var sendMessage : messages) {
            client.execute(sendMessage);
        }

        return step.getStepTrue();
    }
}

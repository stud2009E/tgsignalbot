package pab.ta.handler.tgbot.bot.scenario.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import pab.ta.handler.tgbot.bot.handler.input.InputHandler;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Setter
@Getter
@Component("SearchInputHandler")
@RequiredArgsConstructor
public class SearchInputHandler implements InputHandler {

    private Step step;

    record TickerInfo(String ticker, String type, String description) {
    }

    @Override
    public ActionMessage process(Update update) {
        Message message = update.getMessage();
        String input = message.getText();

        if (input.length() < 3) {
            return new ActionMessage(
                    List.of(SendMessage.builder()
                            .chatId(Utils.chatId(update))
                            .text("Input ticker, minimum 3 symbols:").build()),
                    step.getId()
            );
        }

        List<TickerInfo> tickers = mockSearch(message.getText());
        if (tickers.isEmpty()) {
            return new ActionMessage(
                    List.of(SendMessage.builder()
                            .chatId(Utils.chatId(update))
                            .text("Nothing found. Try again!").build()),
                    step.getId()
            );
        }

        String scenarioId = step.getScenario().getId();
        String stepTrueId = step.getStepTrue();


        List<SendMessage> messages = tickers.stream().map(info -> {
            var btn = InlineKeyboardButton.builder()
                    .text("save " + info.ticker())
                    .callbackData(formatCallBackData(scenarioId, stepTrueId, info.ticker()))
                    .build();

            var markup = InlineKeyboardMarkup.builder()
                    .keyboardRow(new InlineKeyboardRow(btn))
                    .build();

            var text = info.ticker() + " " + info.type() + " " + info.description();

            return (SendMessage) SendMessage.builder()
                    .chatId(Utils.chatId(update))
                    .parseMode("HTML")
                    .text(text)
                    .replyMarkup(markup)
                    .build();
        }).toList();


        return new ActionMessage(messages, step.getStepTrue());
    }


    private List<TickerInfo> mockSearch(String text) {
        var result = Stream.iterate(1, a -> a + 1)
                .limit(5)
                .map(i -> new TickerInfo(text + i, "Type" + i, "description" + i))
                .toList();

        int rnd = new Random().nextInt(10);

        return rnd > 5 ? result : List.of();
    }

}

package pab.ta.handler.tgbot.bot.scenario.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.handler.button.ButtonHandler;
import pab.ta.handler.tgbot.bot.scenario.Step;
import pab.ta.handler.tgbot.bot.scenario.search.dto.AssetInfoDto;
import pab.ta.handler.tgbot.bot.state.StateStore;
import pab.ta.handler.tgbot.data.documents.BotUser;
import pab.ta.handler.tgbot.data.service.BotUserService;
import pab.ta.handler.tgbot.helpers.Utils;

import java.util.List;

@Setter
@Getter
@Component("SearchButtonSaveHandler")
@RequiredArgsConstructor
public class SearchButtonSaveHandler implements ButtonHandler<List<AssetInfoDto>> {

    private Step step;
    private final TelegramClient client;
    private final BotUserService service;
    private final StateStore store;

    @Override
    public String process(Update update) throws TelegramApiException {
        CallbackQuery query = update.getCallbackQuery();
        Message message = (Message) query.getMessage();
        Long userId = query.getFrom().getId();
        Long chatId = Utils.chatId(update);

        ParsedCallbackData callbackData = parseCallBackData(query.getData());
        String tickerData = callbackData.data();

        OpsDecoder ops = OpsDecoder.decode(tickerData);

        OpsDecoder newOps = ops.operation() == Operation.SAVE
                ? new OpsDecoder(ops.data(), Operation.REMOVE)
                : new OpsDecoder(ops.data(), Operation.SAVE);


        BotUser botUser = service.getUser(userId);
        if (ops.operation == Operation.SAVE) {
            botUser.addTicker(ops.data());
        } else {
            botUser.removeTicker(ops.data());
        }
        service.save(botUser);


        String scenarioId = step.getScenario().getId();
        String stepId = step.getId();


        var btn = InlineKeyboardButton.builder()
                .text(newOps.toString())
                .callbackData(formatCallBackData(scenarioId, stepId, newOps.encode()))
                .build();

        var markup = InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(btn))
                .build();

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(message.getMessageId())
                .replyMarkup(markup)
                .build();

        client.execute(newKb);

        return step.getId();
    }

    public enum Operation {
        SAVE,
        REMOVE
    }

    record OpsDecoder(String data, Operation operation) {

        private static final String SEPARATOR = "!!!";

        @Override
        public String toString() {
            return String.format("%s %s", operation.name().toLowerCase(), data);
        }

        public String encode() {
            return data + SEPARATOR + operation.name();
        }

        public static OpsDecoder decode(String str) {
            String[] parts = str.split(SEPARATOR);
            Operation op = Operation.valueOf(parts[1]);

            return new OpsDecoder(parts[0], op);
        }
    }

    @Override
    public List<SendMessage> getButtonsForMe(List<AssetInfoDto> tickers, Long chatId) {
        String scenarioId = step.getScenario().getId();
        String stepId = step.getId();

        return tickers.stream().map(info -> {
            OpsDecoder op = new OpsDecoder(info.getTicker(), Operation.SAVE);

            var btn = InlineKeyboardButton.builder()
                    .text(op.toString())
                    .callbackData(formatCallBackData(scenarioId, stepId, op.encode()))
                    .build();

            var markup = InlineKeyboardMarkup.builder()
                    .keyboardRow(new InlineKeyboardRow(btn))
                    .build();

            var text = info.getTicker() + " " + info.getType() + " " + info.getDescription();

            return (SendMessage) SendMessage.builder()
                    .chatId(chatId)
                    .parseMode("HTML")
                    .text(text)
                    .replyMarkup(markup)
                    .build();
        }).toList();
    }
}

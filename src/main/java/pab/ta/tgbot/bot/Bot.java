package pab.ta.tgbot.bot;

import jakarta.annotation.PostConstruct;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pab.ta.tgbot.entity.BotUserEntity;
import pab.ta.tgbot.entity.CommandText;
import pab.ta.tgbot.projection.AvailableCommandDTO;
import pab.ta.tgbot.service.AvailableCommandService;
import pab.ta.tgbot.service.BotUserService;
import pab.ta.tgbot.service.CommandTextService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private CommandTextService commandTextService;
    @Autowired
    private AvailableCommandService availableCommandService;
    private static final String START = "/start";
    private static final String HELP = "/help";
    private static final String REGISTRY = "/registry";
    private static final String GET = "/get";
    private static final String FIND = "/find";
    List<AvailableCommandDTO> availableCommand;
    List<CommandText> commandText;
    private CurrentUser currentUser;
    private UsersHolder usersHolder;

    public Bot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }
    @PostConstruct
    public void init() {
        availableCommand = availableCommandService.findAllText();
        commandText = commandTextService.findAll();

        List<BotCommand> list = new ArrayList<>();

        list.add(new BotCommand("help", "help"));
        list.add(new BotCommand("start", "start"));

        usersHolder = new UsersHolder();

        try {
            this.execute(new SetMyCommands(list, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        currentUser = usersHolder.getCurrentUser(update);

        if (update.hasCallbackQuery()) {
            //Обработка команды с клавиатуры
            commandHandler(update);
        } else {
            //Ответ пользователя
            responseHandler(update);
        }
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            unknownCommand(update.getMessage().getChatId());
        }
    }
    private void commandHandler(Update update) {

        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String command = update.getCallbackQuery().getData();

        //Установка текущей команды
        currentUser.setCommand(command);

        switch (command) {
            case START -> sendCurrentCommand(update, "Начнем");
            case HELP -> helpCommand(update);
            case REGISTRY -> registryCommand(chatId);
            case GET -> getCommand(chatId);
            case FIND -> findCommand(chatId);
            default -> unknownCommand(chatId);
        }
        //Установка предыдущей команды для последующего анализа
        currentUser.setPrevCommand(command);
    }
    private void responseHandler(Update update) {

        switch (update.getMessage().getText()) {
            case START -> sendCurrentCommand(update, "Начнем");
            case HELP -> helpCommand(update);
            default -> defaultHandler(update);
        }
    }
    private void defaultHandler(Update update){
        switch (currentUser.getPrevCommand()){
            case REGISTRY -> responseRegistryCommand(update);
            default ->  unknownCommand(update.getMessage().getChatId());
        }
    }
    private void getCommand(Long chatId) {
        sendMessage(chatId, "Get Command");
    }
    private void findCommand(Long chatId){
        sendMessage(chatId, "Find Command");
    }
    private void helpCommand(Update update) {
        sendMessage(currentUser.getUser(), "Добрый день!" + "\n" + "Какая-то инфа по боту...");
    }
    private void unknownCommand(Long chatId) {
        sendMessage(chatId, "¯\\_(ツ)_/¯");
    }

    private void registryCommand(Long chatId) {
        if (currentUser.getMode().equals("approved")){
            sendMessage(currentUser.getUser(), "Вы уже зарегистрированы");
            return;
        }
        sendMessage(chatId, "Введите свой email");
    }
    private void responseRegistryCommand(Update update) {

        if (currentUser.getMode().equals("approved")){
            sendMessage(currentUser.getUser(), "Вы уже зарегистрированы");
            return;
        }

        boolean isValid = EmailValidator.getInstance().isValid(update.getMessage().getText());
        if (isValid) {
            /*
            Отправка почты, пока не знаю как сделать
             */
            currentUser.setEmail(update.getMessage().getText());
            currentUser.setMode("approved");

            usersHolder.saveUser(currentUser);
            sendCurrentCommand(update, "Отлично!");
        } else {
            sendMessage(update.getMessage().getChatId()
                    , "Ошибка в " + update.getMessage().getText() + "\n" + "Введите еще раз");
        }
    }

    @Override
    public String getBotUsername() {
        return "BoJern01bot";
    }

    private void sendCurrentCommand(Update update, String text) {

        StringBuilder messageText = new StringBuilder();
        messageText.append(text)
                .append("\n")
                .append("Выберите действие:");

        String chatIdStr = currentUser.getUser().toString();
        SendMessage sender = new SendMessage(chatIdStr, messageText.toString());
        sender.setReplyMarkup(getKeyboard());

        try {
            execute(sender);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(Long chatId, String text) {
        String chatIdStr = String.valueOf(chatId);
        SendMessage sender = new SendMessage(chatIdStr, text);

        try {
            execute(sender);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Формирование кнопок клавиатуры
     * @return
     */
    private InlineKeyboardMarkup getKeyboard() {

        //список списков кнопок, который впоследствии ряды кнопок
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        //объект встроенной клавиатуры
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(rowsInline);

        //Не придумал как это изящно в один оператор сделать, пока только не изящно было
        //Получение списка доступных кнопок
        List<AvailableCommandDTO> commands = availableCommand.stream()
                .filter(command -> command.getMode().equals(currentUser.getMode()))
                .toList();

        //Получение списка списков доступных кнопок по 3 штуки
        List<List<AvailableCommandDTO>> pacCommands = IntStream.range(0, commands.size())
                .filter(i -> i % 3 ==0 )
                .mapToObj(i -> commands.subList(i, Math.min(i + 3, commands.size())))
                .toList();

        //Заполнение клавиатуры
        for(List<AvailableCommandDTO> pac : pacCommands){
            //список для ряда кнопок
            List<InlineKeyboardButton> buttons = pac.stream()
                    .map(command -> new InlineKeyboardButton(command.getText()
                            ,""
                            ,command.getId()
                            ,null
                            ,null
                            ,null
                            ,null
                            ,null
                            ,null))
                    .toList();
            rowsInline.add(buttons);
        }


        /*//Заполнение клавиатуры
        for(List<AvailableCommandDTO> pac : pacCommands){
            //список для ряда кнопок
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            for (AvailableCommandDTO command : pac) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(command.getText());
                inlineKeyboardButton.setCallbackData(command.getId());
                rowInline.add(inlineKeyboardButton);
            }
            rowsInline.add(rowInline);
        }

         */

        return keyboardMarkup;
    }

}

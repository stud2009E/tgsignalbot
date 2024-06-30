package pab.ta.tgbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import pab.ta.tgbot.entity.BotUserEntity;
import pab.ta.tgbot.service.BotUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsersHolder {

    private final Map<Long, CurrentUser> users = new HashMap<>();
    @Autowired
    private BotUserService botUserService;

    public CurrentUser getCurrentUser(Update update){

        Long chatId;
        String command = null;


        if (update.hasCallbackQuery()){
            //Значит была команда с клавиатуры
            chatId = update.getCallbackQuery().getMessage().getChatId();
            command = update.getCallbackQuery().getData();
        }else{
            //Простое сообщение
            chatId = update.getMessage().getChatId();
        }

        //Получение пользователя из списка
        CurrentUser currentUser = users.get(chatId);
        if (currentUser != null) {
            return currentUser;
        }

        //Не нашли в списке создаем и кладем в список
        currentUser = new CurrentUser();
        users.put(chatId, currentUser);

        //Ищем в базе
        Optional<BotUserEntity> botUser = botUserService.findById(chatId.toString());
        if (botUser.isPresent()) {
            //Нашли в базе, заполняем данными
            currentUser.setUser(Long.parseLong(botUser.get().getId()));
            currentUser.setCommand(command);
            currentUser.setMode(botUser.get().getMode());
            currentUser.setEmail(botUser.get().getEmail());
            //currentUser.setPrevCommand(botUser.get();
            return currentUser;
        }

        //Если в базе нет, то делаем нового
        currentUser.setUser(chatId);
        currentUser.setMode("new");

        return currentUser;
    }

    public void saveUser(CurrentUser currentUser){
        botUserService.save(new BotUserEntity(currentUser.getUser().toString()
                , currentUser.getEmail()
                , currentUser.getMode()));

    }
}

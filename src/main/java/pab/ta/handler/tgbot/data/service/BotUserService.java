package pab.ta.handler.tgbot.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import pab.ta.handler.tgbot.data.documents.BotUser;
import pab.ta.handler.tgbot.data.repository.UserRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BotUserService {

    private final UserRepo repo;

    public BotUser getUser(Long id) {
        Optional<BotUser> optionalBotUser = repo.findById(id);

        return optionalBotUser.orElse(null);
    }

    public void save(BotUser botUser) {
        repo.save(botUser);
    }

    public BotUser createUser(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();

        Optional<BotUser> optionalBotUser = repo.findById(user.getId());
        BotUser botUser;

        if (optionalBotUser.isPresent()) {
            return optionalBotUser.get();
        }
        botUser = new BotUser();
        botUser.setId(user.getId());
        botUser.setFirstName(user.getFirstName());
        botUser.setUserName(user.getFirstName());
        botUser.setLanguageCode(user.getLanguageCode());

        botUser.setChatId(message.getChatId());

        repo.save(botUser);
        return botUser;
    }
}

package pab.ta.handler.tgbot.data.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public BotUser createUser(Update update) {
        Message message = update.getMessage();
        User user = message.getFrom();

        Optional<BotUser> optionalBotUser = repo.findById(user.getId());
        BotUser botUser;
        if (optionalBotUser.isEmpty()) {
            botUser = new BotUser();
            botUser.setId(user.getId());
        } else {
            botUser = optionalBotUser.get();
        }

        botUser.setChatId(message.getChatId());
        botUser.setFirstName(user.getFirstName());
        botUser.setUserName(user.getUserName());
        botUser.setLanguageCode(user.getLanguageCode());

        repo.save(botUser);

        return botUser;
    }
}

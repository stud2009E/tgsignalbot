package pab.ta.tgbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pab.ta.tgbot.entity.BotUserEntity;
import pab.ta.tgbot.repository.BotUserRepository;

import java.util.Optional;

@Service
@Transactional
public class BotUserService {
    @Autowired
    private BotUserRepository botUserRepository;
    public Optional<BotUserEntity> findById(String id){
        return botUserRepository.findById(id);
    }
    public void save(BotUserEntity user){
        botUserRepository.save(user);
    };

}

package pab.ta.handler.tgbot.data.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pab.ta.handler.tgbot.data.documents.BotUser;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<BotUser, Long> {

    @NotNull
    Optional<BotUser> findById(@NotNull Long id);
}

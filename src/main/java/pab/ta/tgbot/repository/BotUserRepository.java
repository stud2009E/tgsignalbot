package pab.ta.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pab.ta.tgbot.entity.BotUserEntity;

@Transactional
@Repository
public interface BotUserRepository extends JpaRepository<BotUserEntity, String> {
}

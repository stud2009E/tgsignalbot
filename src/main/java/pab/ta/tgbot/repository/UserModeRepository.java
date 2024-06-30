package pab.ta.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pab.ta.tgbot.entity.UserModeEntity;

@Transactional
@Repository
public interface UserModeRepository extends JpaRepository<UserModeEntity, String> {
}

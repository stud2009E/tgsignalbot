package pab.ta.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pab.ta.tgbot.entity.CommandText;

@Transactional
@Repository
public interface CommandTextRepository extends JpaRepository<CommandText, String> {
}

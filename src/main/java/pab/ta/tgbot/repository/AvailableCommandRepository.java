package pab.ta.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pab.ta.tgbot.entity.AvailableCommandEntity;
import pab.ta.tgbot.entity.BotUserEntity;
import pab.ta.tgbot.projection.AvailableCommandDTO;

import java.util.List;

@Transactional
@Repository
public interface AvailableCommandRepository extends JpaRepository<AvailableCommandEntity, String> {

    List<AvailableCommandEntity> findAllByMode(String mode);
    @Query(value = "SELECT new pab.ta.tgbot.projection.AvailableCommandDTO("
    +"command.id AS id, "
    +"command.mode AS mode, "
    +"text.text AS text) "
    +"FROM AvailableCommandEntity AS command "
    +"JOIN CommandText AS text ON command.id = text.id ")
    List<AvailableCommandDTO> findAllText();
}

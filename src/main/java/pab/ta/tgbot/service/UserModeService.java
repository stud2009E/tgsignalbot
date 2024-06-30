package pab.ta.tgbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pab.ta.tgbot.entity.CommandText;
import pab.ta.tgbot.entity.UserModeEntity;
import pab.ta.tgbot.repository.CommandTextRepository;
import pab.ta.tgbot.repository.UserModeRepository;

@Service
public class UserModeService {
    @Autowired
    private UserModeRepository userModeRepository;
}

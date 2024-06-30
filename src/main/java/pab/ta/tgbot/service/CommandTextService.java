package pab.ta.tgbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pab.ta.tgbot.entity.CommandText;
import pab.ta.tgbot.repository.CommandTextRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommandTextService {

    @Autowired
    private CommandTextRepository commandTextRepository;

    public List<CommandText> findAll(){
        return commandTextRepository.findAll();
    }
}
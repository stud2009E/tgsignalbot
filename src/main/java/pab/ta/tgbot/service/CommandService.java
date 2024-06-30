package pab.ta.tgbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pab.ta.tgbot.repository.CommandRepository;

import java.util.Optional;

@Service
public class CommandService {
    @Autowired
    private CommandRepository commandRepository;

}

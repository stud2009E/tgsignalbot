package pab.ta.tgbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pab.ta.tgbot.entity.AvailableCommandEntity;
import pab.ta.tgbot.projection.AvailableCommandDTO;
import pab.ta.tgbot.repository.AvailableCommandRepository;

import java.util.List;

@Service
public class AvailableCommandService {
    @Autowired
    private AvailableCommandRepository availableCommandRepository;

    public List<AvailableCommandEntity> findAll(){
        return availableCommandRepository.findAll();
    }

    public List<AvailableCommandEntity> findAllByMode(String mode){
        return availableCommandRepository.findAllByMode(mode);
    }

    public List<AvailableCommandDTO> findAllText(){
        return availableCommandRepository.findAllText();
    }

}

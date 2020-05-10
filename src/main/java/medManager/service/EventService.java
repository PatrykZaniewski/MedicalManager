package medManager.service;

import medManager.dao.EventRepository;
import medManager.model.Event;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Event getOne(int id){
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isPresent()){
            return optionalEvent.get();
        }
        return null;
    }
}

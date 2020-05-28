package medManager.service;

import medManager.dao.OperationRepository;
import medManager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperationService {

    private OperationRepository operationRepository;
    private EventService eventService;

    @Autowired
    public OperationService(@Lazy OperationRepository operationRepository, @Lazy EventService eventService) {
        this.operationRepository = operationRepository;
        this.eventService = eventService;
    }

    public Operation getOne(int id) {
        Optional<Operation> optionalOperation = operationRepository.findById(id);
        if (optionalOperation.isPresent()) {
            return optionalOperation.get();
        }
        return null;
    }

    public int addOne(Map<String, String> payload) {
        String name = payload.get("name");
        String specialization = payload.get("specialization");

        if (name == null || specialization == null) {
            return -1;
        }

        Operation operation = new Operation();
        operation.setName(name);
        operation.setSpecialization(specialization);

        operationRepository.save(operation);

        return 0;
    }

    public int updateOne(Map<String, String> payload, int id) {
        Optional<Operation> optionalOperation = operationRepository.findById(id);

        if (!optionalOperation.isPresent()) {
            return -1;
        }

        Operation operation = optionalOperation.get();

        if (payload.get("name") != null) {
            operation.setName(payload.get("name"));
        }

        if (payload.get("specialization") != null) {
            operation.setSpecialization(payload.get("specialization"));
        }

        operationRepository.save(operation);
        return 0;
    }

    public int deleteOne(int id) {
        Optional<Operation> optionalOperation = operationRepository.findById(id);

        if (!optionalOperation.isPresent()) {
            return -1;
        }

        operationRepository.delete(optionalOperation.get());
        return 0;
    }

    public Map<String, Integer> getStatistics(){

        Map<String, Integer> statistics = new HashMap<>();

        ArrayList<Event> events = eventService.getAll();

        for(Event event: events){
            if(statistics.get(event.getOperation().getName()) == null){
                statistics.put(event.getOperation().getName(), 1);
            }
            else
            {
                statistics.put(event.getOperation().getName(), statistics.get(event.getOperation().getName()) + 1);
            }
        }
        return statistics;
    }
}

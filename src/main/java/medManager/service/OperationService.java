package medManager.service;

import medManager.dao.OperationRepository;
import medManager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class OperationService {

    private OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    public Operation getOne(int id){
        Optional<Operation> optionalOperation = operationRepository.findById(id);
        if(optionalOperation.isPresent()){
            return optionalOperation.get();
        }
        return null;
    }

    public int addOne(Map<String, String> payload){
        String name = payload.get("name");
        String specialization = payload.get("specialization");

        if(name == null || specialization == null){
            return -1;
        }

        Operation operation = new Operation();
        operation.setName(name);
        operation.setSpecialization(specialization);

        operationRepository.save(operation);

        return 0;
    }

    public int updateOne(Map<String, String> payload, int id){
        Optional<Operation> optionalOperation = operationRepository.findById(id);

        if(!optionalOperation.isPresent()){
            return -1;
        }

        Operation operation = optionalOperation.get();

        if(payload.get("name") != null){
            operation.setName(payload.get("name"));
        }

        if(payload.get("specialization") != null){
            operation.setSpecialization(payload.get("specialization"));
        }

        operationRepository.save(operation);
        return 0;
    }

    public int deleteOne(int id){
        Optional<Operation> optionalOperation = operationRepository.findById(id);

        if(!optionalOperation.isPresent()){
            return -1;
        }

        operationRepository.delete(optionalOperation.get());
        return 0;
    }
}

package medManager.service;

import medManager.dao.OperationRepository;
import medManager.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

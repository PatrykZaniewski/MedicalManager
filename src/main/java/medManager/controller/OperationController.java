package medManager.controller;

import medManager.model.Operation;
import medManager.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/operation")
public class OperationController {

    private OperationService operationService;

    public OperationController(OperationService operationService){
        this.operationService = operationService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Operation> getOne(@PathVariable("id") int id){
        Operation operation = operationService.getOne(id);
        if(operation == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(operation, HttpStatus.OK);
    }
}

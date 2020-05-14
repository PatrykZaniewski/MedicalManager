package medManager.controller;

import medManager.model.Operation;
import medManager.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping(value = "/")
    public ResponseEntity<Object> addOne(@RequestBody Map<String, String> payload){
        //TODO walidacja moze?
        if (payload == null){
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = operationService.addOne(payload);
        if(code == -1){
            return new ResponseEntity<>("Missing data in json", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Operation added", HttpStatus.OK);
    }
}

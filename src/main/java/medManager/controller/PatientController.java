package medManager.controller;

import medManager.model.Patient;
import medManager.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/patients")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") int id){
        Patient patient = patientService.getOne(id);
        if(patient == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> addPatient(@RequestBody Map<String, String> payload){
        //TODO walidacja moze?
        if (payload == null){
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = patientService.addOne(payload);
        if(code == -1){
            return new ResponseEntity<>("Missing data in json", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Patient added", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> modifyPatient(@PathVariable("id") int id, @RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = patientService.updateOne(payload, id);
        if (code == -1) {
            return new ResponseEntity<>("Patient not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Patient updated", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePatient(@PathVariable("id") int id) {
        int code = patientService.deleteOne(id);
        if (code == -1) {
            return new ResponseEntity<>("Patient not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Patient deleted", HttpStatus.OK);
    }
}

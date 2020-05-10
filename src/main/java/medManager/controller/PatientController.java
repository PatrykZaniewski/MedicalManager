package medManager.controller;

import medManager.model.Patient;
import medManager.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

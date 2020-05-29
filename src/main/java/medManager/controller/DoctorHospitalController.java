package medManager.controller;

import medManager.model.Doctor;
import medManager.service.DoctorHospitalService;
import medManager.service.doctorPOJO.DoctorHospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/doctorhospital")
public class DoctorHospitalController {

    private DoctorHospitalService doctorHospitalService;

    @Autowired
    public DoctorHospitalController(DoctorHospitalService doctorHospitalService){
        this.doctorHospitalService = doctorHospitalService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> addDoctorHospital(@RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = doctorHospitalService.addOne(payload);
        if (code == -1) {
            return new ResponseEntity<>("Missing data in json", HttpStatus.BAD_REQUEST);
        }
        else if (code == -2){
            return new ResponseEntity<>("JSON parse problem.", HttpStatus.BAD_REQUEST);
        }
        else if (code == -3){
            return new ResponseEntity<>("Hospital or doctor not found.", HttpStatus.BAD_REQUEST);
        }
        else if (code == -4){
            return new ResponseEntity<>("Combination of hospital and doctor was not found.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Added doctor to hospital added", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteDoctorHospital(@RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = doctorHospitalService.deleteOne(payload);
        if (code == -1) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Doctor deleted", HttpStatus.OK);
    }
}

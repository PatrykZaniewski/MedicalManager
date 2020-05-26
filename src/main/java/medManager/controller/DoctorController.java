package medManager.controller;

import medManager.model.Doctor;
import medManager.service.DoctorService;
import medManager.service.doctorPOJO.DoctorHospital;
import medManager.service.hospitalPOJO.HospitalDoctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorController {

    private DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") int id){
        Doctor doctor = doctorService.getOne(id);
        if(doctor == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping(value = "/hospitals")
    public ResponseEntity<ArrayList<DoctorHospital>> getHospitalsDoctors(){
        ArrayList<DoctorHospital> doctorHospitalArrayList = doctorService.getDoctorHospital();
        if(doctorHospitalArrayList != null) {
            return new ResponseEntity<>(doctorHospitalArrayList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> addDoctor(@RequestBody Map<String, String> payload){
        //TODO walidacja moze?
        if (payload == null){
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = doctorService.addOne(payload);
        if(code == -1){
            return new ResponseEntity<>("Missing data in json", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Doctor added", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> modifyDoctor(@PathVariable("id") int id, @RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = doctorService.updateOne(payload, id);
        if (code == -1) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Doctor updated", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteDoctor(@PathVariable("id") int id) {
        int code = doctorService.deleteOne(id);
        if (code == -1) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Doctor deleted", HttpStatus.OK);
    }


}

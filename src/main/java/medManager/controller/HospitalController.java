package medManager.controller;

import medManager.model.Hospital;
import medManager.service.HospitalService;
import medManager.service.hospitalPOJO.HospitalDoctor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/hospital")
public class HospitalController {

    private HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService){
        this.hospitalService = hospitalService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Hospital> getOne(@PathVariable("id") int id){
        Hospital hospital = hospitalService.getOne(id);
        if(hospital == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }

    @GetMapping(value = "/doctors")
    public ResponseEntity<ArrayList<HospitalDoctor>> getHospitalsDoctors(){
        ArrayList<HospitalDoctor> hospitalDoctorArrayList = hospitalService.getHospitalDoctor();
        if(hospitalDoctorArrayList != null) {
            return new ResponseEntity<>(hospitalDoctorArrayList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> addHospital(@RequestBody Map<String, String> payload){
        //TODO walidacja moze?
        if (payload == null){
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = hospitalService.addOne(payload);
        if(code == -1){
            return new ResponseEntity<>("Missing data in json", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Hospital added", HttpStatus.OK);
    }

}

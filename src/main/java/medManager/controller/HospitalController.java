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

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Hospital> getOne(@PathVariable("id") int id) {
        Hospital hospital = hospitalService.getOne(id);
        if (hospital == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }

    @GetMapping(value = "/doctors")
    public ResponseEntity<ArrayList<HospitalDoctor>> getHospitalsDoctors() {
        ArrayList<HospitalDoctor> hospitalDoctorArrayList = hospitalService.getHospitalDoctor();
        if (hospitalDoctorArrayList != null) {
            return new ResponseEntity<>(hospitalDoctorArrayList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> addHospital(@RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = hospitalService.addOne(payload);
        if (code == -1) {
            return new ResponseEntity<>("Missing data in json", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Hospital added", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> modifyHospital(@PathVariable("id") int id, @RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = hospitalService.updateOne(payload, id);
        if (code == -1) {
            return new ResponseEntity<>("Hospital not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Hospital updated", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable("id") int id) {
        int code = hospitalService.deleteOne(id);
        if (code == -1) {
            return new ResponseEntity<>("Hospital not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Hospital deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/filtered")
    public ResponseEntity<Object> getFilteredHospital(@RequestParam(required = false, name = "city") String city,
                                                      @RequestParam(required = false, name = "street") String street,
                                                      @RequestParam(required = false, name = "isPublic") String isPublic) {
        if (city == null) {
            city = "";
        }
        if (street == null) {
            street = "";
        }

        ArrayList<Hospital> hospitals;

        try {
            Boolean.parseBoolean(isPublic);
            hospitals = hospitalService.getFiltered(city, street, isPublic);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Wrong data in json. Parse problem.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @GetMapping(value = "/statistics/{id}")
    public ResponseEntity<Object> getStatistics(@PathVariable("id") int id){
        Map<String, Integer> statistics = hospitalService.getStatistics(id);
        if(statistics == null){
            return new ResponseEntity<>("Hospital not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

}

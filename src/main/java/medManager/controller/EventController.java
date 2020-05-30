package medManager.controller;

import medManager.model.Event;
import medManager.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable("id") int id) {
        Event event = eventService.getOne(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> addEvent(@RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = eventService.addOne(payload);
        if (code == 0) {
            return new ResponseEntity<>("Event added", HttpStatus.OK);
        } else if (code == -1) {
            return new ResponseEntity<>("Missing data in json", HttpStatus.BAD_REQUEST);
        } else if (code == -2) {
            return new ResponseEntity<>("Patient not found", HttpStatus.BAD_REQUEST);
        } else if (code == -3) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.BAD_REQUEST);
        } else if (code == -4) {
            return new ResponseEntity<>("Hospital not found", HttpStatus.BAD_REQUEST);
        } else if (code == -5) {
            return new ResponseEntity<>("Operation not found", HttpStatus.BAD_REQUEST);
        } else if (code == -6) {
            return new ResponseEntity<>("This doctor cannot perform this type of operation", HttpStatus.BAD_REQUEST);
        } else if (code == -7) {
            return new ResponseEntity<>("This doctor is not working in this hospital", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Incorrect patient/doctor/hospital/operation id", HttpStatus.BAD_REQUEST);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> modifyEvent(@PathVariable("id") int id, @RequestBody Map<String, String> payload) {
        if (payload == null) {
            return new ResponseEntity<>("Lack of payload", HttpStatus.BAD_REQUEST);
        }
        int code = eventService.updateOne(payload, id);
        if (code == -1) {
            return new ResponseEntity<>("Event not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Event updated", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable("id") int id) {
        int code = eventService.deleteOne(id);
        if (code == -1) {
            return new ResponseEntity<>("Event not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Event deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/filtered")
    public ResponseEntity<Object> getFilteredEvent(@RequestParam(required = false, name = "city") String city,
                                                   @RequestParam(required = false, name = "isPublic") String isPublic,
                                                   @RequestParam(required = false, name = "minCost") String minCost,
                                                   @RequestParam(required = false, name = "maxCost") String maxCost,
                                                   @RequestParam(required = false, name = "doctorName") String doctor,
                                                   @RequestParam(required = false, name = "hospitalName") String hospital,
                                                   @RequestParam(required = false, name = "patientName") String patient,
                                                   @RequestParam(required = false, name = "operationName") String operation) {
        if (city == null) {
            city = "";
        }
        if (minCost == null) {
            minCost = "0";
        }
        if (maxCost == null) {
            maxCost = "0";
        }
        if (doctor == null) {
            doctor = "";
        }
        if (hospital == null) {
            hospital = "";
        }
        if (patient == null) {
            patient = "";
        }
        if (operation == null) {
            operation = "";
        }

        ArrayList<Event> events;

        try {
            Boolean.parseBoolean(isPublic);
            events = eventService.getFiltered(city, isPublic, Integer.parseInt(minCost), Integer.parseInt(maxCost), doctor, hospital, patient, operation);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Wrong data in json. Parse problem.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}

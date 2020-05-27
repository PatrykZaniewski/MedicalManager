package medManager.service;

import medManager.dao.EventRepository;
import medManager.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private PatientService patientService;
    private HospitalService hospitalService;
    private OperationService operationService;
    private DoctorService doctorService;

    @Autowired
    public EventService(EventRepository eventRepository, PatientService patientService, HospitalService hospitalService,
                        OperationService operationService, DoctorService doctorService) {
        this.eventRepository = eventRepository;
        this.patientService = patientService;
        this.hospitalService = hospitalService;
        this.operationService = operationService;
        this.doctorService = doctorService;
    }

    public Event getOne(int id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        return null;
    }

    public ArrayList<Event> getAll() {
        return (ArrayList<Event>) eventRepository.findAll();
    }

    public int addOne(Map<String, String> payload) {
        String id_patientString = payload.get("id_patient");
        String id_doctorString = payload.get("id_doctor");
        String id_hospitalString = payload.get("id_hospital");
        String id_operationString = payload.get("id_operation");
        String eventDateString = payload.get("eventdate");
        String billableString = payload.get("billable");
        String costString = payload.get("cost");

        if (id_patientString == null || id_doctorString == null || id_hospitalString == null || id_operationString == null ||
                eventDateString == null || billableString == null || costString == null) {
            return -1;
        }

        int id_patient = Integer.parseInt(id_patientString);
        int id_doctor = Integer.parseInt(id_doctorString);
        int id_hospital = Integer.parseInt(id_hospitalString);
        int id_operation = Integer.parseInt(id_operationString);
        Date eventDate = Date.valueOf(eventDateString);
        boolean bilalble = Boolean.getBoolean(billableString);
        int cost = Integer.parseInt(costString);

        Patient patient = patientService.getOne(id_patient);
        Doctor doctor = doctorService.getOne(id_doctor);
        Hospital hospital = hospitalService.getOne(id_hospital);
        Operation operation = operationService.getOne(id_operation);


        if (patient == null) {
            return -2;
        }

        if (doctor == null) {
            return -3;
        }

        if (hospital == null) {
            return -4;
        }

        if (operation == null) {
            return -5;
        }

        Event event = new Event();
        event.setPatient(patient);
        event.setDoctor(doctor);
        event.setHospital(hospital);
        event.setOperation(operation);
        event.setEventDate(eventDate);
        event.setBillable(bilalble);
        event.setCost(cost);

        eventRepository.save(event);

        return 0;
    }

    public int updateOne(Map<String, String> payload, int id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (!optionalEvent.isPresent()) {
            return -1;
        }

        Event event = optionalEvent.get();

        if (payload.get("id_patient") != null) {
            Patient patient = patientService.getOne(Integer.parseInt(payload.get("id_patient")));
            if (patient == null) {
                return -2;
            }
            event.setPatient(patient);
        }

        if (payload.get("id_doctor") != null) {
            Doctor doctor = doctorService.getOne(Integer.parseInt(payload.get("id_doctor")));
            if (doctor == null) {
                return -3;
            }
            event.setDoctor(doctor);
        }

        if (payload.get("id_hospital") != null) {
            Hospital hospital = hospitalService.getOne(Integer.parseInt(payload.get("id_hospitalString")));
            if (hospital == null) {
                return -4;
            }
            event.setHospital(hospital);
        }

        if (payload.get("id_operation") != null) {
            Operation operation = operationService.getOne(Integer.parseInt(payload.get("id_operation")));
            if (operation == null) {
                return -5;
            }
            event.setOperation(operation);
        }

        if (payload.get("eventdate") != null) {
            event.setEventDate(Date.valueOf(payload.get("eventdate")));
        }

        if (payload.get("billable") != null) {
            event.setBillable(Boolean.getBoolean(payload.get("billable")));
        }

        if (payload.get("billable") != null) {
            event.setBillable(Boolean.getBoolean(payload.get("billable")));
        }

        if (payload.get("cost") != null) {
            event.setCost(Integer.parseInt(payload.get("cost")));
        }

        eventRepository.save(event);
        return 0;
    }

    public int deleteOne(int id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (!optionalEvent.isPresent()) {
            return -1;
        }

        eventRepository.delete(optionalEvent.get());
        return 0;
    }

    public ArrayList<Event> getFiltered(String city, String isPublic, int minCost, int maxCost, String doctorName, String hospitalName, String patientName) {
        List<List<Event>> events = new ArrayList<>();
        events.add(eventRepository.findAll());
        if (!city.equals("")) {
            List<Event> subList = new ArrayList<>();
            for (Event event : events.get(0)) {
                if (event.getHospital().getCity().equals(city)) {
                    subList.add(event);
                }
            }
            events.add(subList);
        }
        if (!isPublic.equals("")) {
            List<Event> subList = new ArrayList<>();
            for (Event event : events.get(0)) {
                if (event.getHospital().isPublic() == Boolean.parseBoolean(isPublic)) {
                    subList.add(event);
                }
            }
            events.add(subList);
        }
        if (minCost > 0) {
            List<Event> subList = new ArrayList<>();
            for (Event event : events.get(0)) {
                if (event.getCost() >= minCost) {
                    subList.add(event);
                }
            }
            events.add(subList);
        }
        if (maxCost > 0) {
            List<Event> subList = new ArrayList<>();
            for (Event event : events.get(0)) {
                if (event.getCost() <= maxCost) {
                    subList.add(event);
                }
            }
            events.add(subList);
        }
        if (!doctorName.equals("")) {
            List<Event> subList = new ArrayList<>();
            for (Event event : events.get(0)) {

                if ((event.getDoctor().getDegree() + " " + event.getDoctor().getFirstname() + " " + event.getDoctor().getLastname()).equals(doctorName)) {
                    subList.add(event);
                }
            }
            events.add(subList);
        }
        if (!patientName.equals("")) {
            List<Event> subList = new ArrayList<>();
            for (Event event : events.get(0)) {

                if ((event.getPatient().getFirstname() + " " + event.getPatient().getLastname()).equals(patientName)) {
                    subList.add(event);
                }
            }
            events.add(subList);
        }
        if (!hospitalName.equals("")) {
            List<Event> subList = new ArrayList<>();
            for (Event event : events.get(0)) {

                if ((event.getHospital().getName()).equals(hospitalName)) {
                    subList.add(event);
                }
            }
            events.add(subList);
        }
        for (int i = 1; i < events.size(); i++) {
            events.get(0).retainAll(events.get(i));
        }
        return (ArrayList<Event>) events.get(0);
    }
}

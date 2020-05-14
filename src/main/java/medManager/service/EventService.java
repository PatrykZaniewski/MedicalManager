package medManager.service;

import medManager.dao.EventRepository;
import medManager.model.Doctor;
import medManager.model.Event;
import medManager.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                        OperationService operationService, DoctorService doctorService){
        this.eventRepository = eventRepository;
        this.patientService = patientService;
        this.hospitalService = hospitalService;
        this.operationService = operationService;
        this.doctorService = doctorService;
    }

    public Event getOne(int id){
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isPresent()){
            return optionalEvent.get();
        }
        return null;
    }

    public int addOne(Map<String, String> payload){
        String id_patientString = payload.get("id_patient");
        String id_doctorString = payload.get("id_doctor");
        String id_hospitalString = payload.get("id_hospital");
        String id_operationString = payload.get("id_operation");
        String eventDateString = payload.get("eventdate");
        String billableString = payload.get("billable");
        String costString = payload.get("cost");

        if(id_patientString == null || id_doctorString == null || id_hospitalString == null || id_operationString == null ||
                eventDateString == null || billableString == null || costString == null){
            return -1;
        }

        int id_patient = Integer.parseInt(id_patientString);
        int

        if(patientService.getOne())

        Event event = new Event();
        event.set(firstname);
        event.setLastname(lastname);
        event.setSpecialization(specialization);
        event.setDegree(degree);
        event.setSpecialization(specialization);
        event.setDegree(degree);

        doctorRepository.save(doctor);

        return 0;
    }
}

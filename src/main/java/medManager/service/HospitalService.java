package medManager.service;

import medManager.dao.HospitalRepository;
import medManager.model.Doctor;
import medManager.model.Event;
import medManager.model.Hospital;
import medManager.model.Patient;
import medManager.service.hospitalPOJO.HospitalDoctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HospitalService {

    private HospitalRepository hospitalRepository;
    private EventService eventService;

    @Autowired
    public HospitalService(@Lazy HospitalRepository hospitalRepository, EventService eventService) {
        this.hospitalRepository = hospitalRepository;
        this.eventService = eventService;
    }

    public Hospital getOne(int id) {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);
        if (optionalHospital.isPresent()) {
            return optionalHospital.get();
        }
        return null;
    }

    public ArrayList<Hospital> getAll() {
        return (ArrayList<Hospital>) hospitalRepository.findAll();
    }

    public ArrayList<HospitalDoctor> getHospitalDoctor() {
        ArrayList<Hospital> hospitalArrayList = getAll();
        ArrayList<HospitalDoctor> hospitalDoctorArrayList = new ArrayList<>();
        for (Hospital hospital : hospitalArrayList) {
            HospitalDoctor hospitalDoctor = new HospitalDoctor(hospital.getName(), hospital.getCity() + ", " + hospital.getStreet(), hospital.isPublic(), hospital.getDoctors());
            hospitalDoctorArrayList.add(hospitalDoctor);
        }
        return hospitalDoctorArrayList;
    }

    public int addOne(Map<String, String> payload) {
        String name = payload.get("name");
        String street = payload.get("street");
        String city = payload.get("city");
        String isPublicString = payload.get("isPublic");

        if (name == null || street == null || city == null || isPublicString == null) {
            return -1;
        }

        boolean isPublic = Boolean.getBoolean(isPublicString);

        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setStreet(street);
        hospital.setCity(city);
        hospital.setPublic(isPublic);

        hospitalRepository.save(hospital);

        return 0;
    }

    public int updateOne(Map<String, String> payload, int id) {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);

        if (!optionalHospital.isPresent()) {
            return -1;
        }

        Hospital hospital = optionalHospital.get();

        if (payload.get("name") != null) {
            hospital.setName(payload.get("name"));
        }

        if (payload.get("street") != null) {
            hospital.setStreet(payload.get("street"));
        }

        if (payload.get("city") != null) {
            hospital.setCity(payload.get("city"));
        }

        if (payload.get("isPublic") != null) {
            hospital.setPublic(Boolean.getBoolean(payload.get("isPublic")));
        }

        hospitalRepository.save(hospital);
        return 0;
    }

    public int deleteOne(int id) {
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);

        if (!optionalHospital.isPresent()) {
            return -1;
        }

        hospitalRepository.delete(optionalHospital.get());
        return 0;
    }

    public ArrayList<Hospital> getFiltered(String city, String street, String isPublic) {
        List<List<Hospital>> hospitals = new ArrayList<>();
        hospitals.add(hospitalRepository.findAll());
        if (!city.equals("")) {
            List<Hospital> subList = new ArrayList<>();
            for (Hospital hospital : hospitals.get(0)) {
                if (hospital.getCity().equals(city)) {
                    subList.add(hospital);
                }
            }
            hospitals.add(subList);
        }
        if (!street.equals("")) {
            List<Hospital> subList = new ArrayList<>();
            for (Hospital hospital : hospitals.get(0)) {
                if (hospital.getStreet().equals(street)) {
                    subList.add(hospital);
                }
            }
            hospitals.add(subList);
        }
        if (!isPublic.equals("")) {
            List<Hospital> subList = new ArrayList<>();
            for (Hospital hospital : hospitals.get(0)) {
                if (hospital.isPublic() == Boolean.parseBoolean(isPublic)) {
                    subList.add(hospital);
                }
            }
            hospitals.add(subList);
        }
        for (int i = 1; i < hospitals.size(); i++) {
            hospitals.get(0).retainAll(hospitals.get(i));
        }
        return (ArrayList<Hospital>) hospitals.get(0);
    }

    public Map<String, Integer> getStatistics(int id){

        Hospital hospital = this.getOne(id);

        if(hospital == null){
            return null;
        }

        Map<String, Integer> statistics = new HashMap<>();

        Set<Patient> patients = new HashSet<>();
        Set<Doctor> doctors = new HashSet<>();

        ArrayList<Event> hospitalEvents = eventService.getFiltered("", "", 0, 0, "", "", "", "");

        for(Event event: hospitalEvents){
            patients.add(event.getPatient());
            doctors.add(event.getDoctor());
        }
        statistics.put("Events", hospitalEvents.size());
        statistics.put("Patients", patients.size());
        statistics.put("Doctors", doctors.size());

        return statistics;
    }
}

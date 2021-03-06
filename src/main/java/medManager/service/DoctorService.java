package medManager.service;

import medManager.dao.DoctorRepository;
import medManager.model.Doctor;
import medManager.model.Event;
import medManager.model.Hospital;
import medManager.model.Patient;
import medManager.service.doctorPOJO.DoctorHospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;
    private EventService eventService;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, EventService eventService) {
        this.doctorRepository = doctorRepository;
        this.eventService = eventService;
    }

    public Doctor getOne(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            return optionalDoctor.get();
        }
        return null;
    }

    public ArrayList<Doctor> getAll() {
        return (ArrayList<Doctor>) doctorRepository.findAll();
    }

    public ArrayList<DoctorHospital> getDoctorHospital() {
        ArrayList<Doctor> doctorArrayList = getAll();
        ArrayList<DoctorHospital> doctorHospitalArrayList = new ArrayList<>();
        for (Doctor doctor : doctorArrayList) {
            DoctorHospital doctorHospital = new DoctorHospital(doctor.getFirstname() + " " + doctor.getLastname(), doctor.getDegree(), doctor.getSpecialization(), doctor.getHospitals());
            doctorHospitalArrayList.add(doctorHospital);
        }
        return doctorHospitalArrayList;
    }

    public int addOne(Map<String, String> payload) {
        String firstname = payload.get("firstname");
        String lastname = payload.get("lastname");
        String specialization = payload.get("specialization");
        String degree = payload.get("degree");

        if (firstname == null || lastname == null || specialization == null || degree == null) {
            return -1;
        }

        Doctor doctor = new Doctor();
        doctor.setFirstname(firstname);
        doctor.setLastname(lastname);
        doctor.setSpecialization(specialization);
        doctor.setDegree(degree);

        doctorRepository.save(doctor);

        return 0;
    }

    public int updateOne(Map<String, String> payload, int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (!optionalDoctor.isPresent()) {
            return -1;
        }

        Doctor doctor = optionalDoctor.get();

        if (payload.get("firstname") != null) {
            doctor.setFirstname(payload.get("firstname"));
        }

        if (payload.get("lastname") != null) {
            doctor.setLastname(payload.get("lastname"));
        }

        if (payload.get("specialization") != null) {
            doctor.setSpecialization(payload.get("specialization"));
        }

        if (payload.get("degree") != null) {
            doctor.setDegree(payload.get("degree"));
        }

        doctorRepository.save(doctor);
        return 0;
    }

    public int deleteOne(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

        if (!optionalDoctor.isPresent()) {
            return -1;
        }

        doctorRepository.delete(optionalDoctor.get());
        return 0;
    }

    public ArrayList<Doctor> getFiltered(String firstname, String lastname, String specialization, String degree) {
        List<List<Doctor>> doctors = new ArrayList<>();
        doctors.add(doctorRepository.findAll());
        if (!firstname.equals("")) {
            List<Doctor> subList = new ArrayList<>();
            for (Doctor doctor : doctors.get(0)) {
                if (doctor.getFirstname().equals(firstname)) {
                    subList.add(doctor);
                }
            }
            doctors.add(subList);
        }

        if (!lastname.equals("")) {
            List<Doctor> subList = new ArrayList<>();
            for (Doctor doctor : doctors.get(0)) {
                if (doctor.getLastname().equals(lastname)) {
                    subList.add(doctor);
                }
            }
            doctors.add(subList);
        }

        if (!specialization.equals("")) {
            List<Doctor> subList = new ArrayList<>();
            for (Doctor doctor : doctors.get(0)) {
                if (doctor.getSpecialization().equals(specialization)) {
                    subList.add(doctor);
                }
            }
            doctors.add(subList);
        }

        if (!degree.equals("")) {
            List<Doctor> subList = new ArrayList<>();
            for (Doctor doctor : doctors.get(0)) {
                if (doctor.getDegree().equals(degree)) {
                    subList.add(doctor);
                }
            }
            doctors.add(subList);
        }

        for (int i = 1; i < doctors.size(); i++) {
            doctors.get(0).retainAll(doctors.get(i));
        }
        return (ArrayList<Doctor>) doctors.get(0);
    }

    public Map<String, Integer> getStatistics(int id){

        Doctor doctor = this.getOne(id);

        if(doctor == null){
            return null;
        }

        Map<String, Integer> statistics = new HashMap<>();

        Set<Patient> patients = new HashSet<>();
        Set<Hospital> hospitalsOld = new HashSet<>();

        ArrayList<Event> doctorEvents = eventService.getFiltered("", "", 0, 0, doctor.getDegree() + " " + doctor.getFirstname() + " " + doctor.getLastname(), "", "", "");

        for(Event event: doctorEvents){
            patients.add(event.getPatient());
            hospitalsOld.add(event.getHospital());
        }
        statistics.put("Events", doctorEvents.size());
        statistics.put("Patients", patients.size());
        statistics.put("Hospitals now", doctor.getHospitals().size());
        statistics.put("Hospitals old", hospitalsOld.size());

        return statistics;
    }

}

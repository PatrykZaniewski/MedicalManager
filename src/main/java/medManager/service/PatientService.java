package medManager.service;

import medManager.dao.PatientRepository;
import medManager.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getOne(int id) {
        Optional<Patient> optionalAccount = patientRepository.findById(id);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        }
        return null;
    }

    public int addOne(Map<String, String> payload) {
        String firstname = payload.get("firstname");
        String lastname = payload.get("lastname");
        String pesel = payload.get("pesel");
        String birthdateString = payload.get("birthdate");
        String residence = payload.get("residence");
        String phone = payload.get("phone");

        if (firstname == null || lastname == null || pesel == null || birthdateString == null || residence == null || phone == null) {
            return -1;
        }

        Date birthdate = Date.valueOf(birthdateString);

        Patient patient = new Patient();
        patient.setFirstname(firstname);
        patient.setLastname(lastname);
        patient.setPesel(pesel);
        patient.setBirthdate(birthdate);
        patient.setResidence(residence);
        patient.setPhone(phone);

        patientRepository.save(patient);

        return 0;
    }

    public int updateOne(Map<String, String> payload, int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (!optionalPatient.isPresent()) {
            return -1;
        }

        Patient patient = optionalPatient.get();

        if (payload.get("firstname") != null) {
            patient.setFirstname(payload.get("firstname"));
        }

        if (payload.get("lastname") != null) {
            patient.setLastname(payload.get("lastname"));
        }

        if (payload.get("pesel") != null) {
            patient.setPesel(payload.get("pesel"));
        }

        if (payload.get("birthdate") != null) {
            patient.setBirthdate(Date.valueOf(payload.get("birthdate")));
        }

        if (payload.get("residence") != null) {
            patient.setResidence(payload.get("residence"));
        }

        if (payload.get("phone") != null) {
            patient.setPhone(payload.get("phone"));
        }

        patientRepository.save(patient);
        return 0;
    }

    public int deleteOne(int id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (!optionalPatient.isPresent()) {
            return -1;
        }

        patientRepository.delete(optionalPatient.get());
        return 0;
    }

    public ArrayList<Patient> getFiltered(String firstname, String lastname, String pesel, String residence) {
        List<List<Patient>> patients = new ArrayList<>();
        patients.add(patientRepository.findAll());
        if (!firstname.equals("")) {
            List<Patient> subList = new ArrayList<>();
            for (Patient patient : patients.get(0)) {
                if (patient.getFirstname().equals(firstname)) {
                    subList.add(patient);
                }
            }
            patients.add(subList);
        }
        if (!lastname.equals("")) {
            List<Patient> subList = new ArrayList<>();
            for (Patient patient : patients.get(0)) {
                if (patient.getLastname().equals(lastname)) {
                    subList.add(patient);
                }
            }
            patients.add(subList);
        }
        if (!residence.equals("")) {
            List<Patient> subList = new ArrayList<>();
            for (Patient patient : patients.get(0)) {
                if (patient.getResidence().equals(residence)) {
                    subList.add(patient);
                }
            }
            patients.add(subList);
        }
        if (!pesel.equals("")) {
            List<Patient> subList = new ArrayList<>();
            for (Patient patient : patients.get(0)) {
                if (patient.getPesel().equals(pesel)) {
                    subList.add(patient);
                }
            }
            patients.add(subList);
        }
        for (int i = 1; i < patients.size(); i++) {
            patients.get(0).retainAll(patients.get(i));
        }
        return (ArrayList<Patient>) patients.get(0);
    }

}

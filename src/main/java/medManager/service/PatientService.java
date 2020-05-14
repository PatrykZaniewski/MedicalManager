package medManager.service;

import medManager.dao.PatientRepository;
import medManager.model.Doctor;
import medManager.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public Patient getOne(int id){
        Optional<Patient> optionalAccount = patientRepository.findById(id);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        return null;
    }

    public int addOne(Map<String, String> payload){
        String firstname = payload.get("firstname");
        String lastname = payload.get("lastname");
        String pesel = payload.get("specialization");
        String birthdateString = payload.get("firstname");
        String residence = payload.get("lastname");
        String phone = payload.get("specialization");

        if(firstname == null || lastname == null || pesel == null || birthdateString == null || residence == null || phone == null){
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

}

package medManager.service;

import medManager.dao.PatientRepository;
import medManager.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

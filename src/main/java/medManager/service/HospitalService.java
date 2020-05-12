package medManager.service;

import medManager.dao.HospitalRepository;
import medManager.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class HospitalService {

    private HospitalRepository hospitalRepository;

    @Autowired
    public HospitalService(HospitalRepository hospitalRepository){
        this.hospitalRepository = hospitalRepository;
    }

    public Hospital getOne(int id){
        Optional<Hospital> optionalHospital = hospitalRepository.findById(id);
        if(optionalHospital.isPresent()){
            return optionalHospital.get();
        }
        return null;
    }

    public Hospital getAll(){
        ArrayList<Hospital> hospitalList = (ArrayList<Hospital>) hospitalRepository.findAll();
        return null;
    }
}

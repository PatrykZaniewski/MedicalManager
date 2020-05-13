package medManager.service;

import medManager.dao.HospitalRepository;
import medManager.model.Hospital;
import medManager.service.hospitalPOJO.HospitalDoctor;
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

    public ArrayList<Hospital> getAll(){
        return (ArrayList<Hospital>) hospitalRepository.findAll();
    }

    public ArrayList<HospitalDoctor> getHospitalDoctor(){
        ArrayList<Hospital> hospitalArrayList = getAll();
        ArrayList<HospitalDoctor> hospitalDoctorArrayList = new ArrayList<>();
        for(Hospital hospital: hospitalArrayList){
            HospitalDoctor hospitalDoctor = new HospitalDoctor(hospital.getName(), hospital.getCity() + ", " + hospital.getStreet(), hospital.isPublic(), hospital.getDoctors());
            hospitalDoctorArrayList.add(hospitalDoctor);
        }
        return hospitalDoctorArrayList;
    }
}

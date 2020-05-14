package medManager.service;

import medManager.dao.DoctorRepository;
import medManager.model.Doctor;
import medManager.model.Hospital;
import medManager.service.doctorPOJO.DoctorHospital;
import medManager.service.hospitalPOJO.HospitalDoctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public Doctor getOne(int id){
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if(optionalDoctor.isPresent()){
            return optionalDoctor.get();
        }
        return null;
    }

    public ArrayList<Doctor> getAll(){
        return (ArrayList<Doctor>) doctorRepository.findAll();
    }

    public ArrayList<DoctorHospital> getDoctorHospital(){
        ArrayList<Doctor> doctorArrayList = getAll();
        ArrayList<DoctorHospital> doctorHospitalArrayList = new ArrayList<>();
        for(Doctor doctor: doctorArrayList){
            DoctorHospital doctorHospital = new DoctorHospital(doctor.getFirstname() + " " + doctor.getLastname(), doctor.getDegree(), doctor.getSpecialization(), doctor.getHospitals());
            doctorHospitalArrayList.add(doctorHospital);
        }
        return doctorHospitalArrayList;
    }

    public int addOne(Map<String, String> payload){
        String firstname = payload.get("firstname");
        String lastname = payload.get("lastname");
        String specialization = payload.get("specialization");
        String degree = payload.get("degree");

        if(firstname == null || lastname == null || specialization == null || degree == null){
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
}

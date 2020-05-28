package medManager.service;

import medManager.dao.DoctorHospitalRepository;
import medManager.model.Doctor;
import medManager.model.DoctorHospital;
import medManager.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorHospitalService {

    private DoctorHospitalRepository doctorHospitalRepository;
    private DoctorService doctorService;
    private HospitalService hospitalService;

    @Autowired
    public DoctorHospitalService(DoctorHospitalRepository doctorHospitalRepository, DoctorService doctorService, HospitalService hospitalService) {
        this.doctorHospitalRepository = doctorHospitalRepository;
        this.doctorService = doctorService;
        this.hospitalService = hospitalService;
    }

    public int addOne(Map<String, String> payload) {
        if(payload.get("id_doctor") == null || payload.get("id_hospital") == null){
            return -1;
        }
        try {
            Integer.parseInt(payload.get("id_doctor"));
            Integer.parseInt(payload.get("id_hostpial"));
        }
        catch (NumberFormatException e){
            return -2;
        }
        Doctor doctor = doctorService.getOne(Integer.parseInt(payload.get("id_doctor")));
        Hospital hospital = hospitalService.getOne(Integer.parseInt(payload.get("id_hostpial")));
        if (doctor == null || hospital == null) {
            return -3;
        }
        if (getDoctorHospital(Integer.parseInt(payload.get("id_doctor")), Integer.parseInt(payload.get("id_hostpial"))) == -1) {
            DoctorHospital doctorHospital = new DoctorHospital();
            doctorHospital.setDoctor(doctor);
            doctorHospital.setHospital(hospital);
            return 0;
        }
        return -4;
    }

    public ArrayList<DoctorHospital> getAll() {
        return (ArrayList<DoctorHospital>) doctorHospitalRepository.findAll();
    }

    public int deleteOne(Map<String, String> payload) {
        List<DoctorHospital> optionalDoctorHospital = doctorHospitalRepository.findAll();
        if(payload.get("id_doctor") == null || payload.get("id_hospital") == null){
            return -1;
        }
        try {
            Integer.parseInt(payload.get("id_doctor"));
            Integer.parseInt(payload.get("id_hospital"));
        }
        catch (NumberFormatException e){
            return -2;
        }
        for(DoctorHospital doctorHospital: optionalDoctorHospital){
            if(doctorHospital.getDoctor().getId() == Integer.parseInt(payload.get("id_doctor")) && doctorHospital.getHospital().getId() == Integer.parseInt(payload.get("id_hospital"))){
                doctorHospitalRepository.delete(doctorHospital);
                return 0;
            }
        }
        return -3;
    }

    public int getDoctorHospital(int id_doctor, int id_hospital) {
        Doctor doctor = doctorService.getOne(id_doctor);
        Hospital hospital = hospitalService.getOne(id_hospital);
        if (doctor == null || hospital == null) {
            return -1;
        }
        for (DoctorHospital doctorHospital : this.getAll()) {
            if (doctorHospital.getDoctor().getId() == id_doctor && doctorHospital.getHospital().getId() == id_hospital) {
                return 0;
            }
        }
        return -1;
    }
}

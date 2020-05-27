package medManager.service;

import medManager.dao.DoctorHospitalRepository;
import medManager.model.Doctor;
import medManager.model.DoctorHospital;
import medManager.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int addOne(Doctor doctor, Hospital hospital) {
        return 0;
    }

    public int getDoctorHospital(int id_doctor, int id_hospital) {
        //TODO dokonczyc
        Doctor doctor = doctorService.getOne(id_doctor);
        Hospital hospital = hospitalService.getOne(id_hospital);
        if (doctor == null || hospital == null) {
            return -1;
        }
        Optional<DoctorHospital> doctorHospital = doctorHospitalRepository.getDoctorHospital(id_doctor, id_hospital);
        return 0;
    }
}

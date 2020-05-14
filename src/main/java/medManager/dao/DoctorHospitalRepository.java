package medManager.dao;

import medManager.model.DoctorHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorHospitalRepository extends JpaRepository<DoctorHospital, Integer> {

    @Query("select dh from doctor_hospital dh where dh.id_doctor like ?1 and dh.id_hospital like ?2")
    Optional<DoctorHospital> getDoctorHospital(int id_doctor, int id_hospital);
}

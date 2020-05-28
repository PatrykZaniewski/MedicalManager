package medManager.dao;

import medManager.model.DoctorHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorHospitalRepository extends JpaRepository<DoctorHospital, Integer> {
}

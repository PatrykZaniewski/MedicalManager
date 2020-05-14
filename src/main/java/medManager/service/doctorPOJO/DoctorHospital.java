package medManager.service.doctorPOJO;

import medManager.model.Hospital;

import java.util.HashSet;
import java.util.Set;

public class DoctorHospital {
    private String name;
    private String degree;
    private String specialization;
    Set<Hospital> hospitals = new HashSet<>();

    public DoctorHospital(String name, String degree, String specialization, Set<Hospital> hospitals){
        this.name = name;
        this.degree = degree;
        this.specialization = specialization;
        this.hospitals = hospitals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Set<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(Set<Hospital> hospitals) {
        this.hospitals = hospitals;
    }
}

package medManager.model;

import javax.persistence.*;

@Entity
@Table(name = "doctor_hospital")
public class DoctorHospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany
    @JoinColumn(name = "id_doctor", referencedColumnName = "id")
    private Doctor doctor;
    @OneToMany
    @JoinColumn(name = "id_hospital", referencedColumnName = "id")
    private Hospital hospital;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}

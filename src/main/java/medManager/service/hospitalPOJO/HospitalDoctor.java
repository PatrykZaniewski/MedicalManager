package medManager.service.hospitalPOJO;

import medManager.model.Doctor;

import java.util.HashSet;
import java.util.Set;

public class HospitalDoctor {
    private String hospitalName;
    private String hospitalAddress;
    private boolean isPublic;
    private Set<Doctor> doctors;

    public HospitalDoctor(String hospitalName, String hospitalAddress, boolean isPublic, Set<Doctor> doctors){
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.isPublic = isPublic;
        this.doctors = doctors;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
}

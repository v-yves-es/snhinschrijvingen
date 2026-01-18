package be.achieveit.snhinschrijvingen.dto;

public class DoctorForm {
    
    private String doctorName;
    private String doctorPhone;
    
    public DoctorForm() {
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
    public String getDoctorPhone() {
        return doctorPhone;
    }
    
    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }
}

package be.achieveit.snhinschrijvingen.dto;

/**
 * Data Transfer Object for a single relation (parent/guardian)
 */
public class RelationDTO {
    
    private String relationType;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private AddressDTO address;
    
    public RelationDTO() {
        this.address = new AddressDTO();
    }
    
    // Getters and Setters
    
    public String getRelationType() {
        return relationType;
    }
    
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public AddressDTO getAddress() {
        return address;
    }
    
    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}

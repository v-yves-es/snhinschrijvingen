package be.achieveit.snhinschrijvingen.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "relations")
public class Relation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;
    
    @Column(name = "relation_type", nullable = false)
    private String relationType;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    // Address embedded with specific column names
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "street")),
        @AttributeOverride(name = "houseNumber", column = @Column(name = "house_number")),
        @AttributeOverride(name = "box", column = @Column(name = "box")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code")),
        @AttributeOverride(name = "city", column = @Column(name = "city")),
        @AttributeOverride(name = "country", column = @Column(name = "country"))
    })
    private Address address;
    
    @Column(name = "relation_order")
    private Integer relationOrder; // 1 or 2 to track which relation
    
    public Relation() {
    }
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public Registration getRegistration() {
        return registration;
    }
    
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
    
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
    
    public Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Integer getRelationOrder() {
        return relationOrder;
    }
    
    public void setRelationOrder(Integer relationOrder) {
        this.relationOrder = relationOrder;
    }
}

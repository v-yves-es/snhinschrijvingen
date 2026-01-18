package be.achieveit.snhinschrijvingen.dto;

public class LaptopForm {
    
    private String laptopBrand; // Laptop merk (optioneel veld)
    
    public LaptopForm() {
    }
    
    // Getters and Setters
    
    public String getLaptopBrand() {
        return laptopBrand;
    }
    
    public void setLaptopBrand(String laptopBrand) {
        this.laptopBrand = laptopBrand;
    }
}

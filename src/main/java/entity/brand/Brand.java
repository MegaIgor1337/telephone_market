package entity.brand;

import java.util.Objects;

public class Brand {
    private Long id;
    private String brand;

    public Brand(Long id, String brand) {
        this.id = id;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Brand{" +
               "id=" + id +
               ", brand='" + brand + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand1 = (Brand) o;
        return Objects.equals(id, brand1.id) && Objects.equals(brand, brand1.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Brand() {

    }

}

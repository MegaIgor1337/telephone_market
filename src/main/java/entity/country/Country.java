package entity.country;

import java.util.Objects;

public class Country {
    private Long id;
    private String country;

    public Country(Long id, String country) {
        this.id = id;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Country{" +
               "id=" + id +
               ", country='" + country + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country1 = (Country) o;
        return Objects.equals(id, country1.id)
               && Objects.equals(country, country1.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Country() {

    }
}

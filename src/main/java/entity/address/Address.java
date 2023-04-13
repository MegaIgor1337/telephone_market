package entity.address;

import java.util.Objects;

public class Address {
    private Long id;
    private String country;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String street;
    private String house;
    private String flat;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Address(Long id, String country, String city, String street, String house, String flat) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public Address() {

    }

    @Override
    public String toString() {
        return "Address{" +
               "id=" + id +
               ", country='" + country + '\'' +
               ", city='" + city + '\'' +
               ", street='" + street + '\'' +
               ", house='" + house + '\'' +
               ", flat='" + flat + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id)
               && Objects.equals(country, address.country)
               && Objects.equals(city, address.city) && Objects.equals(street, address.street)
               && Objects.equals(house, address.house) && Objects.equals(flat, address.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, house, flat);
    }
}

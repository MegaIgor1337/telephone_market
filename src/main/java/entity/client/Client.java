package entity.client;

import entity.address.Address;

import java.util.Objects;

public class Client {
    private Long id;
    private String name;
    private String password;
    private String passportNo;
    private Address address;
    private String email;

    public Client(Long id, String name, String password, String passportNo, Address address, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.passportNo = passportNo;
        this.address = address;
        this.email = email;
    }

    public Client() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id)
               && Objects.equals(name, client.name)
               && Objects.equals(password, client.password)
               && Objects.equals(passportNo, client.passportNo)
               && Objects.equals(address, client.address) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, passportNo, address, email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setMame(String mame) {
        this.name = mame;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" +
               "id=" + id +
               ", mame='" + name + '\'' +
               ", password='" + password + '\'' +
               ", passportNo='" + passportNo + '\'' +
               ", address=" + address +
               ", email='" + email + '\'' +
               '}';
    }
}

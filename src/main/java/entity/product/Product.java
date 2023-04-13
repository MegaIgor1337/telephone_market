package entity.product;

import entity.brand.Brand;
import entity.color.Color;
import entity.country.Country;
import entity.model.Model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long id;
    private Brand brand;
    private Model model;
    private Color color;
    private Country country;
    private int storageCount;
    private BigDecimal cost;

    public Product(Long id, Brand brand, Model model,
                   Color color, Country country, int storageCount, BigDecimal cost) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.country = country;
        this.storageCount = storageCount;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", brand=" + brand +
               ", model=" + model +
               ", color=" + color +
               ", country=" + country +
               ", storageCount=" + storageCount +
               ", cost=" + cost +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return storageCount == product.storageCount && Objects.equals(id, product.id)
               && Objects.equals(brand, product.brand) && Objects.equals(model, product.model)
               && Objects.equals(color, product.color) && Objects.equals(country, product.country)
               && Objects.equals(cost, product.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, color, country, storageCount, cost);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(int storageCount) {
        this.storageCount = storageCount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Product() {

    }
}

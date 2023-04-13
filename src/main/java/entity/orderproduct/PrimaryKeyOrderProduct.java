package entity.orderproduct;

import entity.order.Order;
import entity.product.Product;

import java.util.Objects;

public class PrimaryKeyOrderProduct {
    private Order order;
    private Product product;

    public PrimaryKeyOrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    @Override
    public String toString() {
        return "PrimaryKeyOrderProduct{" +
               "order=" + order +
               ", product=" + product +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimaryKeyOrderProduct that = (PrimaryKeyOrderProduct) o;
        return Objects.equals(order, that.order)
               && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PrimaryKeyOrderProduct() {

    }
}

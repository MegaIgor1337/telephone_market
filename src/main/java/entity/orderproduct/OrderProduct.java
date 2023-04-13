package entity.orderproduct;

import java.util.Objects;

public class OrderProduct {
    private PrimaryKeyOrderProduct primaryKeyOrderProduct;
    private int clientCount;


    public OrderProduct(PrimaryKeyOrderProduct primaryKeyOrderProduct, int clientCount) {
        this.primaryKeyOrderProduct = primaryKeyOrderProduct;
        this.clientCount = clientCount;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
               "primaryKeyOrderProduct=" + primaryKeyOrderProduct +
               ", clientCount=" + clientCount +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return clientCount == that.clientCount
               && Objects.equals(primaryKeyOrderProduct, that.primaryKeyOrderProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKeyOrderProduct, clientCount);
    }

    public PrimaryKeyOrderProduct getPrimaryKeyOrderProduct() {
        return primaryKeyOrderProduct;
    }

    public void setPrimaryKeyOrderProduct(PrimaryKeyOrderProduct primaryKeyOrderProduct) {
        this.primaryKeyOrderProduct = primaryKeyOrderProduct;
    }

    public int getClientCount() {
        return clientCount;
    }

    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }

    public OrderProduct() {

    }
}

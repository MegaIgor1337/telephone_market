package entity.order;

import entity.client.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private Long id;
    private Client client;
    private BigDecimal cost;
    private LocalDateTime date;
    private Boolean delivered;
    private LocalDateTime dateOfDelivery;

    public Order(Long id, Client client, BigDecimal cost,
                 LocalDateTime date, Boolean delivery, LocalDateTime dateOfDelivery) {
        this.id = id;
        this.client = client;
        this.cost = cost;
        this.date = date;
        this.delivered = delivery;
        this.dateOfDelivery = dateOfDelivery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public LocalDateTime getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(LocalDateTime dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id)
               && Objects.equals(client, order.client)
               && Objects.equals(cost, order.cost)
               && Objects.equals(date, order.date)
               && delivered == order.delivered
               && Objects.equals(dateOfDelivery, order.dateOfDelivery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, cost, date, delivered, dateOfDelivery);
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", clientId=" + client +
               ", cost=" + cost +
               ", date=" + date +
               ", delivery=" + delivered +
               ", dateOfDelivery=" + dateOfDelivery +
               '}';
    }

    public Order() {

    }

}

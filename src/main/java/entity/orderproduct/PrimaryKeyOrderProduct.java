package entity.orderproduct;

import entity.order.Order;
import entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrimaryKeyOrderProduct {
    private Order order;
    private Product product;

}

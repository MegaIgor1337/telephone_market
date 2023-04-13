package filter;

import entity.order.Order;
import entity.product.Product;

public record OrderProductFilter(int limit,
                                 int offset,
                                 Order order,
                                 Product product,
                                 int clientCount) {

}

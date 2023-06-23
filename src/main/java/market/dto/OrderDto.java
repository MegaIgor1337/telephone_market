package market.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private UserDto user;
    private BigDecimal cost;
    private LocalDateTime dateOfDelivery;
    private LocalDateTime date;
    private OrderStatus status;
    private List<OrderProductDto> products;
}

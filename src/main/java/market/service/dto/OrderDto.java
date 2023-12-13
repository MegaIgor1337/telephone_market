package market.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate dateOfDelivery;
    private LocalDateTime date;
    private OrderStatus status;
    private List<OrderProductDto> products;
    private AddressDto address;
}

package market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.entity.OrderProduct;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDtoWithPage {
    private Long id;
    private UserDto user;
    private BigDecimal cost;
    private LocalDateTime dateOfDelivery;
    private LocalDateTime date;
    private Page<OrderProductDto> products;
}
package market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.enums.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFilterDto {
    private String username;
    private Long number;
    private OrderStatus status;
}

package market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.enums.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModerateOrderDto {
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfDelivery;
    private OrderStatus status;
    private Long deliveryAddress;
}

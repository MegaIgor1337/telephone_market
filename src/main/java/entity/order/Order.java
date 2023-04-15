package entity.order;

import entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private User client;
    private BigDecimal cost;
    private LocalDateTime date;
    private Boolean delivered;
    private LocalDateTime dateOfDelivery;

}

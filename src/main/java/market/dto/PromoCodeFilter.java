package market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.enums.PromoCodeStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoCodeFilter {
    private String name;
    private String discount;
    private PromoCodeStatus status;
}

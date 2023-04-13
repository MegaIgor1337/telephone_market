package filter;

import entity.client.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderFilter(int limit,
                          int offset,
                          Client client,
                          BigDecimal cost,
                          LocalDateTime date,
                          Boolean delivered,
                          LocalDateTime dateOfDelivery) {
}

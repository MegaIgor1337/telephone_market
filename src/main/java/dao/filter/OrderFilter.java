package dao.filter;

import entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderFilter(int limit,
                          int offset,
                          User client,
                          BigDecimal cost,
                          LocalDateTime date,
                          Boolean delivered,
                          LocalDateTime dateOfDelivery) {
}

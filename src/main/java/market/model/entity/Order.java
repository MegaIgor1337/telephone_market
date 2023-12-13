package market.model.entity;

import lombok.*;

import jakarta.persistence.*;
import market.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"order\"")
public class Order implements BaseEntity<Long> {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private BigDecimal cost;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "date_of_delivery")
    private LocalDate dateOfDelivery;
    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> products = new ArrayList<>();

}

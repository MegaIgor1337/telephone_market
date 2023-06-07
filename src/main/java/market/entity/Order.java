package market.entity;

import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"order\"")
public class Order implements BaseEntity<Long>{
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private BigDecimal cost;
    private LocalDateTime date;
    private Boolean delivered;
    @Column(name = "date_of_delivery")
    private LocalDateTime dateOfDelivery;
    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> products = new ArrayList<>();

}

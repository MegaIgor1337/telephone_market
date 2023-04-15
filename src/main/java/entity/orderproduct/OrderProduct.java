package entity.orderproduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProduct {
    private PrimaryKeyOrderProduct primaryKeyOrderProduct;
    private int clientCount;

}

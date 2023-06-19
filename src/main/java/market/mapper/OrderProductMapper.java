package market.mapper;


import market.dto.OrderProductDto;
import market.entity.OrderProduct;
import org.mapstruct.Mapper;

import static market.util.StringContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {ProductMapper.class})
public interface OrderProductMapper {
    OrderProductDto orderProductToOrderProductDto(OrderProduct orderProduct);

}

package market.service.mapper;


import market.service.dto.OrderProductDto;
import market.model.entity.OrderProduct;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {ProductMapper.class})
public interface OrderProductMapper {
    OrderProductDto orderProductToOrderProductDto(OrderProduct orderProduct);

}

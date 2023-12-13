package market.service.mapper;


import market.service.dto.OrderDto;
import market.model.entity.Order;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {OrderProductMapper.class, AddressMapper.class})
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);
    Order orderDtoToOrder(OrderDto orderDto);
}

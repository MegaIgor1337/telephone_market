package market.mapper;


import market.dto.OrderDto;
import market.entity.Order;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING, uses = {OrderProductMapper.class, AddressMapper.class})
public interface OrderMapper {
    OrderDto orderToOrderDto(Order order);
    Order orderDtoToOrder(OrderDto orderDto);
}

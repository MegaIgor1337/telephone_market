package market.mapper;

import lombok.RequiredArgsConstructor;
import market.dto.OrderDto;
import market.dto.OrderDtoWithPage;
import market.util.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDtoWithPageMapper {
    public OrderDtoWithPage orderDtoToOrderDtoWithPage(OrderDto orderDto,
                                                       Integer pageNumber,
                                                       Integer pageSize) {
        var pages = PageUtil.createPageFromList(orderDto.getProducts(), PageRequest.of(pageNumber, pageSize));
        return OrderDtoWithPage.builder()
                .id(orderDto.getId())
                .user(orderDto.getUser())
                .products(pages)
                .status(orderDto.getStatus())
                .dateOfDelivery(orderDto.getDateOfDelivery())
                .date(orderDto.getDate())
                .cost(orderDto.getCost())
                .address(orderDto.getAddress())
                .build();
    }
}

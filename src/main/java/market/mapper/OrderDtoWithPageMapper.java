package market.mapper;

import lombok.RequiredArgsConstructor;
import market.dto.OrderDto;
import market.dto.OrderDtoWithPage;
import market.dto.OrderProductDto;
import market.dto.ProductFilter;
import market.repository.ProductRepository;
import market.service.ProductService;
import market.util.PageUtil;
import org.ehcache.core.util.CollectionUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .dateOfDelivery(orderDto.getDateOfDelivery())
                .date(orderDto.getDate())
                .cost(orderDto.getCost())
                .build();
    }
}

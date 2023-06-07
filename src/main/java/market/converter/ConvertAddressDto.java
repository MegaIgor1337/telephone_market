package market.converter;

import jakarta.servlet.http.HttpServletRequest;
import market.dto.CreateAddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class ConvertAddressDto implements Convert<CreateAddressDto> {


    @Override
    public CreateAddressDto convert(HttpServletRequest httpServletRequest) {
        Long id = (Long) httpServletRequest.getSession().getAttribute("addressId");
        System.out.println(id);
        return CreateAddressDto.builder()
                .id(id.toString())
                .country(httpServletRequest.getParameter("country"))
                .city(httpServletRequest.getParameter("city"))
                .street(httpServletRequest.getParameter("street"))
                .house(httpServletRequest.getParameter("house"))
                .flat(httpServletRequest.getParameter("flat"))
                .userId(httpServletRequest.getSession().getAttribute("userId").toString())
                .build();
    }

}

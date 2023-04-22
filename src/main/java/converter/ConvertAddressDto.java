package converter;

import dto.address.CreateAddressDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ConvertAddressDto implements Convert<CreateAddressDto>{
    private static final ConvertAddressDto INSTANCE = new ConvertAddressDto();





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

    public static ConvertAddressDto getInstance() {
        return INSTANCE;
    }
}

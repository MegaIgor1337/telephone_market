package market.http.controller;

import lombok.RequiredArgsConstructor;
import market.dto.CreateAddressDto;
import market.dto.CreateUpdateAddressDto;
import market.dto.UserDto;
import market.exception.ValidationException;
import market.service.AddressService;
import market.service.impl.AddressServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

import static market.util.ModelHelper.addAttributes;
import static market.util.ConstantContainer.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes({USER_DTO, ADDRESS_ID, PAGE_A})
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/address/addAddress")
    public String addAddress() {
        return "/address";
    }

    @PostMapping("/address/addAddress")
    public String addAddress(CreateAddressDto createAddressDto,  Model model,
                             RedirectAttributes redirectAttributes) {
        UserDto userDto = (UserDto) model.getAttribute(USER_DTO);
        createAddressDto.setUserId(String.valueOf(userDto.getId()));
        try {
            addressService.save(createAddressDto);
            return "redirect:/users";
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            redirectAtt(redirectAttributes, createAddressDto.getCity(), createAddressDto.getCountry(),
                    createAddressDto.getHouse(), createAddressDto.getStreet(), createAddressDto.getFlat());
            return "redirect:/address/addAddress";
        }
    }

    private void redirectAtt(RedirectAttributes redirectAttributes, String city, String country,
                             String house, String street, String flat) {
        redirectAttributes.addFlashAttribute(CITY, city);
        redirectAttributes.addFlashAttribute(COUNTRY, country);
        redirectAttributes.addFlashAttribute(HOUSE, house);
        redirectAttributes.addFlashAttribute(STREET, street);
        redirectAttributes.addFlashAttribute(FLAT, flat);
    }




    @PostMapping("/address/deleteAddress")
    public String deleteAddress(Model model,
                                String addressId,
                                String pageA) {
        addAttributes(model, Map.of(PAGE_A, pageA));
        addressService.delete(Long.valueOf(addressId));
        return "redirect:/users";
    }
}


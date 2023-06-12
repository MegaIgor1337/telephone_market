package market.controller;

import lombok.RequiredArgsConstructor;
import market.dto.CreateAddressDto;
import market.dto.CreateUpdateAddressDto;
import market.dto.UserDto;
import market.exception.ValidationException;
import market.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static market.util.StringContainer.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes({USER_DTO, ADDRESS_ID})
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/address/addAddress")
    public String addAddress() {
        return "/address";
    }

    @PostMapping("/address/addAddress")
    public String addAddress(String country, String city,
                             String street, String house,
                             String flat, Model model,
                             RedirectAttributes redirectAttributes) {
        UserDto userDto = (UserDto) model.getAttribute(USER_DTO);
        var createAddressDto = CreateAddressDto.builder()
                .country(country)
                .city(city)
                .street(street)
                .house(house)
                .flat(flat)
                .userId(userDto.getId().toString())
                .build();
        try {
            addressService.save(createAddressDto);
            return "redirect:/users/menu";
        } catch (ValidationException e) {
            model.addAttribute(ERRORS, e.getErrors());
            redirectAttributes.addFlashAttribute(CITY, city);
            redirectAttributes.addFlashAttribute(COUNTRY, country);
            redirectAttributes.addFlashAttribute(HOUSE, house);
            redirectAttributes.addFlashAttribute(STREET, street);
            redirectAttributes.addFlashAttribute(FLAT, flat);
            return "redirect:/address/addAddress";
        }
    }

    @GetMapping("/address/changeAddress")
    public String changeAddress(Model model, String addressId) {
        model.addAttribute(USER_DTO, addressId);
        return "/changeAddress";
    }

    @PostMapping("/address/changeAddress")
    public String changeAddress(String country, String city,
                                String street, String house,
                                String flat, Model model,
                                @SessionAttribute UserDto userDto,
                                @SessionAttribute String addressId,
                                RedirectAttributes redirectAttributes) {
        CreateUpdateAddressDto createAddressDto = CreateUpdateAddressDto.builder()
                .id(addressId)
                .country(country)
                .city(city)
                .street(street)
                .house(house)
                .flat(flat)
                .userId(userDto.getId().toString())
                .build();
        try {
            addressService.update(createAddressDto);
            return "redirect:/users/menu";
        } catch (ValidationException e) {
            model.addAttribute(ERRORS, e.getErrors());
            redirectAttributes.addFlashAttribute(CITY, city);
            redirectAttributes.addFlashAttribute(COUNTRY, country);
            redirectAttributes.addFlashAttribute(HOUSE, house);
            redirectAttributes.addFlashAttribute(STREET, street);
            redirectAttributes.addFlashAttribute(FLAT, flat);
            return "redirect:/address/changeAddress";
        }
    }

    @PostMapping("/address/deleteAddress")
    public String deleteAddress(String addressId) {
        addressService.delete(Long.valueOf(addressId));
        return "redirect:/users/menu";
    }

}


package market.service.util;

import lombok.experimental.UtilityClass;
import market.service.dto.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@UtilityClass
public class ModelHelper {
    public static void addAttributes(Model model, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            model.addAttribute(key, value);
        }
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes, UserFilter filter) {
        if (filter.getUsername() != null) redirectAttributes.addFlashAttribute(ConstantContainer.NAME,
                filter.getUsername());
        if (filter.getPassword() != null) redirectAttributes.addFlashAttribute(ConstantContainer.PASSWORD,
                filter.getPassword());
        if (filter.getPassportNo() != null) redirectAttributes.addFlashAttribute(ConstantContainer.PASSPORT_NO,
                filter.getPassportNo());
        if (filter.getEmail() != null) redirectAttributes.addFlashAttribute(ConstantContainer.EMAIL,
                filter.getEmail());
        if (filter.getGender() != null) redirectAttributes.addFlashAttribute(ConstantContainer.GENDER,
                filter.getGender());
        if (filter.getCountry() != null) redirectAttributes.addFlashAttribute(ConstantContainer.COUNTRY,
                filter.getCountry());
        if (filter.getCity() != null) redirectAttributes.addFlashAttribute(ConstantContainer.CITY,
                filter.getCity());
        if (filter.getStreet() != null) redirectAttributes.addFlashAttribute(ConstantContainer.STREET,
                filter.getStreet());
        if (filter.getHouse() != null) redirectAttributes.addFlashAttribute(ConstantContainer.HOUSE,
                filter.getHouse());
        if (filter.getFlat() != null) redirectAttributes.addFlashAttribute(ConstantContainer.FLAT,
                filter.getFlat());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes, ProductFilter createUserProductDto) {
        if (createUserProductDto.getCountry() != null) redirectAttributes.addFlashAttribute(ConstantContainer.COUNTRY,
                createUserProductDto.getCountry());
        if (createUserProductDto.getColor() != null) redirectAttributes.addFlashAttribute(ConstantContainer.COLOR,
                createUserProductDto.getColor());
        if (createUserProductDto.getModel() != null) redirectAttributes.addFlashAttribute(ConstantContainer.MODEL,
                createUserProductDto.getModel());
        if (createUserProductDto.getMinPrice() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.MIN_PRICE,
                    createUserProductDto.getMinPrice());
        if (createUserProductDto.getMaxPrice() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.MAX_PRICE,
                    createUserProductDto.getMaxPrice());
        if (createUserProductDto.getCount() != null) redirectAttributes.addFlashAttribute(ConstantContainer.COUNT,
                createUserProductDto.getCount());
        if (createUserProductDto.getBrand() != null) redirectAttributes.addFlashAttribute(ConstantContainer.BRAND,
                createUserProductDto.getBrand());
        if (createUserProductDto.getPriceQuery() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.SELECTED_PRICE_QUERY,
                    createUserProductDto.getPriceQuery());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          PromoCodeFilter promoCodeFilter) {
        if (promoCodeFilter.getName() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.NAME, promoCodeFilter.getName());
        if (promoCodeFilter.getDiscount() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.DISCOUNT, promoCodeFilter.getDiscount());
        if (promoCodeFilter.getStatus() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.STATUS, promoCodeFilter.getStatus());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            redirectAttributes.addFlashAttribute(key, value);
        }
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          CreatePromoCodeDto createPromoCodeDto) {
        if (createPromoCodeDto.getName() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.NAME, createPromoCodeDto.getName());
        if (createPromoCodeDto.getDiscount() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.DISCOUNT, createPromoCodeDto.getDiscount());
        if (createPromoCodeDto.getProductsId() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.PRODUCT_ID, createPromoCodeDto.getProductsId());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          CreateProductDto createProductDto) {
        if (createProductDto.getColor() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.COLOR, createProductDto.getColor());
        if (createProductDto.getBrand() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.BRAND, createProductDto.getBrand());
        if (createProductDto.getModel() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.MODEL, createProductDto.getModel());
        if (createProductDto.getCountry() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.COUNTRY, createProductDto.getCountry());
        if (createProductDto.getCost() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.COST, createProductDto.getCost());
        if (createProductDto.getCountry() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.COUNT, createProductDto.getCount());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          CreateUserDto createUserDto) {
        redirectAttributes.addFlashAttribute(ConstantContainer.USER_NAME, createUserDto.getUsername());
        redirectAttributes.addFlashAttribute(ConstantContainer.PASSWORD, createUserDto.getRawPassword());
        redirectAttributes.addFlashAttribute(ConstantContainer.PASSPORT_NO, createUserDto.getPassportNo());
        redirectAttributes.addFlashAttribute(ConstantContainer.EMAIL, createUserDto.getEmail());
        redirectAttributes.addFlashAttribute(ConstantContainer.GENDER, createUserDto.getGender());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          OrderFilterDto orderFilterDto) {
        if (orderFilterDto.getUsername() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.USER_NAME, orderFilterDto.getUsername());
        if (orderFilterDto.getNumber() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.NUMBER, orderFilterDto.getNumber());
        if (orderFilterDto.getStatus() != null)
            redirectAttributes.addFlashAttribute(ConstantContainer.STATUS, orderFilterDto.getStatus());
    }
}

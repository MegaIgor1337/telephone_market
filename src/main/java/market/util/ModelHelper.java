package market.util;

import lombok.experimental.UtilityClass;
import market.dto.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

import static market.util.StringContainer.*;
import static market.util.StringContainer.SELECTED_PRICE_QUERY;

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
        if (filter.getUsername() != null) redirectAttributes.addFlashAttribute(NAME,
                filter.getUsername());
        if (filter.getPassword() != null) redirectAttributes.addFlashAttribute(PASSWORD,
                filter.getPassword());
        if (filter.getPassportNo() != null) redirectAttributes.addFlashAttribute(PASSPORT_NO,
                filter.getPassportNo());
        if (filter.getEmail() != null) redirectAttributes.addFlashAttribute(EMAIL,
                filter.getEmail());
        if (filter.getGender() != null) redirectAttributes.addFlashAttribute(GENDER,
                filter.getGender());
        if (filter.getCountry() != null) redirectAttributes.addFlashAttribute(COUNTRY,
                filter.getCountry());
        if (filter.getCity() != null) redirectAttributes.addFlashAttribute(CITY,
                filter.getCity());
        if (filter.getStreet() != null) redirectAttributes.addFlashAttribute(STREET,
                filter.getStreet());
        if (filter.getHouse() != null) redirectAttributes.addFlashAttribute(HOUSE,
                filter.getHouse());
        if (filter.getFlat() != null) redirectAttributes.addFlashAttribute(FLAT,
                filter.getFlat());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes, ProductFilter createUserProductDto) {
        if (createUserProductDto.getCountry() != null) redirectAttributes.addFlashAttribute(COUNTRY,
                createUserProductDto.getCountry());
        if (createUserProductDto.getColor() != null) redirectAttributes.addFlashAttribute(COLOR,
                createUserProductDto.getColor());
        if (createUserProductDto.getModel() != null) redirectAttributes.addFlashAttribute(MODEL,
                createUserProductDto.getModel());
        if (createUserProductDto.getMinPrice() != null) redirectAttributes.addFlashAttribute(MIN_PRICE,
                createUserProductDto.getMinPrice());
        if (createUserProductDto.getMaxPrice() != null) redirectAttributes.addFlashAttribute(MAX_PRICE,
                createUserProductDto.getMaxPrice());
        if (createUserProductDto.getCount() != null) redirectAttributes.addFlashAttribute(COUNT,
                createUserProductDto.getCount());
        if (createUserProductDto.getBrand() != null) redirectAttributes.addFlashAttribute(BRAND,
                createUserProductDto.getBrand());
        if (createUserProductDto.getPriceQuery() != null) redirectAttributes.addFlashAttribute(SELECTED_PRICE_QUERY,
                createUserProductDto.getPriceQuery());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          PromoCodeFilter promoCodeFilter) {
        if (promoCodeFilter.getName() != null)
            redirectAttributes.addFlashAttribute(NAME, promoCodeFilter.getName());
        if (promoCodeFilter.getDiscount() != null)
            redirectAttributes.addFlashAttribute(DISCOUNT, promoCodeFilter.getDiscount());
        if (promoCodeFilter.getStatus() != null)
            redirectAttributes.addFlashAttribute(STATUS, promoCodeFilter.getStatus());
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
            redirectAttributes.addFlashAttribute(NAME, createPromoCodeDto.getName());
        if (createPromoCodeDto.getDiscount() != null)
            redirectAttributes.addFlashAttribute(DISCOUNT, createPromoCodeDto.getDiscount());
        if (createPromoCodeDto.getProductsId() != null)
            redirectAttributes.addFlashAttribute(PRODUCT_ID, createPromoCodeDto.getProductsId());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          CreateProductDto createProductDto) {
        if (createProductDto.getColor() != null)
            redirectAttributes.addFlashAttribute(COLOR, createProductDto.getColor());
        if (createProductDto.getBrand() != null)
            redirectAttributes.addFlashAttribute(BRAND, createProductDto.getBrand());
        if (createProductDto.getModel() != null)
            redirectAttributes.addFlashAttribute(MODEL, createProductDto.getModel());
        if (createProductDto.getCountry() != null)
            redirectAttributes.addFlashAttribute(COUNTRY, createProductDto.getCountry());
        if (createProductDto.getCost() != null)
            redirectAttributes.addFlashAttribute(COST, createProductDto.getCost());
        if (createProductDto.getCountry() != null)
            redirectAttributes.addFlashAttribute(COUNT, createProductDto.getCount());
    }

    public static void redirectAttributes( RedirectAttributes redirectAttributes,
                                           CreateUserDto createUserDto) {
        redirectAttributes.addFlashAttribute(USER_NAME, createUserDto.getUsername());
        redirectAttributes.addFlashAttribute(PASSWORD, createUserDto.getRawPassword());
        redirectAttributes.addFlashAttribute(PASSPORT_NO, createUserDto.getPassportNo());
        redirectAttributes.addFlashAttribute(EMAIL, createUserDto.getEmail());
        redirectAttributes.addFlashAttribute(GENDER, createUserDto.getGender());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          OrderFilterDto orderFilterDto) {
        if (orderFilterDto.getUsername() != null)
            redirectAttributes.addFlashAttribute(USER_NAME, orderFilterDto.getUsername());
        if (orderFilterDto.getNumber() != null)
            redirectAttributes.addFlashAttribute(NUMBER, orderFilterDto.getNumber());
        if (orderFilterDto.getStatus() != null)
            redirectAttributes.addFlashAttribute(STATUS, orderFilterDto.getStatus());
    }
}

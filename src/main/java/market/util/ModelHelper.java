package market.util;

import lombok.experimental.UtilityClass;
import market.dto.ProductFilter;
import market.dto.PromoCodeFilter;
import market.dto.UserFilter;
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

    public static void redirectAttributesUserFilter(RedirectAttributes redirectAttributes, UserFilter filter) {
        if (filter.getName() != null) redirectAttributes.addFlashAttribute(NAME,
                filter.getName());
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

    public static void redirectAttributesProductFilter(RedirectAttributes redirectAttributes, ProductFilter createUserProductDto) {
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

    public static void redirectAttributesPromoCodeFilter(RedirectAttributes redirectAttributes,
                                                         PromoCodeFilter promoCodeFilter) {
        if (promoCodeFilter.getName() != null)
            redirectAttributes.addFlashAttribute(promoCodeFilter.getName());
        if (promoCodeFilter.getDiscount() != null)
            redirectAttributes.addFlashAttribute(promoCodeFilter.getDiscount());
        if (promoCodeFilter.getStatus() != null)
            redirectAttributes.addFlashAttribute(promoCodeFilter.getStatus());
    }

    public static void redirectAttributes(RedirectAttributes redirectAttributes,
                                          Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            redirectAttributes.addFlashAttribute(key, value);
        }
    }
}

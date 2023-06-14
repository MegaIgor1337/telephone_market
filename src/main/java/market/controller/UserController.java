package market.controller;

import lombok.RequiredArgsConstructor;
import market.converter.CreateUserProductConverter;
import market.dto.*;
import market.enums.CommentStatus;
import market.enums.OrderStatus;
import market.exception.ValidationException;
import market.service.*;
import market.validator.Error;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static market.util.StringContainer.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@SessionAttributes({USER_DTO, USER_COMMENTS, PRODUCTS_IN_BASKET, MESSAGE_IF_SEARCHED_PRODUCTS_EMPTY,
        ADDRESSES, ERRORS, ID, PRODUCTS, FAVOURITES, SEARCHED_PRODUCTS, PRICE_QUERIES,
        CREATE_USER_PRODUCT_DTO})
public class UserController {
    private final CommentService commentService;
    private final AddressService addressService;
    private final CreateUserProductConverter createUserProductConverter;
    private final ProductService productService;
    private final UserService userService;
    private final ModelService modelService;
    private final ColorService colorService;
    private final CountryService countryService;
    private final BrandService brandService;

    @GetMapping("/menu")
    public String userMenu(Model model) {
        model.addAttribute(ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM));
        model.addAttribute(SEARCHED_PRODUCTS, Page.empty());
        model.addAttribute(MESSAGE_IF_SEARCHED_PRODUCTS_EMPTY, EMPTY_PARAM);
        return "user/menu";
    }

    @GetMapping("/{id}/addComment")
    public String addComment(@PathVariable(ID) Long id) {
        return "/addComment";
    }

    @PostMapping("/{id}/addComment")
    public String addComment(@PathVariable(ID) Long id, Model model,
                             String comment) {
        UserDto userDto = (UserDto) model.getAttribute(USER_DTO);
        CommentDto commentDto = CommentDto.builder()
                .comment(comment)
                .userDto(userDto)
                .commentStatus(CommentStatus.MODERATING)
                .build();
        commentService.save(commentDto);
        return "redirect:/users/menu";
    }

    @GetMapping("/{id}/personalComments")
    public String personalComments(@PathVariable(ID) Long id, Model model) {
        List<CommentDto> comments = commentService.findCommentsByUserId(id);
        model.addAttribute(USER_COMMENTS, comments);
        return "/userComments";
    }

    @PostMapping("/{id}/personalComments")
    public String personalComments(@PathVariable(ID) Long id, String commentId) {
        commentService.deleteComment(Long.valueOf(commentId));
        return "redirect:/users/{id}/personalComments";
    }

    @GetMapping("/{id}/profileMenu")
    public String personalMenu(@PathVariable(ID) Long id, Model model) {
        List<AddressDto> addressDtoList = addressService.getAddresses(id);
        model.addAttribute(ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM));
        model.addAttribute(ADDRESSES, addressDtoList);
        return "/user/profileMenu";
    }

    @GetMapping("/{id}/profileMenu/changeLogin")
    public String changeLogin(@PathVariable(ID) Long id) {
        return "/user/changeLogin";
    }

    @PostMapping("/{id}/profileMenu/changeLogin")
    public String changeLogin(@PathVariable(ID) Long id, Model model,
                              String password, String newLogin, @SessionAttribute UserDto userDto,
                              RedirectAttributes redirectAttributes) {
        try {
            userService.setNewLogin(userDto, newLogin, password);
            model.addAttribute(USER_DTO, userDto);
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute(NEW_LOGIN, newLogin);
            redirectAttributes.addFlashAttribute(PASSWORD, password);
            model.addAttribute(ERRORS, e.getErrors());
            return "redirect:/users/{id}/profileMenu/changeLogin";
        }
    }

    @GetMapping("/{id}/profileMenu/changeEmail")
    public String changeEmail(@PathVariable(ID) Long id) {
        return "user/changeEmail";
    }

    @PostMapping("/{id}/profileMenu/changeEmail")
    public String changeEmail(@PathVariable(ID) Long id, Model model,
                              @SessionAttribute UserDto userDto, String newEmail,
                              String password, RedirectAttributes redirectAttributes) {
        try {
            userService.setNewEmail(userDto, newEmail, password);
            model.addAttribute(USER_DTO, userDto);
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute(NEW_EMAIL, newEmail);
            redirectAttributes.addFlashAttribute(PASSWORD, password);
            model.addAttribute(ERRORS, e.getErrors());
            return "redirect:/users/{id}/profileMenu/changeEmail";
        }

    }

    @GetMapping("/{id}/profileMenu/changePassportNo")
    public String changePassportNo(@PathVariable(ID) Long id) {
        return "user/changePassportNo";
    }

    @PostMapping("users/{id}/profileMenu/changePassportNo")
    public String changePassportNo(@PathVariable(ID) Long id, Model model,
                                   @SessionAttribute UserDto userDto, String newPassportNo,
                                   String password, RedirectAttributes redirectAttributes) {
        try {
            userService.setNewPassportNo(userDto, newPassportNo, password);
            model.addAttribute(USER_DTO, userDto);
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute(NEW_PASSPORT_NO, newPassportNo);
            redirectAttributes.addFlashAttribute(PASSWORD, password);
            model.addAttribute(ERRORS, e.getErrors());
            return "redirect:/users/{id}/profileMenu/changePassportNo";
        }
    }

    @GetMapping("/{id}/profileMenu/changePassword")
    public String changePassword(@PathVariable(ID) Long id) {
        return "user/changePassword";
    }

    @PostMapping("/{id}/profileMenu/changePassword")
    public String changePassword(@PathVariable(ID) Long id, Model model,
                                 @SessionAttribute UserDto userDto, String newPassword,
                                 String oldPassword, RedirectAttributes redirectAttributes) {
        try {
            userService.setNewPassword(userDto, oldPassword, newPassword);
            model.addAttribute(USER_DTO, userDto);
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            model.addAttribute(ERRORS, e.getErrors());
            redirectAttributes.addFlashAttribute(NEW_PASSWORD, newPassword);
            redirectAttributes.addFlashAttribute(OLD_PASSWORD, oldPassword);
            return "redirect:/users/{id}/profileMenu/changePassword";
        }
    }

    @GetMapping("/{id}/profileMenu/putMoney")
    public String putMoney(@PathVariable(ID) Long id) {
        return "user/putMoney";
    }

    @PostMapping("/{id}/profileMenu/putMoney")
    public String putMoney(@PathVariable(ID) Long id, String money,
                           Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute(USER_DTO, userService.putMoney(id, money).get());
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            model.addAttribute(ERRORS, e.getErrors());
            redirectAttributes.addFlashAttribute(MONEY, money);
            return "redirect:/users/{id}/profileMenu/putMoney";
        }
    }


    @GetMapping("/{id}/products")
    public String viewProducts(@PathVariable(ID) Long id,
                               @RequestParam(name = PAGE, defaultValue = ZERO) Integer page,
                               Model m, RedirectAttributes redirectAttributes,
                               ProductFilter createUserProductDto) {
        m.addAttribute(ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM));
        m.addAttribute(MODELS, modelService.getAllModels());
        m.addAttribute(COUNTRIES, countryService.getAllCountries());
        m.addAttribute(BRANDS, brandService.getAllBrands());
        m.addAttribute(COLORS, colorService.getAllColors());
        m.addAttribute(FAVOURITES, productService
                .getProductsByUserIdInFavourites(id));
        m.addAttribute(PRODUCTS_IN_BASKET, productService
                .getProductsByUserIdAndOrderStatus(id, OrderStatus.WAITING_PAID));
        m.addAttribute(PRICE_QUERIES, List.of(CHEAP_FIRST, REACH_FIRST));
        try {
            PageRequest pageable;
            if (CHEAP_FIRST.equals(createUserProductDto.getPriceQuery())) {
                pageable = PageRequest.of(page, 2, Sort.by(COST));
            } else if (REACH_FIRST.equals(createUserProductDto.getPriceQuery())) {
                pageable = PageRequest.of(page, 2, Sort.by(COST).descending());
            } else {
                pageable = PageRequest.of(page, 2);
            }
            var searchedProducts = productService
                                .getProductsByPredicates(createUserProductDto, pageable);
            var products = searchedProducts.isEmpty() ? productService.getProducts(pageable) : searchedProducts;
            m.addAttribute(PRODUCTS, products);
            redirectAttributes(redirectAttributes, createUserProductDto);
            return "redirect:/users/products";
        } catch (ValidationException e) {
            m.addAttribute(ERRORS, e.getErrors());
            return "redirect:/users/products";
        }
    }


   @GetMapping("/products")
   public String viewProducts() {
        return "user/products";
   }



    private void redirectAttributes(RedirectAttributes redirectAttributes, ProductFilter createUserProductDto) {
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
}

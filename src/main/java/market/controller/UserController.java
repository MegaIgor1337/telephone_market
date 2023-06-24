package market.controller;

import lombok.RequiredArgsConstructor;
import market.dto.*;
import market.enums.OrderStatus;
import market.exception.LackOfMoneyException;
import market.exception.ValidationException;
import market.service.*;
import market.validator.Error;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static market.util.StringContainer.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@SessionAttributes({USER_DTO, USER_COMMENTS, PRODUCTS_IN_BASKET, MESSAGE_IF_SEARCHED_PRODUCTS_EMPTY,
        ADDRESSES, ERRORS, ID, PRODUCTS, FAVOURITES, PRICE_QUERIES, PAGE_FB, ORDER,
        CREATE_USER_PRODUCT_DTO, DISCOUNTED_ORDER, PAGE_F})
public class UserController {
    private final CommentService commentService;
    private final AddressService addressService;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final FavouriteService favouriteService;
    private final ModelService modelService;
    private final ColorService colorService;
    private final CountryService countryService;
    private final BrandService brandService;
    private OrderDto discountedOrder;

    @GetMapping("/menu")
    public String userMenu(Model model) {
        model.addAttribute(ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM));
        return "/user/menu";
    }

    @GetMapping("/{id}/addComment")
    public String addComment(@PathVariable(ID) Long id) {
        return "/addComment";
    }

    @PostMapping("/{id}/addComment")
    public String addComment(@PathVariable(ID) Long id,
                             String comment) {
        commentService.save(comment, id);
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
    public String personalMenu(@PathVariable(ID) Long id,
                               @RequestParam(name = PAGE, defaultValue = ZERO) Integer page,
                               Model model) {
        String pageA = (String) model.getAttribute(PAGE_A);
        String pageMain = EMPTY_PARAM.equals(pageA) || pageA == null ? page.toString() : pageA;
        var addressDtoList = addressService.getAddresses(id, Integer.valueOf(pageMain));
        model.addAttribute(PAGE_A, EMPTY_PARAM);
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
        return "/user/changePassportNo";
    }

    @PostMapping("/{id}/profileMenu/changePassportNo")
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
                               ProductFilter filter) {
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
            String pageM = (String) m.getAttribute(PAGE_FB);
            String pageFB = EMPTY_PARAM.equals(pageM) || pageM == null ? page.toString() : pageM;
            var products = productService.getProductsByPredicates(filter, Integer.parseInt(pageFB));
            m.addAttribute(PRODUCTS, products);
            m.addAttribute(PAGE_FB, EMPTY_PARAM);
            redirectAttributes(redirectAttributes, filter);
            return "redirect:/users/products";
        } catch (ValidationException e) {
            redirectAttributes(redirectAttributes, filter);
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

    @PostMapping("/{id}/products/addToFavourite")
    public String addToFavourite(@PathVariable(ID) Long id, Model model,
                                 String productId, String pageFB, ProductFilter productFilter,
                                 RedirectAttributes redirectAttributes) {
        favouriteService.addFavourite(id, Long.valueOf(productId));
        model.addAttribute(PAGE_FB, pageFB);
        redirectAttributes(redirectAttributes, productFilter);
        return "redirect:/users/{id}/products";
    }

    @PostMapping("/{id}/products/addToBasket")
    public String addToBasket(@PathVariable(ID) Long id, String productId, String pageFB,
                              Model model, String count, ProductFilter productFilter,
                              RedirectAttributes redirectAttributes) {
        orderService.addProductToBasket(id, productId, count);
        model.addAttribute(PAGE_FB, pageFB);
        redirectAttributes(redirectAttributes, productFilter);
        return "redirect:/users/{id}/products";
    }

    @GetMapping("/{id}/order")
    public String order(@PathVariable(ID) Long id, Model model,
                        @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        String pageM = (String) model.getAttribute(PAGE_OR);
        String pageFB = EMPTY_PARAM.equals(pageM) || pageM == null ? page.toString() : pageM;
        model.addAttribute(PAGE_OR, EMPTY_PARAM);
        var order = orderService.findByUserIdActiveBasket(id, Integer.valueOf(pageFB));
        var presentOrder = order.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var modelOrder = discountedOrder == null  ? presentOrder : orderService
                .getOrderDtoWithPage(discountedOrder, Integer.valueOf(pageFB));
        model.addAttribute(ORDER, modelOrder);
        return "user/order";
    }

    @PostMapping("/{id}/order/delete")
    public String deleteProductFromBasket(@PathVariable(ID) Long id, String productId,
                                String pageOr, Model model) {
        orderService.deleteProductFromBasket(Long.valueOf(productId), id);
        model.addAttribute(PAGE_OR, pageOr);
        return "redirect:/users/{id}/order";
    }

    @GetMapping("/{id}/order/paidOrder")
    public String payiedOrder() {
        return "user/paidOrder";
    }

    @PostMapping("/{id}/order/pay")
    public String payOrder(@PathVariable(ID) Long id, Model model,
                           @SessionAttribute OrderDtoWithPage order) {
        try {
            discountedOrder = null;
            var userDto = (UserDto) model.getAttribute(USER_DTO);
            orderService.payOrder(userDto, order);
            return "redirect:/users/{id}/order/paidOrder";
        } catch (LackOfMoneyException e) {
            model.addAttribute(ERRORS, e.getErrors());
            return "redirect:/users/{id}/profileMenu/putMoney";
        }
    }

    @PostMapping("/{id}/order/promoCode")
    public String searchPromoCode(@PathVariable(ID) Long id, Model model,
                                  String promoCode, RedirectAttributes redirectAttributes) {
        try {
            discountedOrder = orderService.acceptPromoCode(promoCode, id);
            return "redirect:/users/{id}/order";
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute(PROMO_CODE, promoCode);
            model.addAttribute(ERRORS, e.getErrors());
            return "redirect:/users/{id}/order";
        }
    }


    @GetMapping("/{id}/favourite")
    public String viewFavourite(@PathVariable(ID) Long id, Model model,
                                @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        String pageM = (String) model.getAttribute(PAGE_F);
        model.addAttribute(PRODUCTS_IN_BASKET, productService
                .getProductsByUserIdAndOrderStatus(id, OrderStatus.WAITING_PAID));
        String pageF = EMPTY_PARAM.equals(pageM) || pageM == null ? page.toString() : pageM;
        model.addAttribute(PAGE_F, EMPTY_PARAM);
        var favourites = favouriteService.getFavouritesByUserId(id, Integer.valueOf(pageF));
        model.addAttribute(FAVOURITE_PRODUCTS, favourites);
        return "user/favourite";
    }

    @PostMapping("/{id}/favourite/deleteFavourite")
    public String deleteFavourite(@PathVariable(ID) Long id, Model model, String pageF,
                                  Long favouriteId) {
        model.addAttribute(PAGE_F, pageF);
        favouriteService.deleteProductFromFavourite(favouriteId);
        return "redirect:/users/{id}/favourite";
    }

    @PostMapping("/{id}/favourite/addToBasketFromFavourite")
    public String addToBasketFromFavourite(@PathVariable(ID) Long id, Model model, String pageF,
                                           String productId) {
        orderService.addProductToBasket(id, productId, null);
        model.addAttribute(PAGE_F, pageF);
        return "redirect:/users/{id}/favourite";
    }

    @GetMapping("/{id}/orderHistory")
    public String viewOrderHistory(@PathVariable(ID) Long id,
                                   @RequestParam(name = PAGE, defaultValue = ZERO) String page, Model model) {
        var pageOrders = orderService.findAllOrdersByUserID(id, Integer.valueOf(page));
        model.addAttribute(USER_ORDERS, pageOrders);
        return "user/orderHistory";
    }

    @GetMapping("/{id}/orders/{orderId}")
    public String viewOrder(@PathVariable(ID) Long id,
                            @PathVariable(ORDER_ID) Long orderId,
                            Model model,
                            @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        var order = orderService.findOrderById(orderId, page);
        model.addAttribute(USER_ORDER, order);
        return "user/productFromOH";
    }

    @PostMapping("/{id}/avatar/setNew")
    public String setNewAvatar(@PathVariable(ID) Long id,
                               Model model, MultipartFile image) {
        var user = userService.setNewAvatar(id, image);
        model.addAttribute(USER_DTO, user);
        return "redirect:/users/{id}/profileMenu";
    }
}

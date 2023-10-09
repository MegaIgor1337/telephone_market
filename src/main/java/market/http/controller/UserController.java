package market.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.dto.*;
import market.enums.OrderStatus;
import market.exception.LackOfMoneyException;
import market.exception.ValidationException;
import market.service.*;
import market.service.impl.*;
import market.util.ModelHelper;
import market.validator.Error;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

import static market.util.AccessDeniedExceptionHelper.setNewAuthentication;
import static market.util.ModelHelper.addAttributes;
import static market.util.ModelHelper.redirectAttributes;
import static market.util.ConstantContainer.*;

@Controller
@Slf4j
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

    @GetMapping
    public String userMenu(Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {    //получить инфу о юзере который работает
        var user = userService.getUserByName(userDetails.getUsername());
        addAttributes(model, Map.of(USER_DTO, user,
                ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM)));
        return "/user/menu";
    }

    @GetMapping("/{id}/addComment")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String addComment(@PathVariable(ID) Long id,
                             Authentication authentication) {
        return "/addComment";
    }

    @PostMapping("/{id}/addComment")
    public String addComment(@PathVariable(ID) Long id,
                             String comment) {
        log.info("User with id {} started adding comment", id);
        commentService.save(comment, id);
        log.info("User with id {} wrote comment", id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/personalComments")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String personalComments(@PathVariable(ID) Long id,
                                   Model model,
                                   Authentication authentication) {
        List<CommentDto> comments = commentService.findCommentsByUserId(id);
        addAttributes(model, Map.of(USER_COMMENTS, comments));
        return "/userComments";
    }

    @PostMapping("/{id}/personalComments/delete")
    public String personalComments(@PathVariable(ID) Long id,
                                   String commentId) {
        commentService.deleteComment(Long.valueOf(commentId));
        return "redirect:/users/{id}/personalComments";
    }

    @GetMapping("/{id}/profileMenu")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String personalMenu(@PathVariable(ID) Long id,
                               Authentication authentication,
                               @RequestParam(name = PAGE, defaultValue = ZERO) Integer page,
                               Model model) {
        String pageA = (String) model.getAttribute(PAGE_A);
        String pageMain = EMPTY_PARAM.equals(pageA) || pageA == null ? page.toString() : pageA;
        var addressDtoList = addressService.getAddressesByUserId(id, Integer.valueOf(pageMain));
        addAttributes(model, Map.of(PAGE_A, EMPTY_PARAM,
                ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM),
                ADDRESSES, addressDtoList));
        return "/user/profileMenu";
    }

    @PostMapping("/{id}/profileMenu/changeAvatar")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String changeImage(@PathVariable(ID) Long id,
                              MultipartFile image,
                              Authentication authentication) {
        userService.updateImage(id, image);
        return "redirect:/users/{id}/profileMenu";
    }


    @GetMapping("/{id}/profileMenu/changeLogin")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String changeLogin(@PathVariable(ID) Long id,
                              Authentication authentication) {
        return "/user/changeLogin";
    }

    @PostMapping("/{id}/profileMenu/changeLogin")
    public String changeLogin(@PathVariable(ID) Long id, Model model,
                              Authentication authentication,
                              String newLogin,
                              @SessionAttribute UserDto userDto,
                              RedirectAttributes redirectAttributes) {
        try {
            userService.setNewLogin(userDto, newLogin);
            addAttributes(model, Map.of(USER_DTO, userService.findById(id)));
            setNewAuthentication(authentication, newLogin);
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            ModelHelper.redirectAttributes(redirectAttributes, Map.of(NEW_LOGIN, newLogin));
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            return "redirect:/users/{id}/profileMenu/changeLogin";
        }
    }

    @GetMapping("/{id}/profileMenu/changeEmail")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String changeEmail(@PathVariable(ID) Long id,
                              Authentication authentication) {
        return "user/changeEmail";
    }

    @PostMapping("/{id}/profileMenu/changeEmail")
    public String changeEmail(@PathVariable(ID) Long id,
                              Model model,
                              @SessionAttribute UserDto userDto,
                              String newEmail,
                              RedirectAttributes redirectAttributes) {
        try {
            userService.setNewEmail(userDto, newEmail);
            addAttributes(model, Map.of(USER_DTO, userService.findById(id)));
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            ModelHelper.redirectAttributes(redirectAttributes, Map.of(NEW_EMAIL, newEmail));
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            return "redirect:/users/{id}/profileMenu/changeEmail";
        }

    }

    @GetMapping("/{id}/profileMenu/changePassportNo")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String changePassportNo(@PathVariable(ID) Long id,
                                   Authentication authentication) {
        return "/user/changePassportNo";
    }

    @PostMapping("/{id}/profileMenu/changePassportNo")
    public String changePassportNo(@PathVariable(ID) Long id,
                                   Model model,
                                   @SessionAttribute UserDto userDto,
                                   String newPassportNo,
                                   RedirectAttributes redirectAttributes) {
        try {
            userService.setNewPassportNo(userDto, newPassportNo);
            addAttributes(model, Map.of(USER_DTO, userService.findById(id)));
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            ModelHelper.redirectAttributes(redirectAttributes, Map.of(NEW_PASSPORT_NO, newPassportNo));
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            return "redirect:/users/{id}/profileMenu/changePassportNo";
        }
    }



    @GetMapping("/{id}/profileMenu/putMoney")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String putMoney(@PathVariable(ID) Long id,
                           Authentication authentication) {
        return "user/putMoney";
    }

    @PostMapping("/{id}/profileMenu/putMoney")
    public String putMoney(@PathVariable(ID) Long id,
                           String money,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            log.info("User with id {}, started putting money {}", id, money);
            addAttributes(model, Map.of(USER_DTO, userService.putMoney(id, money)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
            return "redirect:/users/{id}/profileMenu";
        } catch (ValidationException e) {
            log.info("User with id {} entered invalid count of money {}", id, money);
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            ModelHelper.redirectAttributes(redirectAttributes, Map.of(MONEY, money));
            return "redirect:/users/{id}/profileMenu/putMoney";
        }
    }


    @GetMapping("/{id}/products")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String viewProducts(@PathVariable(ID) Long id,
                               @RequestParam(name = PAGE, defaultValue = ZERO) Integer page,
                               Model m,
                               RedirectAttributes redirectAttributes,
                               Authentication authentication,
                               ProductFilter filter) {
        addAttributes(m, Map.of(ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM),
                MODELS, modelService.getAllModels(),
                COUNTRIES, countryService.getAllCountries(),
                BRANDS, brandService.getAllBrands(),
                COLORS, colorService.getAllColors(),
                FAVOURITES, productService
                        .getProductsByUserIdInFavourites(id),
                PRODUCTS_IN_BASKET, productService
                        .getProductsByUserIdAndOrderStatus(id, OrderStatus.WAITING_PAID),
                PRICE_QUERIES, List.of(CHEAP_FIRST, REACH_FIRST)));
        try {
            String pageM = (String) m.getAttribute(PAGE_FB);
            String pageFB = EMPTY_PARAM.equals(pageM) || pageM == null ? page.toString() : pageM;
            var products = productService.getProductsByPredicates(filter, Integer.parseInt(pageFB));
            addAttributes(m, Map.of(PRODUCTS, products,
                    PAGE_FB, EMPTY_PARAM));
            redirectAttributes(redirectAttributes, filter);
            return "redirect:/users/products";
        } catch (ValidationException e) {
            addAttributes(m, Map.of(ERRORS, e.getErrors()));
            redirectAttributes(redirectAttributes, filter);
            return "redirect:/users/products";
        }
    }


   @GetMapping("/products")
   public String viewProducts() {
        return "user/products";
   }





    @PostMapping("/{id}/products/addToFavourite")
    public String addToFavourite(@PathVariable(ID) Long id,
                                 Model model,
                                 String productId,
                                 String pageFB,
                                 ProductFilter productFilter,
                                 RedirectAttributes redirectAttributes) {
        log.info("User {} started adding product {} to favourite", id, productId);
        favouriteService.addFavourite(id, Long.valueOf(productId));
        addAttributes(model, Map.of(PAGE_FB, pageFB));
        redirectAttributes(redirectAttributes, productFilter);
        return "redirect:/users/{id}/products";
    }

    @PostMapping("/{id}/products/addToBasket")
    public String addToBasket(@PathVariable(ID) Long id,
                              String productId,
                              String pageFB,
                              Model model,
                              String count,
                              ProductFilter productFilter,
                              RedirectAttributes redirectAttributes) {
        log.info("User {} started processing of adding product {} to basket", id, productId);
        orderService.addProductToBasket(id, productId, count);
        addAttributes(model, Map.of(PAGE_FB, pageFB));
        redirectAttributes(redirectAttributes, productFilter);
        return "redirect:/users/{id}/products";
    }

    @GetMapping("/{id}/order")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String order(@PathVariable(ID) Long id,
                        Model model,
                        @RequestParam(name = PAGE, defaultValue = ZERO) Integer page,
                        Authentication authentication) {
        String pageM = (String) model.getAttribute(PAGE_OR);
        String pageFB = EMPTY_PARAM.equals(pageM) || pageM == null ? page.toString() : pageM;
        addAttributes(model, Map.of(PAGE_OR, EMPTY_PARAM));
        var order = orderService.findByUserIdActiveBasket(id, Integer.valueOf(pageFB));
        var presentOrder = order.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var modelOrder = discountedOrder == null  ? presentOrder : orderService
                .getOrderDtoWithPage(discountedOrder, Integer.valueOf(pageFB));
        var deliveryAddresses = addressService.getAddressesByUserId(id);
        addAttributes(model, Map.of(ORDER, modelOrder,
                DELIVERY_ADDRESSES, deliveryAddresses));
        return "user/order";
    }

    @PostMapping("/{id}/order/delete")
    public String deleteProductFromBasket(@PathVariable(ID) Long id,
                                          String productId,
                                          String pageOr,
                                          Model model) {
        log.info("User {} started processing of deleting product {} from basket", id, productId);
        orderService.deleteProductFromBasket(Long.valueOf(productId), id);
        addAttributes(model, Map.of(PAGE_OR, pageOr));
        return "redirect:/users/{id}/order";
    }

    @GetMapping("/{id}/order/paidOrder")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String payiedOrder(@PathVariable(ID) Long id,
                              Authentication authentication) {
        return "user/paidOrder";
    }

    @PostMapping("/{id}/order/pay")
    public String payOrder(@PathVariable(ID) Long id,
                           Model model,
                           Long deliveryAddress,
                           @ModelAttribute UserDto userDto,
                           @SessionAttribute OrderDtoWithPage order) {
        try {
            log.info("User with id {}, started paein order {}", id,order.getId());
            discountedOrder = null;
            orderService.payOrder(userDto, deliveryAddress,  order);
            return "redirect:/users/{id}/order/paidOrder";
        } catch (LackOfMoneyException e) {
            log.info("User does not have money");
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            return "redirect:/users/{id}/profileMenu/putMoney";
        }
    }

    @PostMapping("/{id}/order/promoCode")
    public String searchPromoCode(@PathVariable(ID) Long id,
                                  Model model,
                                  String promoCode,
                                  RedirectAttributes redirectAttributes) {
        try {
            discountedOrder = orderService.acceptPromoCode(promoCode, id);
            return "redirect:/users/{id}/order";
        } catch (ValidationException e) {
            ModelHelper.redirectAttributes(redirectAttributes, Map.of(PROMO_CODE, promoCode));
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            return "redirect:/users/{id}/order";
        }
    }


    @GetMapping("/{id}/favourite")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String viewFavourite(@PathVariable(ID) Long id,
                                Model model,
                                @RequestParam(name = PAGE, defaultValue = ZERO) Integer page,
                                Authentication authentication) {
        String pageM = (String) model.getAttribute(PAGE_F);
        String pageF = EMPTY_PARAM.equals(pageM) || pageM == null ? page.toString() : pageM;
        var favourites = favouriteService.getFavouritesByUserId(id, Integer.valueOf(pageF));
        addAttributes(model, Map.of(PRODUCTS_IN_BASKET, productService
                .getProductsByUserIdAndOrderStatus(id, OrderStatus.WAITING_PAID),
                        PAGE_F, EMPTY_PARAM,
                        FAVOURITE_PRODUCTS, favourites));
        return "user/favourite";
    }

    @PostMapping("/{id}/favourite/deleteFavourite")
    public String deleteFavourite(@PathVariable(ID) Long id,
                                  Model model,
                                  String pageF,
                                  Long favouriteId) {
        log.info("User {} started deleting product {} from favourite", id, favouriteId);
        addAttributes(model, Map.of(PAGE_F, pageF));
        favouriteService.deleteProductFromFavourite(favouriteId);
        return "redirect:/users/{id}/favourite";
    }

    @PostMapping("/{id}/favourite/addToBasketFromFavourite")
    public String addToBasketFromFavourite(@PathVariable(ID) Long id,
                                           Model model,
                                           String pageF,
                                           String productId) {
        log.info("User {} started adding product {} to basket", id, productId);
        orderService.addProductToBasket(id, productId, null);
        addAttributes(model, Map.of(PAGE_F, pageF));
        return "redirect:/users/{id}/favourite";
    }

    @GetMapping("/{id}/orderHistory")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String viewOrderHistory(@PathVariable(ID) Long id,
                                   Authentication authentication,
                                   @RequestParam(name = PAGE, defaultValue = ZERO) String page,
                                   Model model) {
        var pageOrders = orderService.findAllOrdersByUserID(id, Integer.valueOf(page));
        addAttributes(model, Map.of(USER_ORDERS, pageOrders));
        return "user/orderHistory";
    }

    @GetMapping("/{id}/orders/{orderId}")
    @PreAuthorize("(@userServiceImpl.getUsernameByID(#id)) == authentication.principal.username ")
    public String viewOrder(@PathVariable(ID) Long id,
                            Authentication authentication,
                            @PathVariable(ORDER_ID) Long orderId,
                            Model model,
                            @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        var order = orderService.findById(orderId, page);
        addAttributes(model, Map.of(USER_ORDER, order));
        return "user/productFromOH";
    }
}

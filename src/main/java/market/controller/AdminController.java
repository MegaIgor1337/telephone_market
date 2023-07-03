package market.controller;

import lombok.RequiredArgsConstructor;
import market.dto.*;
import market.enums.OrderStatus;
import market.enums.PromoCodeStatus;
import market.exception.ValidationException;
import market.service.*;
import market.util.ModelHelper;
import market.validator.Error;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static market.util.ModelHelper.addAttributes;
import static market.util.ModelHelper.redirectAttributes;
import static market.util.StringContainer.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@SessionAttributes({SIZE_MODERATE_COMMENTS, ACCESSED_COMMENTS, USER, ERRORS, MODELS,
        PRODUCTS, COUNTRIES, COLORS, BRANDS, PAGE_PR, PROMO_CODES, ORDER_STATUSES,
        PRODUCTS_DOR_ADDING_PROMO_CODE, PROMO_CODE_CHANGE_ERRORS,
        PAGE_U, MODERATE_COMMENTS, MODERATE_ORDERS, MODERATE_ORDER, PROMO_CODE_STATUSES})
public class AdminController {
    private final CommentService commentService;
    private final PromoCodeService promoCodeService;
    private final ProductService productService;
    private final ColorService colorService;
    private final CountryService countryService;
    private final ModelService modelService;
    private final BrandService brandService;
    private final UserService userService;
    private final OrderService orderService;
    private final PromoCodeProductService promoCodeProductService;
    private final AddressService addressService;

    @ModelAttribute(PROMO_CODE_STATUSES)
    public List<PromoCodeStatus> getPromoCodeStatuses() {return Arrays.asList(PromoCodeStatus.values());}
    @ModelAttribute(ORDER_STATUSES)
    public List<OrderStatus> getOrderStatuses() {
        return Arrays.asList(OrderStatus.values());
    }
    @GetMapping("/menu")
    public String adminMenu(Model model) {
        var sizeModerateOrders = orderService.getSizeModerateOrders();
        addAttributes(model, Map.of(SIZE_MODERATE_ORDERS, sizeModerateOrders,
                ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM)));
        return "admin/adminMenu";
    }

    @GetMapping("/menu/moderateComments")
    public String moderateComments(Model model) {
        List<CommentDto> commentsDto = commentService.getModerateComments();
        addAttributes(model, Map.of(MODERATE_COMMENTS, commentsDto));
        return "admin/moderateComments";
    }

    @PostMapping("/menu/moderateComments")
    public String moderateComments(String commentId, String value, Model model) {
        if (value.equals(ACCESS)) {
            commentService.updateCommentFromModeratingToAccess(Long.valueOf(commentId));
        } else {
            commentService.deleteComment(Long.valueOf(commentId));
        }
        addAttributes(model, Map.of(SIZE_MODERATE_COMMENTS,
                commentService.getModerateComments().size()));
        return "redirect:/admin/menu/moderateComments";
    }

    @GetMapping("/menu/accessedComments")
    public String accessedComments(Model model) {
        List<CommentDto> commentsDto = commentService.getAccessedComments();
        addAttributes(model, Map.of(ACCESSED_COMMENTS, commentsDto));
        return "admin/accessedCommentsForAdmin";
    }

    @PostMapping("/menu/accessedComments")
    public String accessedComments(String commentId) {
        commentService.deleteComment(Long.valueOf(commentId));
        return "redirect:/admin/menu/accessedComments";
    }

    @GetMapping("/menu/users")
    public String viewAllUsers(Model model,
                               @RequestParam(value = PAGE, defaultValue = ZERO) Integer page,
                               UserFilter filter, RedirectAttributes redirectAttributes) {
        String pageU = (String) model.getAttribute(PAGE_U);
        String pageMain = EMPTY_PARAM.equals(pageU) || pageU == null ? page.toString() : pageU;
        var users = userService.getUsersByPredicates(filter, Integer.valueOf(pageMain));
        addAttributes(model, Map.of(PAGE_U, EMPTY_PARAM,
                USERS, users));
        ModelHelper.redirectAttributes(redirectAttributes, filter);
        return "/admin/users";
    }

    @PostMapping("/menu/users/{id}/delete")
    public String deleteUser(@PathVariable(ID) Long id,
                             String pageU, Model model, UserFilter filter,
                             RedirectAttributes redirectAttributes) {
        addAttributes(model, Map.of(PAGE_U, pageU));
        userService.deleteUser(id);
        ModelHelper.redirectAttributes(redirectAttributes, filter);
        return "redirect:/admin/menu/users";
    }

    @GetMapping("/menu/users/{id}/profile")
    public String viewUserProfile(@PathVariable(ID) Long id,
                                  @RequestParam(name = PAGE_A, defaultValue = ZERO) Integer pageA,
                                  @RequestParam(name = PAGE_O, defaultValue = ZERO) Integer pageO,
                                  Model model, UserFilter filter, String pageU,
                                  RedirectAttributes redirectAttributes) {
        var userOrders = orderService.findAllOrdersByUserID(id, pageO);
        var addresses = addressService.getAddresses(id, pageA);
        var user = userService.findById(id);
        addAttributes(model, Map.of(USER_ORDERS, userOrders,
                PAGE_U, pageU,
                USER, user,
                ADDRESSES, addresses));
        ModelHelper.redirectAttributes(redirectAttributes, filter);
        return "/admin/userProfile";
    }

    @GetMapping("/menu/users/{id}/profile/orders/{orderId}")
    public String viewOrder(@PathVariable(ID) Long id,
                            @PathVariable(ORDER_ID) Long orderId,
                            @RequestParam(name = PAGE_PR, defaultValue = ZERO) String pagePr,
                            Model model) {
        var order = orderService.findOrderById(orderId, Integer.valueOf(pagePr));
        addAttributes(model, Map.of(USER_ORDER, order));
        return "/admin/userOrder";
    }



    @GetMapping("/menu/moderateOrders")
    public String moderateOrders(Model model,
                                 @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        var orders = orderService.getOrdersByStatus(OrderStatus.MODERATING, page);
        addAttributes(model, Map.of(MODERATE_ORDERS, orders));
        return "/admin/moderateOrders";
    }

    @GetMapping("/menu/moderateOrders/{id}/moderate")
    public String moderate(@PathVariable(ID) Long id, Model model,
                           @RequestParam(name = PAGE_PR, defaultValue = ZERO) Integer pagePr) {

        var moderateOrder = orderService.findOrderById(id, pagePr);
        addAttributes(model, Map.of(MODERATE_ORDER, moderateOrder));
        return "/admin/moderatingOrders";
    }

    @PostMapping("/menu/moderateOrders/{id}/moderate")
    public String moderate(@PathVariable(ID) Long id, Model model,
                           ModerateOrderDto moderateOrderDto,
                           RedirectAttributes redirectAttributes) {
        try {
            orderService.moderateOrder(id, moderateOrderDto);
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            ModelHelper.redirectAttributes(redirectAttributes,
                    Map.of(DATE_OF_DELIVERY, moderateOrderDto.getDateOfDelivery()));
        }
        return "redirect:/admin/menu/moderateOrders";
    }

    @GetMapping("/menu/orders")
    public String viewAllOrders(Model model,
                                @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        var orders = orderService.getAllOrders(page);
        addAttributes(model, Map.of(ORDERS, orders));
        return "/admin/orders";
    }

    @GetMapping("/menu/orders/{id}/viewInfo")
    public String viewInfoOrder(@PathVariable(ID) Long id, Model model,
                                @RequestParam(name = PAGE_PR, defaultValue = ZERO) Integer pagePr) {
        var order = orderService.findOrderById(id, pagePr);
        var deliveryAddresses = addressService.getAddressesByUserId(order.getUser().getId());
        addAttributes(model, Map.of(ORDER, order,
                DELIVERY_ADDRESSES, deliveryAddresses));
        return "/admin/orderInfo";
    }

    @GetMapping("/menu/orders/{id}/viewInfo/changeInfo")
    public String changeInfoOrder(@PathVariable(ID) Long id, Model model,
                                  ModerateOrderDto moderateOrderDto,
                                  RedirectAttributes redirectAttributes) {
        try {
            orderService.moderateOrder(id, moderateOrderDto);
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            redirectAttributes(redirectAttributes,
                    Map.of(DATE_OF_DELIVERY, moderateOrderDto.getDateOfDelivery()));
        }
        return "redirect:/admin/menu/orders";
    }

    @GetMapping("/menu/productsForAdmin")
    public String viewAllProducts(ProductFilter productFilter, Model model,
                                  RedirectAttributes redirectAttributes,
                                  @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        var pageM = ((String) model.getAttribute(PAGE_PR));
            var pageMain = EMPTY_PARAM.equals(pageM) || pageM == null ? page.toString() : pageM;
        var products = productService.getProductsByPredicates(productFilter,
                Integer.valueOf(pageMain));
        addAttributes(model, Map.of(MODELS, modelService.getAllModels(),
                COUNTRIES, countryService.getAllCountries(),
                BRANDS, brandService.getAllBrands(),
                COLORS, colorService.getAllColors(),
                PRICE_QUERIES, List.of(CHEAP_FIRST, REACH_FIRST),
                PAGE_PR, EMPTY_PARAM,
                PRODUCTS, products));
        redirectAttributes(redirectAttributes, productFilter);
        return "redirect:/admin/menu/products";
    }

    @GetMapping("/menu/products")
    public String products() {
        return "/admin/products";
    }

    @PostMapping("/menu/products/{id}/addCount")
    public String addCount(@PathVariable(ID) Long id,
                           RedirectAttributes redirectAttributes,
                           Model model, ProductFilter productFilter,
                           String addCount, String pagePR) {
        try {
            addAttributes(model, Map.of(ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM),
                    PAGE_PR, pagePR));
            productService.addCount(id, addCount);
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
        }
        redirectAttributes(redirectAttributes, productFilter);
        return "redirect:/admin/menu/productsForAdmin";
    }

    @PostMapping("/menu/products/{id}/removeCount")
    public String removeCount(@PathVariable(ID) Long id,
                              RedirectAttributes redirectAttributes,
                              Model model, ProductFilter productFilter,
                              String removeCount, String pagePR) {
        try {
            addAttributes(model, Map.of(ERRORS, Error.of(EMPTY_PARAM, EMPTY_PARAM),
                    PAGE_PR, pagePR));
            productService.removeCount(id, removeCount);
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
        }
        redirectAttributes(redirectAttributes, productFilter);
        return "redirect:/admin/menu/productsForAdmin";
    }

    @PostMapping("/menu/products/{id}/delete")
    public String deleteProduct(@PathVariable(ID) Long id,
                                ProductFilter productFilter,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes(redirectAttributes, productFilter);
        productService.deleteProduct(id);
        return "redirect:/admin/menu/productsForAdmin";
    }

    @GetMapping("/menu/promoCodesForAdmin")
    public String viewPromoCodes(Model model, PromoCodeFilter promoCodeFilter,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(name = PAGE, defaultValue = ZERO) Integer page) {
        try {
            var promoCodes = promoCodeService.getPromoCodes(promoCodeFilter, page);
            addAttributes(model, Map.of(PROMO_CODES, promoCodes));
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
        }
        ModelHelper.redirectAttributes(redirectAttributes, promoCodeFilter);
        return "redirect:/admin/menu/promoCodes";
    }

    @GetMapping("/menu/promoCodes")
    public String promoCodes() {
        return "/admin/promoCodes";
    }

    @GetMapping("/menu/promoCodes/{id}/viewInfo")
    public String promoCodeInfo(@PathVariable(ID) Long id, Model model,
                                @RequestParam(name = PAGE, defaultValue = ZERO) Integer page,
                                PromoCodeFilter promoCodeFilter,
                                RedirectAttributes redirectAttributes) {
        var promoCode = promoCodeService.getPromoCodeWithPage(id, page);
        var products = productService.getProductsForAddingPromoCode(id);
        ModelHelper.redirectAttributes(redirectAttributes, promoCodeFilter);
        addAttributes(model, Map.of(PROMO_CODE, promoCode,
                PRODUCTS_DOR_ADDING_PROMO_CODE, products));
        return "/admin/promoCodeInfo";
    }

    @PostMapping("/menu/promoCodes/{id}/viewInfo/deleteFromProduct")
    public String deleteProductFromPromoCode(@PathVariable(ID) Long id,
                                             String productId) {
        promoCodeService.deleteProductFromPromoCode(productId, id);
        return "redirect:/admin/menu/promoCodes/{id}/viewInfo";
    }

    @PostMapping("/menu/promoCodes/{id}/delete")
    public String deletePromoCode(@PathVariable(ID) Long id) {
        promoCodeService.deletePromoCode(id);
        return "redirect:/admin/menu/promoCodesForAdmin";
    }
    @PostMapping("/menu/promoCodes/{id}/viewInfo/addProduct")
    public String addProduct(@PathVariable(ID) Long id,
                             String productId) {
        promoCodeProductService.addProductToPromoCode(id, Long.valueOf(productId));
        return "redirect:/admin/menu/promoCodes/{id}/viewInfo";
    }

    @PostMapping("/menu/promoCodes/{id}/viewInfo/changeName")
    public String changeNamePromoCode(@PathVariable(ID) Long id, Model model,
                                      String newName, RedirectAttributes redirectAttributes) {
        try {
            promoCodeService.changeName(id, newName);
        } catch (ValidationException  e) {
            addAttributes(model, Map.of(PROMO_CODE_CHANGE_ERRORS, e.getErrors()));
            redirectAttributes(redirectAttributes, Map.of(NEW_NAME, newName));
            return "redirect:/admin/menu/promoCodes/{id}/viewInfo";
        }
        addAttributes(model, Map.of(PROMO_CODE_CHANGE_ERRORS, EMPTY_PARAM));
        return "redirect:/admin/menu/promoCodes/{id}/viewInfo";
    }

    @PostMapping("/menu/promoCodes/{id}/viewInfo/changeDiscount")
    public String changeDiscount(@PathVariable(ID) Long id, Model model,
                                 RedirectAttributes redirectAttributes,
                                 String newDiscount) {
        try {
            promoCodeService.changeDiscount(id, newDiscount);
        } catch (ValidationException e) {
            addAttributes(model, Map.of(PROMO_CODE_CHANGE_ERRORS, e.getErrors()));
            redirectAttributes(redirectAttributes, Map.of(NEW_DISCOUNT, newDiscount));
            return "redirect:/admin/menu/promoCodes/{id}/viewInfo";
        }
        addAttributes(model, Map.of(PROMO_CODE_CHANGE_ERRORS, EMPTY_PARAM));
        return "redirect:/admin/menu/promoCodes/{id}/viewInfo";
    }

    @PostMapping("/menu/promoCodes/{id}/viewInfo/changeStatus")
    public String changeStatus(@PathVariable(ID) Long id,
                               String newStatus) {
        promoCodeService.changeStatus(id, newStatus);
        return "redirect:/admin/menu/promoCodes/{id}/viewInfo";
    }

    @GetMapping("/menu/addPromoCodeInfo")
    public String addPromoCode(Model model,
                               CreatePromoCodeDto createPromoCodeDto,
                               RedirectAttributes redirectAttributes) {
        var products = productService.getProducts();
        addAttributes(model, Map.of(PRODUCTS, products));
        ModelHelper.redirectAttributes(redirectAttributes, createPromoCodeDto);
        return "redirect:/admin/menu/addPromoCode";
    }

    @GetMapping("/menu/addPromoCode")
    public String addPromoCode() {
        return "/admin/addPromoCode";
    }

    @PostMapping("/menu/addPromoCodeInfo")
    public String addPromoCode(CreatePromoCodeDto createPromoCodeDto,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            promoCodeService.addPromoCode(createPromoCodeDto);
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            ModelHelper.redirectAttributes(redirectAttributes, createPromoCodeDto);
        }
        return "redirect:/admin/menu";
    }

    @GetMapping("/menu/addNewProduct")
    public String addNewProduct(Model model) {
        addAttributes(model, Map.of(MODELS, modelService.getAllModels(),
                COUNTRIES, countryService.getAllCountries(),
                BRANDS, brandService.getAllBrands(),
                COLORS, colorService.getAllColors()));
        return "/admin/addNewProduct";
    }

    @PostMapping("/menu/addNewProduct")
    public String addNewProduct(CreateProductDto createProductDto,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.addNewProduct(createProductDto);
        } catch (ValidationException e) {
            addAttributes(model, Map.of(ERRORS, e.getErrors()));
            redirectAttributes(redirectAttributes, createProductDto);
            return "redirect:/admin/menu/addNewProduct";
        }
        return "redirect:/admin/menu";
    }

}




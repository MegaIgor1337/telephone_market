package market.controller;

import lombok.RequiredArgsConstructor;
import market.dto.CommentDto;
import market.dto.UserFilter;
import market.service.AddressService;
import market.service.CommentService;
import market.service.OrderService;
import market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static market.util.StringContainer.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@SessionAttributes({SIZE_MODERATE_COMMENTS, ACCESSED_COMMENTS, USER,
        PAGE_U, MODERATE_COMMENTS})
public class AdminController {
    private final CommentService commentService;
    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    @GetMapping("/menu")
    public String adminMenu() {
        return "admin/adminMenu";
    }

    @GetMapping("/menu/moderateComments")
    public String moderateComments(Model model) {
        List<CommentDto> commentsDto = commentService.getModerateComments();
        model.addAttribute(MODERATE_COMMENTS, commentsDto);
        return "admin/moderateComments";
    }

    @PostMapping("/menu/moderateComments")
    public String moderateComments(String commentId, String value, Model model) {
        if (value.equals(ACCESS)) {
            commentService.updateCommentFromModeratingToAccess(Long.valueOf(commentId));
        } else {
            commentService.deleteComment(Long.valueOf(commentId));
        }
        model.addAttribute(SIZE_MODERATE_COMMENTS, commentService.getModerateComments().size());
        return "redirect:/admin/menu/moderateComments";
    }

    @GetMapping("/menu/accessedComments")
    public String accessedComments(Model model) {
        List<CommentDto> commentsDto = commentService.getAccessedComments();
        model.addAttribute(ACCESSED_COMMENTS, commentsDto);
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
        model.addAttribute(PAGE_U, EMPTY_PARAM);
        model.addAttribute(USERS, users);
        redirectAtt(redirectAttributes, filter);
        return "/admin/users";
    }

    @PostMapping("/menu/users/{id}/delete")
    public String deleteUser(@PathVariable(ID) Long id,
                             String pageU, Model model, UserFilter filter,
                             RedirectAttributes redirectAttributes) {
        model.addAttribute(PAGE_U, pageU);
        userService.deleteUser(id);
        redirectAtt(redirectAttributes, filter);
        return "redirect:/admin/menu/users";
    }

    @GetMapping("/menu/users/{id}/profile")
    public String viewUserProfile(@PathVariable(ID) Long id,
                                  @RequestParam(name = PAGE_A, defaultValue = ZERO) Integer pageA,
                                  @RequestParam(name = PAGE_O, defaultValue = ZERO) Integer pageO,
                                  Model model, UserFilter filter, String pageU,
                                  RedirectAttributes redirectAttributes) {
        var userOrders = orderService.findAllOrdersByUserID(id, pageO);
        model.addAttribute(USER_ORDERS, userOrders);
        var addresses = addressService.getAddresses(id, pageA);
        model.addAttribute(PAGE_U, pageU);
        var user = userService.findById(id);
        model.addAttribute(USER, user);
        model.addAttribute(ADDRESSES, addresses);
        redirectAtt(redirectAttributes, filter);
        return "/admin/userProfile";
    }

    @GetMapping("/menu/users/{id}/profile/orders/{orderId}")
    public String viewOrder(@PathVariable(ID) Long id,
                            @PathVariable(ORDER_ID) Long orderId,
                            @RequestParam(name = PAGE_PR, defaultValue = ZERO) String pagePr,
                            Model model) {
        var order = orderService.findOrderById(orderId, Integer.valueOf(pagePr));
        model.addAttribute(USER_ORDER, order);
        return "/admin/userOrder";
    }

    private void redirectAtt(RedirectAttributes redirectAttributes, UserFilter filter) {
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

}

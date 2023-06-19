package market.controller.resp;


import lombok.RequiredArgsConstructor;
import market.dto.CommentDto;
import market.dto.UserDto;
import market.enums.CommentStatus;
import market.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static market.util.StringContainer.*;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
//@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {
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

    @PostMapping("/{id}/addComment")
    public CommentDto addComment(@PathVariable(ID) Long id,
                             String comment) {
        return commentService.save(comment, id);
    }

    @GetMapping("/{id}/personalComments")
    public List<CommentDto> personalComments(@PathVariable(ID) Long id) {
        return commentService.findCommentsByUserId(id);
    }

    @DeleteMapping("/{id}/personalComments/delete")
    public ResponseEntity<?> personalComments(@PathVariable(ID) Long id, String commentId) {
        return commentService.deleteComment(Long.valueOf(commentId))
                ? noContent().build()
                : notFound().build();
    }
}

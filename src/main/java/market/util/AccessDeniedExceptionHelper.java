package market.util;

import lombok.experimental.UtilityClass;
import market.dto.UserDto;
import market.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static market.util.StringContainer.EMPTY_PARAM;

@UtilityClass
public class AccessDeniedExceptionHelper {
    public static void authorizeCheck(UserService userService, Authentication authentication, Long id) {
        UserDto currentUser = userService.getCurrentUser(authentication);
        if (!currentUser.getId().equals(id)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    public static void setNewAuthentication(Authentication authentication, String newName) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            User updatedUserDetails = new User(newName, EMPTY_PARAM ,userDetails.getAuthorities());
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(updatedUserDetails,
                    authentication.getCredentials(), authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        }
    }
}

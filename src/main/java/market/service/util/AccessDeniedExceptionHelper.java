package market.service.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import market.service.dto.UserDto;
import market.service.impl.UserServiceImpl;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static market.service.util.ConstantContainer.EMPTY_PARAM;

@UtilityClass
@Slf4j
public class AccessDeniedExceptionHelper {
    public static void authorizeCheck(UserServiceImpl userService, Authentication authentication, Long id) {
        UserDto currentUser = userService.getCurrentUser(authentication);
        if (!currentUser.getId().equals(id)) {
            log.info("User dose not have access");
            throw new AccessDeniedException("Access denied");
        }
        log.info("User has access");

    }

    public static void setNewAuthentication(Authentication authentication, String newName) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                User updatedUserDetails = new User(newName, EMPTY_PARAM, userDetails.getAuthorities());
                Authentication newAuthentication = new UsernamePasswordAuthenticationToken(updatedUserDetails,
                        authentication.getCredentials(), authentication.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            }
        }
    }
}

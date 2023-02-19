package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static boolean checkIfUserIsSignedIn() {
        Authentication authentication = getAuthentication();
        if (authentication.isAuthenticated() && !(authentication == null) && !(authentication instanceof AnonymousAuthenticationToken)) {
            return true;
        }
        return false;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getSignedInUsersName() {
        return getAuthentication().getName();

    }
}

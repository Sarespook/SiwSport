package siw.uniroma3.it.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class GlobalController {
	
	@ModelAttribute("userDetails")

    public Object getUser() {
       Object user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
        	Object principal = authentication.getPrincipal();
        	
        	  if (principal instanceof UserDetails) {
                  // If the user is authenticated via form-based login
                  user = (UserDetails) principal;
              } else if (principal instanceof OAuth2User) {
                  // If the user is authenticated via OAuth2 login
                  user = (OAuth2User) principal;
              }
        }
        return user;
    }

}

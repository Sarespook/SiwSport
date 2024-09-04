package siw.uniroma3.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.model.Presidente;
import siw.uniroma3.it.model.User;
import siw.uniroma3.it.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

    @GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "register.html";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "login.html";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "index";
		}
		 Object principal = authentication.getPrincipal();
		    if (principal instanceof UserDetails) {
		        UserDetails userDetails = (UserDetails) principal;
		        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		        model.addAttribute("userDetails", userDetails);
		        if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
		            return "user/index"; // Assumes `user/index.html` is in `src/main/resources/templates/`
		        }
		        return "admin/index"; // Assumes `admin/index.html` is in `src/main/resources/templates/`
		    } else if (principal instanceof OAuth2User) {
		        OAuth2User oauth2User = (OAuth2User) principal;
		        // Handle OAuth2 user
		        // You may need to fetch or create credentials for the OAuth2 user
		        // Assuming OAuth2 users have a role attribute or some mapping
		        String role = (String) oauth2User.getAttributes().get("role"); // Adjust this as needed
		        model.addAttribute("userDetails", oauth2User);
		        if ("DEFAULT_ROLE".equals(role)) {
		            return "user/index"; // Assumes `user/index.html` is in `src/main/resources/templates/`
		        }
		        return "admin/index"; // Assumes `admin/index.html` is in `src/main/resources/templates/`
		    }
		    
		    // Fallback if neither UserDetails nor OAuth2User
		    return "index";
	}
	
	
//    @GetMapping(value = "/success")
//    public String defaultAfterLogin(Model model) {
//        
//    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//    	if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
//    		model.addAttribute("userDetails", userDetails);
//            return "/user/index.html";
//        }
//    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//    		model.addAttribute("userDetails", userDetails);
//            return "/admin/index.html";
//        }
//    	model.addAttribute("userDetails", userDetails);
//        return "index";
//    }
	
	
	
	

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        	Presidente presidente=new Presidente();
        	presidente.setNome(user.getName());
        	presidente.setCognome(user.getSurname());
        	
        	user.setPresidente(presidente);
        	    
            credentials.setUser(user);
            
            credentialsService.saveCredentials(credentials);   //idem sopra
            model.addAttribute("user", user);
          
            return "registrationSuccessful";
        	
        }
        return "register";
    }
	
}

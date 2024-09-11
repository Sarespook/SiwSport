package siw.uniroma3.it.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import siw.uniroma3.it.model.Presidente;
import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.model.User;
import siw.uniroma3.it.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Value("${upload.path-Presidente}")
    private String uploadPath;
	
	@Autowired
	private CredentialsService credentialsService;

	
	

	
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
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("userDetails", userDetails);
		if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
    		model.addAttribute("squadra",credentials.getUser().getPresidente().getSquadra());  //potrebbe essere null
			return "/user/index.html";
			}
		return "/admin/index.html";
	}
	
	
	
	
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
    		model.addAttribute("userDetails", userDetails);
    		model.addAttribute("squadra",credentials.getUser().getPresidente().getSquadra());  //e' sicuramente null
            return "/user/index.html";
        }
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		model.addAttribute("userDetails", userDetails);
            return "/admin/index.html";
        }
    	model.addAttribute("userDetails", userDetails);
        return "index";
        
    }
    

    
    
    @GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("presidente",new Presidente());
		return "register.html";
	}
    
    
    /*--------------------------------------------------------------------------------------------------------------
     * nel register avviene anche la creazione del presidente e il caricamento dell'imamagine-----------------------
     * -------------------------------------------------------------------------------------------------------------*/
    
    
	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 @ModelAttribute("presidente") Presidente presidente,
                 BindingResult presidenteBindingResult
                 ,@RequestParam("image") MultipartFile imageFile
                 ,Model model)throws IOException {
		
		

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!credentialsBindingResult.hasErrors() && !presidenteBindingResult.hasErrors()) { 
        	
        	if (!imageFile.isEmpty()) {
            	
            	// Save the image in the specified directory
                Files.createDirectories(Paths.get(uploadPath));
                String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadPath, filename);
                imageFile.transferTo(imagePath.toFile());

                // Set the URL to the image in the animal entity
                presidente.setUrlImage("/PresidenteImages/" + filename); // URL mapped to the dynamic endpoint
            }
        	
        	
        	presidente.setNome(user.getName().trim());
        	presidente.setCognome(user.getSurname().trim());
        	
        	user.setPresidente(presidente);
        	    
            credentials.setUser(user);
            
            credentialsService.saveCredentials(credentials);   //idem sopra
            model.addAttribute("user", user);
          
            return "registrationSuccessful";
        }
        return "register";
    }
	
}

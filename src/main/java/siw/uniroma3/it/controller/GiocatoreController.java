package siw.uniroma3.it.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.model.Giocatore;
import siw.uniroma3.it.service.GiocatoreService;
import siw.uniroma3.it.validation.GiocatoreValidator;

@Controller
public class GiocatoreController {
	
	@Value("${upload.path-Giocatore}")
    private String uploadPath;
	
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private GlobalController globalController;

	@Autowired
	private GiocatoreValidator giocatoreValidator;
	
	
	
	@GetMapping("/admin/formNuovoGiocatore")
	public String getFormNuovoGiocatore(Model model) {
		model.addAttribute("userDetails",this.globalController.getUser());
		model.addAttribute("giocatore",new Giocatore());
		return "/admin/formNuovoGiocatore.html";
	}
	
	@PostMapping("/admin/giocatore")
	public String nuovoGiocatore(@Valid@ModelAttribute ("giocatore")Giocatore giocatore
			,@RequestParam("image") MultipartFile imageFile
			,BindingResult giocatoreBindingResult) throws IOException {
		
		this.giocatoreValidator.validate(giocatore,giocatoreBindingResult);
		
		if(giocatoreBindingResult.hasErrors()==false) {
			
			if (!imageFile.isEmpty()) {
            	
            	// Save the image in the specified directory
                Files.createDirectories(Paths.get(uploadPath));
                String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadPath, filename);
                imageFile.transferTo(imagePath.toFile());

                // Set the URL to the image in the animal entity
                giocatore.setUrlImage("/images/" + filename); // URL mapped to the dynamic endpoint
            }
			
			giocatoreService.save(giocatore);
			return "redirect:/admin/index";
		}
		else {
			return "redirect:/admin/formNuovoGiocatore";
		}
		
		
	}
	

}

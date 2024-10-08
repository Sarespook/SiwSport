package siw.uniroma3.it.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.model.Presidente;
import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.service.CredentialsService;
import siw.uniroma3.it.service.GiocatoreService;
import siw.uniroma3.it.service.PresidenteService;
import siw.uniroma3.it.service.SquadraService;
import siw.uniroma3.it.validation.SquadraValidator;

@Controller
public class SquadraController {
	
	@Value("${upload.path-Squadra}")
    private String uploadPath;
	
	
	@Autowired
	private SquadraValidator squadraValidator;
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private PresidenteService presidenteService;
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@Autowired
	private SquadraService squadraService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@GetMapping("/squadra/{squadraId}")
	public String getSquadra(@PathVariable("squadraId")Long squadraId,Model model) {
		UserDetails userDetails=(UserDetails) this.globalController.getUser();
		if(userDetails!=null) {
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			model.addAttribute("credentials", credentials);
			model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		}
		model.addAttribute("UserDetails", this.globalController.getUser());
		Squadra squadra=this.squadraService.findById(squadraId);
		
		model.addAttribute("squadra",squadra);
		model.addAttribute("presidente",squadra.getPresidente());
		model.addAttribute("giocatori", this.giocatoreService.findAllSelectedBySquadra(squadraId));
		return "squadra.html";
	}
	
	@GetMapping("/user/squadra/{squadraId}")
	public String getSquadraPresidente(@PathVariable("squadraId")Long squadraId,Model model) {
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		Squadra squadra = this.squadraService.findById(squadraId);
		model.addAttribute("squadra",squadra);
		model.addAttribute("tesserati",squadra.getTesserati());
		model.addAttribute("presidente",squadra.getPresidente());
		return "/user/squadra.html";
	}
	
	@GetMapping("/squadre")
	public String getSquadre(Model model) {
		UserDetails userDetails=(UserDetails) this.globalController.getUser();
		if(userDetails!=null) {
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			model.addAttribute("credentials", credentials);
			model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		}
		model.addAttribute("UserDetails", this.globalController.getUser());
		
		model.addAttribute("squadre",this.squadraService.findAll());
		return "squadre.html";
	}
	
	
	/*---------------------Logica inserimento nuova squadra---------------------------------*/
	
	@GetMapping("/admin/formNuovaSquadra/{presidenteId}")
	public String getFormNuovaSquadra(@PathVariable("presidenteId")Long presidenteId,Model model){
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("squadra", new Squadra());
		model.addAttribute("presidente", this.presidenteService.findById(presidenteId));
		return "/admin/formNuovaSquadra.html";
	}
	
	@PostMapping("/squadra/{presidenteId}")
	public String nuovaSquadra(@Valid@ModelAttribute("squadra")Squadra squadra
			,BindingResult squadraBindingResult
			,@PathVariable("presidenteId")Long presidenteId
			,@RequestParam("image") MultipartFile imageFile
			,Model model)throws IOException {
		
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		this.squadraValidator.validate(squadra, squadraBindingResult);
		
		if(squadraBindingResult.hasErrors()==false) {
			
			if (!imageFile.isEmpty()) {
            	
            	// Save the image in the specified directory
                Files.createDirectories(Paths.get(uploadPath));
                String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadPath, filename);
                imageFile.transferTo(imagePath.toFile());

                // Set the URL to the image in the animal entity
                squadra.setUrlLogo("/SquadraImages/" + filename); // URL mapped to the dynamic endpoint
            }
			
			Presidente presidente=this.presidenteService.findById(presidenteId);
			
			squadra.setPresidente(presidente);
			presidente.setSquadra(squadra);
			
			this.squadraService.save(squadra);
			
			
			model.addAttribute("squadra",squadra);
			model.addAttribute("presidente",squadra.getPresidente());
			model.addAttribute("giocatori", this.giocatoreService.findAllSelectedBySquadra(squadra.getId()));
			model.addAttribute("UserDetails", this.globalController.getUser());
			return "redirect:/squadra/"+squadra.getId();
		}
		else {
			return "redirect:/admin/formNuovaSquadra/"+presidenteId;
		}
	}

}


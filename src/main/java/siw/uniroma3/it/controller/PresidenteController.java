package siw.uniroma3.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.service.CredentialsService;
import siw.uniroma3.it.service.PresidenteService;

@Controller
public class PresidenteController {
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private PresidenteService presidenteService;

	@Autowired
	private CredentialsService credentialsService;
	
	
	@GetMapping("/admin/scegliPresidentePerUnaSquadra")
	public String scegliPresidente(Model model) {
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("presidenti", this.presidenteService.findAllWithoutSquad());
		return "/admin/presidentiPerUnaSquadra.html";
	}
	
	
	@GetMapping("/admin/scegliPresidentePerUnaSquadra/{presidenteId}")
	public String confermaPresidente(@PathVariable("presidenteId")Long presidenteId,Model model) {
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		model.addAttribute("presidente", this.presidenteService.findById(presidenteId));
		return "/admin/confermaPresidentePerUnaSquadra.html";
	}
	
	@GetMapping("/admin/confermaPresidentePerUnaSquadra/{presidenteId}")
	public String aggiungiPresidente(@PathVariable("presidenteId")Long presidenteId,Model model,RedirectAttributes redirectAttributes) {
			Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
			model.addAttribute("credentials", credentials);
			model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
			model.addAttribute("squadra",new Squadra());
			model.addAttribute("presidente",this.presidenteService.findById(presidenteId));
			return "/admin/formNuovaSquadra.html";
	}
	
}

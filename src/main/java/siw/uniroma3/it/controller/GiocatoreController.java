package siw.uniroma3.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.model.Giocatore;
import siw.uniroma3.it.service.GiocatoreService;
import siw.uniroma3.it.validation.GiocatoreValidator;

@Controller
public class GiocatoreController {
	
	
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
	public String nuovoGiocatore(@Valid@ModelAttribute ("giocatore")Giocatore giocatore,
			BindingResult giocatoreBindingResult) {
		
		this.giocatoreValidator.validate(giocatore,giocatoreBindingResult);
		
		if(giocatoreBindingResult.hasErrors()==false) {
			/*parte relativa al caricamento della foto del giocatore , utilizzo dell'imageController*/
			
			giocatoreService.save(giocatore);
			return "redirect:/admin/index";
		}
		else {
			return "redirect:/admin/formNuovoGiocatore";
		}
		
		
	}
	

}

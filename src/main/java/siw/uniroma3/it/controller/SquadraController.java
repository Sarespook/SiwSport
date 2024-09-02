package siw.uniroma3.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import siw.uniroma3.it.model.Presidente;
import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.service.PresidenteService;
import siw.uniroma3.it.service.SquadraService;
import siw.uniroma3.it.validation.SquadraValidator;

@Controller
public class SquadraController {
	
	
	@Autowired
	private SquadraValidator squadraValidator;
	
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private PresidenteService presidenteService;
	
	@Autowired
	private SquadraService squadraService;
	
	
	
	@GetMapping("/admin/formNuovaSquadra")
	public String getFormNuovaSquadra(Model model){
		model.addAttribute("userDetails", this.globalController.getUser());
		return "/admin/formNuovaSquadra.html";
	}
	
	@PostMapping("/admin/squadra")
	public String nuovaSquadra(@Valid@ModelAttribute("squadra")Squadra squadra
			,@Valid@ModelAttribute("presidente")Presidente presidente
			,BindingResult squadraBindingResult
			,BindingResult presidenteBindingResult
			,Model model){
		
		this.squadraValidator.validate(squadra, squadraBindingResult);
		
		if(squadraBindingResult.hasErrors()==false) {
			/*anche qui , bisogna inserire la gestione del salvataggio e caricamento del logo della squadra*/
			
			squadra.setPresidente(presidente);
			presidente.setSquadra(squadra);
			
			this.squadraService.save(squadra);
			
			model.addAttribute("squadra",squadra);
			return "/admin/riepilogoInserimentoSquadra.html";
		}
		else {
			return "redirect:/admin/formNuovaSquadra";
		}
	}

}

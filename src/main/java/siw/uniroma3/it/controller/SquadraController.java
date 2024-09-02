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

@Controller
public class SquadraController {
	
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private PresidenteService presidenteService;
	
	@Autowired
	private SquadraService squadraService;
	
	
	
	@GetMapping("/admin/formNuovaSquadra")
	public String getFormNuovaSquadra(Model model){
		model.addAttribute("userDetails", this.globalController.getUser());
		return "/admin/scegliPresidentePerUnaSquadra.html";
	}
	
	@PostMapping("/admin/squadra")
	public String nuovaSquadra(@Valid@ModelAttribute("squadra")Squadra squadra
			,@Valid@ModelAttribute("presidente")Presidente presidente
			,BindingResult squadraBindingResult
			,BindingResult presidenteBindingResult
			,Model model){
		
	}

}

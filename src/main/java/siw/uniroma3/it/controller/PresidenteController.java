package siw.uniroma3.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import siw.uniroma3.it.model.Presidente;
import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.service.PresidenteService;
import siw.uniroma3.it.service.SquadraService;

@Controller
public class PresidenteController {
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private PresidenteService presidenteService;
	
	
	
	
//	@GetMapping("/user/index")
//	public String indexPresidente(Model model) {
//		
//	}
	
	
	
	@GetMapping("/admin/scegliPresidentePerUnaSquadra")
	public String scegliPresidente(Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("presidenti", this.presidenteService.findAllWithoutSquad());
		return "/admin/presidentiPerUnaSquadra.html";
	}
	
	
	@GetMapping("/admin/scegliPresidentePerUnaSquadra/{presidenteId}")
	public String confermaPresidente(@PathVariable("presidenteId")Long presidenteId,Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("presidente", this.presidenteService.findById(presidenteId));
		return "/admin/confermaPresidentePerUnaSquadra.html";
	}
	
	@GetMapping("/admin/confermaPresidentePerUnaSquadra/{presidenteId}")
	public String aggiungiPresidente(@PathVariable("presidenteId")Long presidenteId,Model model,RedirectAttributes redirectAttributes) {
			model.addAttribute("userDetails", this.globalController.getUser());
			Presidente presidente=this.presidenteService.findById(presidenteId);
			model.addAttribute("squadra",new Squadra());
			redirectAttributes.addFlashAttribute("presidente",presidente);
			return "redirect:/admin/formNuovaSquadra.html";
	}
	
}

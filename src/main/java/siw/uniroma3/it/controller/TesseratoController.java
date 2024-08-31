package siw.uniroma3.it.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


import siw.uniroma3.it.model.Giocatore;
import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.model.Tesserato;
import siw.uniroma3.it.service.GiocatoreService;
import siw.uniroma3.it.service.SquadraService;

@Controller
public class TesseratoController {
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private GiocatoreService giocatoreService;

	@Autowired
	private SquadraService squadraService;
	
	@GetMapping("/user/aggiungiNuovoTesserato/{squadraId}")
	public String scegliGiocatore(@PathVariable("squadraId")Long squadraId,Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("giocatori", this.giocatoreService.findAll());
		model.addAttribute("squadra", this.squadraService.findById(squadraId));
		return "/user/scegliGiocatoreComeTesserato.html";
	}
	
	@GetMapping("/user/aggiungiNuovoTesserato/{squadraId}/{giocatoreId}")
	public String ScegliNuovoTesserato(@PathVariable("squadraId")Long squadraId,@PathVariable("giocatoreId")Long giocatoreId
			,Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("giocatore",this.giocatoreService.findById(giocatoreId));
		model.addAttribute("squadra", this.squadraService.findById(squadraId));   
		return "/user/richiestaConfermaNuovoTesserato.html";
	}
	
	@GetMapping("/user/confermaNuovoTesserato/{squadraId}/{giocatoreId}")
	public String aggiungiNuovoTesserato(@PathVariable("squadraId")Long squadraId,@PathVariable("giocatoreId")Long giocatoreId
			,Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		Tesserato tesserato=new Tesserato();
		Giocatore giocatore=this.giocatoreService.findById(giocatoreId);
		Squadra squadra=this.squadraService.findById(squadraId);
		tesserato.setSquadra(squadra);
		tesserato.setGiocatore(giocatore);
		tesserato.setDataInizioTesseramento(new Date());
		
		giocatore.getTesseramenti().add(tesserato);
		squadra.getTesserati().add(tesserato);
		
		this.giocatoreService.save(giocatore);
		this.squadraService.save(squadra);
		
		return "redirect:/user/index";
	}
	
	
	

}

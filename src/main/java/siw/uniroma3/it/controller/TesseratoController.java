package siw.uniroma3.it.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import siw.uniroma3.it.model.Giocatore;
import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.model.Tesserato;
import siw.uniroma3.it.service.GiocatoreService;
import siw.uniroma3.it.service.SquadraService;
import siw.uniroma3.it.service.TesseratoService;

@Controller
public class TesseratoController {
	
	@Autowired
	private GlobalController globalController;
	
	@Autowired
	private GiocatoreService giocatoreService;

	@Autowired
	private TesseratoService tesseratoService;
	
	@Autowired
	private SquadraService squadraService;
	
	/*Logica implementativa per l'aggiunta di un tesserato in una squadra , da parte del presidente
	 * Della Squadra*/
	
	@GetMapping("/user/aggiungiNuovoTesserato/{squadraId}")
	public String scegliGiocatore(@PathVariable("squadraId")Long squadraId,Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("giocatori", this.giocatoreService.findAllNotSelected());
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
		
		giocatore.setSelezionato(true);    //forse questa verifica logica , rende superfluo il unique constraint
		giocatore.getTesseramenti().add(tesserato);
		squadra.getTesserati().add(tesserato);
		
		this.giocatoreService.save(giocatore);
		this.squadraService.save(squadra);
		
		return "redirect:/user/scegliGiocatoreComeTesserato.html";
	}
	
	
/*-------------------------------------------------------------------------------------------------
 *--------------------------------------------------------------------------------------------------*/
	
	@GetMapping("/user/scegliTesseratoDaEliminare/{squadraId}")
	public String scegliGiocatoreDaEliminare(@PathVariable("squadraId")Long squadraId,Model model) {
		model.addAttribute("userDetails",this.globalController.getUser());
		model.addAttribute("giocatori", this.giocatoreService.findAllSelected());
		model.addAttribute("squadra",this.squadraService.findById(squadraId));
		return "/user/scegliTesseratoDaEliminare.html";
	}
	
	
	@GetMapping("/user/scegliTesseratoDaEliminare/{squadraId}/{giocatoreId")
	public String confermaGiocatoreDaEliminare(@PathVariable("squadraId")Long squadraId
			,@PathVariable("giocatoreId")Long giocatoreId
			,Model model) {
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("giocatore", this.giocatoreService.findById(giocatoreId));
		model.addAttribute("squadraId", this.squadraService.findById(squadraId));
		return "/user/confermaTesseratoDaEliminare.html";
	}
	
	
	@GetMapping("/user/eliminaGiocatore/{squadraId}/{giocatoreId}")
	public String eliminaGiocatore(@PathVariable("squadraId")Long squadraId
			,@PathVariable("giocatoreId")Long giocatoreId
			,Model model) {
		Tesserato tesserato = this.tesseratoService.findGiocatoreBySquadraToCancel(giocatoreId , squadraId);
		Giocatore giocatore= this.giocatoreService.findById(giocatoreId);
		Squadra squadra=this.squadraService.findById(squadraId);
		
		tesserato.setDataFineTesseramento(new Date());
		
		giocatore.setSelezionato(false);
		
		squadra.getTesserati().remove(tesserato);
		
		this.giocatoreService.save(giocatore);
		this.squadraService.save(squadra);
		
		return "redirect:/user/scegliTesseratoDaEliminare.html";
	}

}

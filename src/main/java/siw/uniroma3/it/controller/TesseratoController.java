package siw.uniroma3.it.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.model.Giocatore;
import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.model.Tesserato;
import siw.uniroma3.it.service.CredentialsService;
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

	@Autowired
	private CredentialsService credentialsService;
	
	/*Logica implementativa per l'aggiunta di un tesserato in una squadra , da parte del presidente
	 * Della Squadra*/
	
	@GetMapping("/user/aggiungiNuovoTesserato/{squadraId}")
	public String scegliGiocatore(@PathVariable("squadraId")Long squadraId,Model model) {
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		model.addAttribute("userDetails", this.globalController.getUser());
		model.addAttribute("giocatori", this.giocatoreService.findAllNotSelected());
		model.addAttribute("squadra", this.squadraService.findById(squadraId));
		return "/user/scegliGiocatoreComeTesserato.html";
	}
	
	@GetMapping("/user/confermaNuovoTesserato/{squadraId}/{giocatoreId}")
	public String aggiungiNuovoTesserato(@PathVariable("squadraId")Long squadraId
			,@PathVariable("giocatoreId")Long giocatoreId
			,Model model) {
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		Tesserato tesserato=new Tesserato();
		Giocatore giocatore=this.giocatoreService.findById(giocatoreId);
		Squadra squadra=this.squadraService.findById(squadraId);
		
		tesserato.setNome(giocatore.getNome());
		tesserato.setCognome(giocatore.getCognome());
		tesserato.setSquadra(squadra);
		tesserato.setGiocatore(giocatore);
		tesserato.setDataInizioTesseramento(new Date());
		
		giocatore.setSelezionato(true);    //forse questa verifica logica , rende superfluo il unique constraint
		giocatore.getTesseramenti().add(tesserato);
		squadra.getTesserati().add(tesserato);
		
		
		this.tesseratoService.save(tesserato);
		
		
		model.addAttribute("giocatori", this.giocatoreService.findAllNotSelected());
		model.addAttribute("squadra", this.squadraService.findById(squadraId));
		return "/user/scegliGiocatoreComeTesserato.html";
	}
	
	
/*-------------------------------------------------------------------------------------------------
 *--------------------------------------------------------------------------------------------------*/
	
	@GetMapping("/user/eliminaGiocatore/{tesseratoId}")
	public String eliminaGiocatore(@PathVariable("tesseratoId")Long tesseratoId
			,Model model) {
		Credentials credentials = credentialsService.getCredentials(((UserDetails) this.globalController.getUser()).getUsername());
		model.addAttribute("credentials", credentials);
		model.addAttribute("presidenteUser",credentials.getUser().getPresidente());
		
		Tesserato tesserato = this.tesseratoService.findById(tesseratoId);
		Giocatore giocatore= tesserato.getGiocatore();
		Squadra squadra=tesserato.getSquadra();
		
		tesserato.setDataFineTesseramento(new Date());
		
		giocatore.setSelezionato(false);
		
		squadra.getTesserati().remove(tesserato);
		
		this.tesseratoService.save(tesserato);  
		this.giocatoreService.save(giocatore);
		this.squadraService.save(squadra);
		
		
		model.addAttribute("squadra",squadra);
		model.addAttribute("tesserati",squadra.getTesserati());
		model.addAttribute("presidente",squadra.getPresidente());
		return "/user/squadra.html";
	}

}

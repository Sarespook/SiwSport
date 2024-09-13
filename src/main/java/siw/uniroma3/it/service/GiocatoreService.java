package siw.uniroma3.it.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Giocatore;
import siw.uniroma3.it.repository.GiocatoreRepository;

@Service
public class GiocatoreService {
	
	@Autowired
	private GiocatoreRepository giocatoreRepository;
	
	public Iterable<Giocatore> findAll(){
		return this.giocatoreRepository.findAll();
	}

	public Giocatore save( Giocatore giocatore) {
		return this.giocatoreRepository.save(giocatore);
	}

	public boolean existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(String Nome
			,String Cognome
			,Date DataDiNascita
			,String LuogoDiNascita) {
		return this.giocatoreRepository.existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(Nome
				,Cognome
				,DataDiNascita
				,LuogoDiNascita);
	}

	public Giocatore findById(Long giocatoreId) {
		return this.giocatoreRepository.findById(giocatoreId).get();
	}

	public Iterable<Giocatore> findAllNotSelected() {
		return this.giocatoreRepository.findAllNotSelected();
	}

	public Iterable<Giocatore> findAllSelectedBySquadra(Long squadraId) {
		return this.giocatoreRepository.findAllSelectedBySquadra(squadraId);
	}
	

}

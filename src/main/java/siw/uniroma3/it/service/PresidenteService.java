package siw.uniroma3.it.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Presidente;
import siw.uniroma3.it.repository.PresidenteRepository;

@Service
public class PresidenteService {
	
	@Autowired
	private PresidenteRepository presidenteRepository;

	public Iterable<Presidente> findAll() {
		return this.presidenteRepository.findAll();
	}

	public Presidente findById(Long presidenteId) {
		return this.presidenteRepository.findById(presidenteId).get();
	}

	public Iterable<Presidente> findAllWithoutSquad() {
		return this.presidenteRepository.findAllWithoutSquad();
	}

	public boolean existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(String nome, String cognome, Date dataDiNascita
			,String luogoDiNascita) {
		return this.presidenteRepository.existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(nome
				,cognome
				,dataDiNascita
				,luogoDiNascita);
	}

	public void save(Presidente presidente) {
		this.presidenteRepository.save(presidente);
	}

}

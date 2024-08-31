package siw.uniroma3.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.repository.SquadraRepository;

@Service
public class SquadraService {
	
	@Autowired
	private SquadraRepository squadraRepository;

	public Squadra findById(Long squadraId) {
		return this.squadraRepository.findById(squadraId).get();
	}

	public void save(Squadra squadra) {
		this.squadraRepository.save(squadra);
	}

}

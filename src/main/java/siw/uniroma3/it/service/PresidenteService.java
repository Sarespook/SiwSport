package siw.uniroma3.it.service;

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

}

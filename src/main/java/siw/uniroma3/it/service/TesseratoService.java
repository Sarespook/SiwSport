package siw.uniroma3.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import siw.uniroma3.it.model.Tesserato;
import siw.uniroma3.it.repository.TesseratoRepository;

@Service
public class TesseratoService {
	
	@Autowired
	private TesseratoRepository tesseratoRepository;

	public Tesserato findGiocatoreBySquadraToCancel(Long giocatoreId, Long squadraId) {
		return this.tesseratoRepository.findGiocatoreBySquadraToCancel(giocatoreId,squadraId);
	}

}

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

	public Iterable<Tesserato> findAllBySquadraId(Long squadraId) {
		return this.tesseratoRepository.findAllBySquadraId(squadraId);
	}

	public void save(Tesserato tesserato) {
		this.tesseratoRepository.save(tesserato);
	}

	public Tesserato findById(Long tesseratoId) {
		return this.tesseratoRepository.findById(tesseratoId).get();
	}

}

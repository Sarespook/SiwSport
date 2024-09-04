package siw.uniroma3.it.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.it.model.Giocatore;
import siw.uniroma3.it.service.GiocatoreService;

@Component
public class GiocatoreValidator implements Validator {
	
	@Autowired
	private GiocatoreService giocatoreService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Giocatore.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Giocatore giocatore=(Giocatore) target;
		
		if(giocatore.getNome()!=null && giocatore.getCognome()!=null && giocatore.getDataDiNascita()!=null &&
				giocatore.getLuogoDiNascita()!=null) {
			boolean duplicato=this.giocatoreService.existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(giocatore.getNome()
					,giocatore.getCognome()
					,giocatore.getDataDiNascita()
					,giocatore.getLuogoDiNascita());
			if(duplicato) {
				errors.reject("giocatore.duplicate");
			}
		}
	}

}

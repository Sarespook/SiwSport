package siw.uniroma3.it.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.it.model.Squadra;
import siw.uniroma3.it.service.SquadraService;

@Component
public class SquadraValidator implements Validator {
	
	
	@Autowired
	private SquadraService squadraService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Squadra.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Squadra squadra=(Squadra) target;
		
		if(squadra.getNome()!= null && this.squadraService.existsByNome(squadra.getNome())){
				errors.reject("squadra.duplicate");
			}
		}
		
}

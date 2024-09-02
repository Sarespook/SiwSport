package siw.uniroma3.it.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.it.model.Presidente;
import siw.uniroma3.it.service.PresidenteService;



@Component
public class PresidenteValidator implements Validator{
	
	
	@Autowired
	private PresidenteService presidenteService ;

	@Override
	public boolean supports(Class<?> clazz) {
		return Presidente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Presidente presidente =(Presidente) target;
		
		if(presidente.getNome()!=null && presidente.getCognome()!=null
				&& presidente.getDataDiNascita()!=null && presidente.getLuogoDiNascita()!=null) {
			boolean duplicato=this.presidenteService.existsByNomeCognomeDataDiNascitaLuogoDiNascita(
					presidente.getNome()
					,presidente.getCognome()
					,presidente.getDataDiNascita()
					,presidente.getLuogoDiNascita());
			if(duplicato) errors.reject("presidente.duplicate");
		}
	}
	
}

package siw.uniroma3.it.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.it.model.Credentials;
import siw.uniroma3.it.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator{
	
	
	@Autowired
	private CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials=(Credentials) target;
		if(credentials.getUsername()!=null &&
				this.credentialsService.existsByUsername(credentials.getUsername())){
			errors.reject("credentials.duplicate");
			}
	}

}

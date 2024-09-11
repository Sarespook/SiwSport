package siw.uniroma3.it.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import siw.uniroma3.it.model.User;
import siw.uniroma3.it.service.UserService;

public class UserValidator implements Validator{

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user=(User) target;
		if(user.getName()!=null &&
				user.getSurname()!=null &&
				user.getEmail()!=null &&
				this.userService.existsByNameAndSurnameAndEmail(user.getName(), user.getSurname() , user.getEmail())){
			errors.reject("user.duplicate");
			}
	}

}

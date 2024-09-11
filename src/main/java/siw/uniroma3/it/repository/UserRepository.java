package siw.uniroma3.it.repository;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByEmail(String email);


	public boolean existsByNameAndSurnameAndEmail(String name, String surname, String email);

}

package siw.uniroma3.it.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

	Optional<Credentials> findByUsername(String username);

	public boolean existsByUsername(String username);

}

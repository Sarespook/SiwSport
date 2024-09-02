package siw.uniroma3.it.repository;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Squadra;

public interface SquadraRepository extends CrudRepository<Squadra, Long> {

	boolean existsByName(String nome);

}

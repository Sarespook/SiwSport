package siw.uniroma3.it.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Presidente;

public interface PresidenteRepository extends CrudRepository<Presidente, Long> {

	@Query(value="SELECT * "
	        + "FROM presidente p"
	        + "WHERE p.squadra_id IS NULL ", nativeQuery=true)
	Iterable<Presidente> findAllWithoutSquad();

	boolean existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(String nome, String cognome, Date dataDiNascita,
			String luogoDiNascita);

}

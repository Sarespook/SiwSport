package siw.uniroma3.it.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Presidente;

public interface PresidenteRepository extends CrudRepository<Presidente, Long> {

	@Query(value="SELECT * "
	        + "FROM Presidente "
	        + "WHERE p.squadraId IS NULL ", nativeQuery=true)
	Iterable<Presidente> findAllWithoutSquad();

	boolean existsByNomeCognomeDataDiNascitaLuogoDiNascita(String nome, String cognome, Date dataDiNascita,
			String luogoDiNascita);

}

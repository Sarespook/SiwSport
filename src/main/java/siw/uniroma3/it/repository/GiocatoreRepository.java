package siw.uniroma3.it.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

	public boolean existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(String Nome
			,String Cognome
			,Date dataDiNascita
			,String LuogoDiNascita);

	@Query(value="SELECT * "
	        + "FROM giocatore g"
	        + "WHERE g.selezionato IS FALSE ", nativeQuery=true)
	public Iterable<Giocatore> findAllNotSelected();

	@Query(value="SELECT * "
	        + "FROM giocatore g"
	        + "WHERE g.selezionato IS TRUE ", nativeQuery=true)
	public Iterable<Giocatore> findAllSelected();

}

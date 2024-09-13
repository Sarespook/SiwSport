package siw.uniroma3.it.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import siw.uniroma3.it.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

	public boolean existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(String Nome
			,String Cognome
			,Date dataDiNascita
			,String LuogoDiNascita);

	@Query(value="SELECT * "
	        + "FROM giocatore g "
	        + "WHERE g.selezionato IS FALSE ", nativeQuery=true)
	public Iterable<Giocatore> findAllNotSelected();

	@Query(value="SELECT g.selezionato,g.data_di_nascita,g.id,g.cognome,g.luogo_di_nascita,g.nome,g.url_image "
	        + "FROM (giocatore_tesseramenti gt JOIN squadra_tesserati st ON "
	        + "gt.tesseramenti_id=st.tesserati_id ) JOIN giocatore g ON "
	        + "g.id=gt.giocatore_id "
	        + "WHERE st.squadra_id = :squadraId ", nativeQuery=true)
	public Iterable<Giocatore> findAllSelectedBySquadra(@Param("squadraId") Long squadraId);

}

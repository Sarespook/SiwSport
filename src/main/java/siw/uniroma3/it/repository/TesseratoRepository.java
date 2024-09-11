package siw.uniroma3.it.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import siw.uniroma3.it.model.Tesserato;

public interface TesseratoRepository extends CrudRepository<Tesserato, Long> {

	
	@Query(value="SELECT * "
	        + "FROM  tesserato t"
	        + "WHERE t.giocatore_id = :giocatoreId AND"
	        + "t.squadra_id = :squadraId AND "
	        + "t.data_fine_tesseramento IS NULL ", nativeQuery=true)
	public Tesserato findGiocatoreBySquadraToCancel(@Param("giocatoreId")Long giocatoreId
			,@Param("squadraId") Long squadraId);

	
	
	@Query(value="SELECT *"
			+ "FROM tesserato t"
			+ "WHERE t.squadra_id = :squadraId" , nativeQuery=true)
	public Iterable<Tesserato> findAllBySquadraId(@Param("squadraId")Long squadraId);

}

package siw.uniroma3.it.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import siw.uniroma3.it.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

	public boolean existsByNomeAndCognomeAndDataDiNascitaAndLuogoDiNascita(String Nome
			,String Cognome
			,Date dataDiNascita
			,String LuogoDiNascita);

}

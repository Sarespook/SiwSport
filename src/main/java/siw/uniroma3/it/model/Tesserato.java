package siw.uniroma3.it.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"giocatore_id","dataInizioTesseramento","dataFineTesseramento"})})
public class Tesserato {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	
	private Date dataInizioTesseramento;
	
	private Date dataFineTesseramento;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Squadra squadra;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Giocatore giocatore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInizioTesseramento() {
		return dataInizioTesseramento;
	}

	public void setDataInizioTesseramento(Date dataInizioTesseramento) {
		this.dataInizioTesseramento = dataInizioTesseramento;
	}

	public Date getDataFineTesseramento() {
		return dataFineTesseramento;
	}

	public void setDataFineTesseramento(Date dataFineTesseramento) {
		this.dataFineTesseramento = dataFineTesseramento;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataFineTesseramento, dataInizioTesseramento, giocatore, squadra);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tesserato other = (Tesserato) obj;
		return Objects.equals(dataFineTesseramento, other.dataFineTesseramento)
				&& Objects.equals(dataInizioTesseramento, other.dataInizioTesseramento)
				&& Objects.equals(giocatore, other.giocatore) && Objects.equals(squadra, other.squadra);
	}
	
	
}

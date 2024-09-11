package siw.uniroma3.it.model;

import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(uniqueConstraints= {@UniqueConstraint(columnNames= {"giocatore_id","dataInizioTesseramento","dataFineTesseramento"})})
public class Tesserato {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;


	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataInizioTesseramento;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataFineTesseramento;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "squadra_id", nullable = false)
	private Squadra squadra;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "giocatore_id", nullable = false)
	private Giocatore giocatore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

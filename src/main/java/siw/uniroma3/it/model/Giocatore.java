package siw.uniroma3.it.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Giocatore {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataDiNascita;
	
	@NotBlank
	private String luogoDiNascita;
	
	@NotBlank
	private String ruolo;
	
	
	private String urlImage;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;
	
	
	private boolean selezionato=false;
	
	@OneToMany
	private List<Tesserato> tesseramenti;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}

	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUrlImage() {
			return urlImage;
		}
	
	public void setUrlImage(String urlImage) {
			this.urlImage = urlImage;
		}
	
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public List<Tesserato> getTesseramenti() {
		return tesseramenti;
	}

	public void setTesseramenti(List<Tesserato> tesseramenti) {
		this.tesseramenti = tesseramenti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataDiNascita, luogoDiNascita, nome, tesseramenti);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataDiNascita, other.dataDiNascita)
				&& Objects.equals(luogoDiNascita, other.luogoDiNascita) && Objects.equals(nome, other.nome)
				&& Objects.equals(tesseramenti, other.tesseramenti);
	}

	public boolean isSelezionato() {
		return selezionato;
	}

	public void setSelezionato(boolean selezionato) {
		this.selezionato = selezionato;
	}

	
	
	

}

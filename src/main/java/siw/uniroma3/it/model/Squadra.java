package siw.uniroma3.it.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Squadra {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@NotBlank
	private String nome;
	
	@NotNull
	private Long annoFondazione;
	
	@NotBlank
	private String indirizzoSede;
	
	private String urlLogo;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Presidente presidente;
	
	@OneToMany
	private List<Tesserato> tesserati;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getAnnoFondazione() {
		return annoFondazione;
	}

	public void setAnnoFondazione(Long annoFondazione) {
		this.annoFondazione = annoFondazione;
	}

	public String getIndirizzoSede() {
		return indirizzoSede;
	}

	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}
	
	public String getUrlLogo() {
			return urlLogo;
		}
	
	public void setUrlLogo(String urlLogo) {
			this.urlLogo = urlLogo;
		}
	
	public Presidente getPresidente() {
		return presidente;
	}

	public void setPresidente(Presidente presidente) {
		this.presidente = presidente;
	}

	public List<Tesserato> getTesserati() {
		return tesserati;
	}

	public void setTesserati(List<Tesserato> tesserati) {
		this.tesserati = tesserati;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoFondazione, indirizzoSede, nome, presidente, tesserati);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(annoFondazione, other.annoFondazione)
				&& Objects.equals(indirizzoSede, other.indirizzoSede) && Objects.equals(nome, other.nome)
				&& Objects.equals(presidente, other.presidente) && Objects.equals(tesserati, other.tesserati);
	}

	
	
	
	
	
	
}

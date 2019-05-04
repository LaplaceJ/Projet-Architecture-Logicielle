package ejb.entites;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Spectacle implements Serializable{
	
	@Id
	private String nomSpectacle;
	private Timestamp time;
	private double prixDeBase;
	
	@ManyToMany (fetch=FetchType.EAGER)
	private Set<Place> placeDisponibles;

	public Spectacle() {
		
	}

	public String getNomSpectacle() {
		return nomSpectacle;
	}

	public void setNomSpectacle(String nom) {
		this.nomSpectacle = nom;
	}

	

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public double getPrixDeBase() {
		return prixDeBase;
	}

	public void setPrixDeBase(double prixDeBase) {
		this.prixDeBase = prixDeBase;
	}

	public Set<Place> getPlaceDisponibles() {
		return placeDisponibles;
	}

	public void setPlaceDisponibles(Set<Place> placeDisponibles) {
		this.placeDisponibles = placeDisponibles;
	}
	
	
	
	

}

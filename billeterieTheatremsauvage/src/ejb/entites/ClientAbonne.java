package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class ClientAbonne extends Client implements Serializable  {

	private int nbAnneeFidelite ;

	
	public ClientAbonne() {
		super();
	}

	public int getNbAnneeFidelite() {
		return nbAnneeFidelite;
	}

	public void setNbAnneeFidelite(int nbAnneeFidelite) {
		this.nbAnneeFidelite = nbAnneeFidelite;
	} 
	
	
	
}

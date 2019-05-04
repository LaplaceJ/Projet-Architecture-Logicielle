package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Billet implements Serializable{
	
	@Id
	@GeneratedValue
	private int idBillet;
	private double prixBillet;
	
	@ManyToOne
	private Place placeBillet;
	
	@ManyToOne
	private Spectacle spectacle;
	
	@ManyToOne
	private Client acheteur ;
	
	
	public Billet() {
		
	}


	

	public int getIdBillet() {
		return idBillet;
	}




	public void setIdBillet(int idBillet) {
		this.idBillet = idBillet;
	}




	public Client getAcheteur() {
		return acheteur;
	}




	public void setAcheteur(Client acheteur) {
		this.acheteur = acheteur;
	}




	public double getPrixBillet() {
		return prixBillet;
	}


	public void setPrixBillet(double prixBillet) {
		this.prixBillet = prixBillet;
	}


	public Place getPlaceBillet() {
		return placeBillet;
	}


	public void setPlaceBillet(Place placeBillet) {
		this.placeBillet = placeBillet;
	}


	public Spectacle getSpectacle() {
		return spectacle;
	}


	public void setSpectacle(Spectacle spectacle) {
		this.spectacle = spectacle;
	}




	@Override
	public String toString() {
		return "Billet no:"+idBillet+" place: "+placeBillet.getIdPlace()+" prix:"+prixBillet;
		
	}
	

	
	
}

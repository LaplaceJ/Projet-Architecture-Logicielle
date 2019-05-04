package ejb.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance ( strategy =InheritanceType.TABLE_PER_CLASS) 
public abstract class Client implements Serializable{

	@Id
	protected String email;
	protected String nomClient;
	protected String prenomClient;
	
	@OneToMany(mappedBy = "acheteur", fetch=FetchType.EAGER )
	private Collection<Billet> achete;

	public Client() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nom) {
		this.nomClient = nom;
	}

	public String getPrenomClient() {
		return prenomClient;
	}

	public void setPrenomClient(String prenom) {
		this.prenomClient = prenom;
	}

	public Collection<Billet> getAchete() {
		return achete;
	}

	public void setAchete(Collection<Billet> achete) {
		this.achete = achete;
	}

	
	
	
	
	
}

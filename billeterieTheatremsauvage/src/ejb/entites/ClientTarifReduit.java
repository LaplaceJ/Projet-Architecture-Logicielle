package ejb.entites;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class ClientTarifReduit extends Client implements Serializable{

	private String typeReduction ;

	public ClientTarifReduit() {
		super();
	}

	public String getTypeReduction() {
		return typeReduction;
	}

	public void setTypeReduction(String typeReduction) {
		this.typeReduction = typeReduction;
	} 
	
	
}

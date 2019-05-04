package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Place implements Serializable{
	
	@Id
	private String idPlace;
	
	@ManyToOne
	private CategoriePlace categoriePlace;

	public Place() {
		
	}

	public String getIdPlace() {
		return idPlace;
	}

	public void setIdPlace(String idPlace) {
		this.idPlace = idPlace;
	}

	public CategoriePlace getCategoriePlace() {
		return categoriePlace;
	}

	public void setCategoriePlace(CategoriePlace categoriePlace) {
		this.categoriePlace = categoriePlace;
	}
	
	
	

}

package ejb.entites;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class CategoriePlace implements Serializable{
	
	@Id
	private String nomCat;
	private int nombrePlace;
	private String abreviation;
	private double majoration;
	
	public CategoriePlace() {
		
	}
	
	

	public String getNomCat() {
		return nomCat;
	}

	public void setNomCat(String nomCat) {
		this.nomCat = nomCat;
	}

	public int getNombrePlace() {
		return nombrePlace;
	}

	public void setNombrePlace(int nombrePlace) {
		this.nombrePlace = nombrePlace;
	}

	public String getAbreviation() {
		return abreviation;
	}

	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}

	public double getMajoration() {
		return majoration;
	}

	public void setMajoration(double majoration) {
		this.majoration = majoration;
	}



	@Override
	public String toString() {
		return "CategoriePlace [nomCat=" + nomCat + ", nombrePlace=" + nombrePlace + ", abreviation=" + abreviation
				+ ", majoration=" + majoration + "]";
	}
	

	
	
}

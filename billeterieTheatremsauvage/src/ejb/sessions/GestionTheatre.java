package ejb.sessions;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

import ejb.entites.Billet;
import ejb.entites.CategoriePlace;
import ejb.entites.Client;
import ejb.entites.Place;

public interface GestionTheatre {
	
	public void creationCategoriePlace(String nomCat ,  int nombrePlace , String abreviation ,double majoration ) throws CategoriePlaceDejaCreeeException, PlaceDejaCreeeException ; 
	public void creationSpectacle( String nom, Timestamp time, double prixDeBase) throws SpectacleDejaCreeException ; 
	public void creationClientStandard( String email, String nom, String prenom) throws ClientStandardDejaCreeException ; 
	public void creationClientAbonne( String email, String nom, String prenom, int nbAnneeFidelite) throws ClientAbonneDejaCreeException ; 
	public void creationClientTarifRed( String email, String nom, String prenom, TypeRed typeReduction) throws ClientTarifRedDejaCreeException ; 
	public void ouverturePlace(String nomCat ,String nomSpec, int n ) throws LimiteNbPlacesDisponiblesException, SpectacleInconnuCreeException, CategoriePlaceInconnuException ;
	public enum TypeRed {ETUDIANT,FAMILLE_NOMBREUSE,HANDICAP,SENIOR};
}

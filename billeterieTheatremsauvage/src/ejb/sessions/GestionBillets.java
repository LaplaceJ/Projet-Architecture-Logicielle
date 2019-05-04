package ejb.sessions;

import java.util.Collection;
import java.util.Set;

import ejb.entites.Billet;
import ejb.entites.CategoriePlace;
import ejb.entites.Client;
import ejb.entites.Spectacle;

public interface GestionBillets {
	
	
	public Collection<CategoriePlace> listerCategorie(String nomSpec) throws SpectacleInconnuException ; 
	public void achatDePlace( String mailClient,  int n  , String nomCatPlace , String nomSpec ) throws CategoriePlaceNonDisponibleException, CategoriePlaceInconnuException, SpectacleInconnuException, ClientInconnuException, PlaceInconnuException ; 
	public int nbPlaceDispo(String nomCatPlace ,String nomSpec ) throws SpectacleInconnuException, CategoriePlaceInconnuException ; 
	public Spectacle infoSpectacle(String nomSpec) throws SpectacleInconnuException;
	public Collection<Billet> infoBillets(String nomSpec , String mailClient) throws SpectacleInconnuException, ClientInconnuException;

}

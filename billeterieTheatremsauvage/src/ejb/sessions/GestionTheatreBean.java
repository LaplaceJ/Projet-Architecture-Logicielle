package ejb.sessions;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ejb.entites.CategoriePlace;
import ejb.entites.Client;
import ejb.entites.ClientAbonne;
import ejb.entites.ClientStandard;
import ejb.entites.ClientTarifReduit;
import ejb.entites.Place;
import ejb.entites.Spectacle;

@Stateless
public class GestionTheatreBean implements GestionTheatreRemote {
	
	@PersistenceContext(unitName = "billeterie")
	protected EntityManager em;
	
	@Override
	public void creationCategoriePlace(String nomCat, int nombrePlace, String abreviation, double majoration) 
			throws CategoriePlaceDejaCreeeException, PlaceDejaCreeeException {
		
		
		
		if (em.find(CategoriePlace.class, nomCat) == null) {
			

			
			//Ajout de la catégorie 
			CategoriePlace cp = new CategoriePlace();
			cp.setAbreviation(abreviation);
			cp.setMajoration(majoration);
			cp.setNombrePlace(nombrePlace);
			cp.setNomCat(nomCat);
			em.persist(cp);
			
			
			//Ajout des places de la categorie nomCat
			String nomPlace ; 
			for (int i = 1; i <= nombrePlace; i++) {
				nomPlace = abreviation + i ; 
				if (em.find(Place.class, nomPlace) != null) 
					throw new PlaceDejaCreeeException(); 
				Place p = new Place() ; 
				p.setIdPlace(nomPlace);
				p.setCategoriePlace(cp);
				em.persist(p);
			}
			
		} else {
			throw new CategoriePlaceDejaCreeeException();
		}
	}
	
	
	

	@Override
	public void creationSpectacle(String nom, Timestamp time,  double prixDeBase) throws SpectacleDejaCreeException {
		if (em.find(Spectacle.class, nom) == null) {
			Spectacle spe = new Spectacle();
			spe.setTime(time);
			spe.setNomSpectacle(nom);
			spe.setPrixDeBase(prixDeBase);
			em.persist(spe);
		} else {
			throw new SpectacleDejaCreeException();
		}
		
	}

	@Override
	public void creationClientStandard(String email, String nom, String prenom) throws ClientStandardDejaCreeException {
		if (em.find(Client.class, email) == null) {
			ClientStandard cs=new ClientStandard();
			cs.setEmail(email);
			cs.setNomClient(nom);
			cs.setPrenomClient(prenom);
			
			em.persist(cs);
		} else {
			throw new ClientStandardDejaCreeException();
		}
		
	}

	@Override
	public void creationClientAbonne(String email, String nom, String prenom, int nbAnneeFidelite) throws ClientAbonneDejaCreeException {
		if (em.find(Client.class, email) == null) {
			ClientAbonne ca =new ClientAbonne();
			ca.setEmail(email);
			ca.setNomClient(nom);
			ca.setPrenomClient(prenom);
			ca.setNbAnneeFidelite(nbAnneeFidelite);
			
			em.persist(ca);
		} else {
			throw new ClientAbonneDejaCreeException();
		}
		
	}

	@Override
	public void creationClientTarifRed(String email, String nom, String prenom, TypeRed typeReduction) throws ClientTarifRedDejaCreeException {
		String tmp = "";
		if (em.find(Client.class, email) == null) {
			ClientTarifReduit ctr =new ClientTarifReduit();
			ctr.setEmail(email);
			ctr.setNomClient(nom);
			ctr.setPrenomClient(prenom);
			
			switch(typeReduction){
			case ETUDIANT: tmp = "Etudiant";break;
			case FAMILLE_NOMBREUSE: tmp = "Famille nombreuse";break;
			case HANDICAP: tmp = "Handicap";break;
			case SENIOR: tmp ="Senior";break;
			default : tmp="Inconnu";
			}
			ctr.setTypeReduction(tmp);
			
			em.persist(ctr);
		} else {
			throw new ClientTarifRedDejaCreeException();
		}
		
	}

	@Override
	public void ouverturePlace(String nomCat, String nomSpec,int n) throws LimiteNbPlacesDisponiblesException, SpectacleInconnuCreeException, CategoriePlaceInconnuException {
		
		if(em.find(CategoriePlace.class, nomCat) == null )throw new  CategoriePlaceInconnuException() ; 
		if(em.find(Spectacle.class, nomSpec) == null )throw new  SpectacleInconnuCreeException() ; 

		
		// get spectacle 
		CategoriePlace cat = em.find(CategoriePlace.class, nomCat); 
		
		if(cat.getNombrePlace() < n ) throw new  LimiteNbPlacesDisponiblesException() ; 
		
		Spectacle spe = em.find(Spectacle.class, nomSpec);
		
		String abreviation =  cat.getAbreviation();
		Set<Place> placesDispo = spe.getPlaceDisponibles() ; 
		
		// attribution  du nombre de place 
		for(int i=1;i<=n;i++){
			placesDispo.add(em.find(Place.class, abreviation + i));
		}
		
		
		// Sauvegarde de l'attribution 
		em.persist(spe);
		
		
		
	}

}

package ejb.sessions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ejb.entites.Billet;
import ejb.entites.CategoriePlace;
import ejb.entites.Client;
import ejb.entites.ClientAbonne;
import ejb.entites.ClientTarifReduit;
import ejb.entites.Place;
import ejb.entites.Spectacle;

@Stateless
public class GestionBilletsBean implements GestionBilletsLocal,GestionBilletsRemote{

	@PersistenceContext(unitName = "billeterie")
	protected EntityManager em;


	public GestionBilletsBean() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public Collection<CategoriePlace> listerCategorie(String nomSpec) throws SpectacleInconnuException {
		Spectacle sp = em.find(Spectacle.class, nomSpec) ; 

		if (sp== null) throw new SpectacleInconnuException() ; 

		@SuppressWarnings("unchecked")
		Collection<CategoriePlace> catPlace =  (Collection<CategoriePlace>) 
		em.createQuery("SELECT DISTINCT c "
				+ "FROM Spectacle sp "
				+ "JOIN  sp.placeDisponibles places "
				+ "JOIN  places.categoriePlace c"
				+ " WHERE sp.nomSpectacle = :nomSpec").setParameter("nomSpec", nomSpec).getResultList();

		return catPlace ; 
	}

	@Override
	public int nbPlaceDispo(String nomCatPlace, String nomSpec) throws SpectacleInconnuException, CategoriePlaceInconnuException {
		CategoriePlace cp = em.find(CategoriePlace.class, nomCatPlace) ; 
		Spectacle sp = em.find(Spectacle.class, nomSpec) ; 

		if (sp== null) throw new SpectacleInconnuException() ; 
		if (cp== null) throw new CategoriePlaceInconnuException() ; 


		int  nbPlaceAchete = nbPlaceAcheter(nomCatPlace, nomSpec) ; 

		@SuppressWarnings("unchecked")
		int  nbPlaceDisponible =(int)  (long) 
		em.createQuery("SELECT DISTINCT COUNT(places.idPlace) "
				+ "FROM Spectacle sp "
				+ "JOIN  sp.placeDisponibles places "
				+ "JOIN  places.categoriePlace cp "
				+ "WHERE cp.nomCat = :nomCatPlace "
				+ "AND sp.nomSpectacle = :nomSpec ").setParameter("nomSpec", nomSpec).setParameter("nomCatPlace", nomCatPlace).getSingleResult();

		return  nbPlaceDisponible - nbPlaceAchete  ;
	}

	@Override
	public Spectacle infoSpectacle(String nomSpec) throws SpectacleInconnuException {
		Spectacle sp = em.find(Spectacle.class, nomSpec) ; 

		if (sp== null) throw new SpectacleInconnuException() ; 
		return sp;
	}

	@Override
	public void achatDePlace(String mailClient, int n, String nomCatPlace, String nomSpec) throws CategoriePlaceNonDisponibleException, CategoriePlaceInconnuException, SpectacleInconnuException ,ClientInconnuException, PlaceInconnuException{

		Client cl = em.find(Client.class, mailClient) ;
		Spectacle sp = em.find(Spectacle.class, nomSpec) ; 
		CategoriePlace cp = em.find(CategoriePlace.class, nomCatPlace) ; 

		if (cl== null) throw new ClientInconnuException() ; 
		if (sp== null) throw new SpectacleInconnuException() ; 
		if (cp== null) throw new CategoriePlaceInconnuException() ; 

		Collection<CategoriePlace> catPlace =  listerCategorie(nomSpec); 

		if( ! catPlace.contains(cp)) throw new CategoriePlaceNonDisponibleException() ;

		if(nbPlaceDispo(nomCatPlace, nomSpec) < n ) throw new CategoriePlaceNonDisponibleException() ;

		int nbPlace =  nbPlaceAcheter(nomCatPlace, nomSpec) ; 
		for (int i = nbPlace + 1 ; i <= ( nbPlace + n ); i++) {
			Billet b = new Billet() ; 
			b.setAcheteur(cl);

			Place p = em.find(Place.class, cp.getAbreviation() + i ) ; 
			if (p == null) throw new PlaceInconnuException() ; 
			b.setPlaceBillet(p);
			b.setPrixBillet(getPrixBillet(cl, sp, cp));
			b.setSpectacle(sp);
			b.setAcheteur(cl);

			em.persist(b);
		}
	}

	public int nbPlaceAcheter(String nomCatPlace, String nomSpec) throws SpectacleInconnuException, CategoriePlaceInconnuException {

		CategoriePlace cp = em.find(CategoriePlace.class, nomCatPlace) ; 
		Spectacle sp = em.find(Spectacle.class, nomSpec) ; 

		if (sp== null) throw new SpectacleInconnuException() ; 
		if (cp== null) throw new CategoriePlaceInconnuException() ; 

		@SuppressWarnings("unchecked")
		int  nbPlaceAchete =  (int) (long) 
		em.createQuery("SELECT DISTINCT COUNT(b.idBillet) "
				+ "FROM Billet b "
				+ "JOIN  b.placeBillet place "
				+ "JOIN  b.spectacle sp "
				+ "JOIN  place.categoriePlace cp "
				+ "WHERE cp.nomCat = :nomCatPlace "
				+ "AND sp.nomSpectacle = :nomSpec ").setParameter("nomSpec", nomSpec).setParameter("nomCatPlace", nomCatPlace).getSingleResult();

		return nbPlaceAchete ; 
	}

	private double getPrixBillet(Client c  , Spectacle sp , CategoriePlace cp  ){

		if(c instanceof ClientTarifReduit )return  sp.getPrixDeBase() * 0.9 + cp.getMajoration()   ; 

		if(c instanceof ClientAbonne )return  (sp.getPrixDeBase() + cp.getMajoration()) * ( (80 - Math.min(10,((ClientAbonne) c).getNbAnneeFidelite()) ) / 100.0 )   ; 

		return  sp.getPrixDeBase() + cp.getMajoration()   ; 
	}

	@Override
	public Collection<Billet> infoBillets(String nomSpec, String emailClient) throws SpectacleInconnuException, ClientInconnuException {
		Spectacle sp = em.find(Spectacle.class, nomSpec) ; 
		Client cl = em.find(Client.class, emailClient) ;

		if (sp== null) throw new SpectacleInconnuException() ;
		if (cl== null) throw new ClientInconnuException() ; 

		@SuppressWarnings("unchecked")
		Collection<Billet> billets =  (Collection<Billet>) 
		em.createQuery("SELECT DISTINCT b "
				+ "FROM Client c "
				+ "JOIN  c.achete b "
				+ "JOIN  b.spectacle sp "
				+ "WHERE c.email = :email "
				+ "AND  sp.nomSpectacle = :nomSpec ").setParameter("nomSpec", nomSpec).setParameter("email", emailClient).getResultList();

		return billets ; 
	}

}

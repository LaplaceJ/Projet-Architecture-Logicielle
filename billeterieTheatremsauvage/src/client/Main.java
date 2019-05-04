package client;

import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.entites.Billet;
import ejb.entites.CategoriePlace;
import ejb.sessions.CategoriePlaceDejaCreeeException;
import ejb.sessions.CategoriePlaceInconnuException;
import ejb.sessions.CategoriePlaceNonDisponibleException;
import ejb.sessions.ClientAbonneDejaCreeException;
import ejb.sessions.ClientInconnuException;
import ejb.sessions.ClientStandardDejaCreeException;
import ejb.sessions.ClientTarifRedDejaCreeException;
import ejb.sessions.GestionBillets;
import ejb.sessions.GestionTheatre;
import ejb.sessions.GestionTheatreRemote;
import ejb.sessions.LimiteNbPlacesDisponiblesException;
import ejb.sessions.PlaceDejaCreeeException;
import ejb.sessions.PlaceInconnuException;
import ejb.sessions.SpectacleDejaCreeException;
import ejb.sessions.SpectacleInconnuCreeException;
import ejb.sessions.SpectacleInconnuException;

public class Main {

	public static void main(String[] args) throws NamingException {

		// ******** Utilisation du service GestionThéâtre ********
		InitialContext ctx = new InitialContext();
		Object obj = ctx
				.lookup("ejb:billeterie/billeterieSessions//" + "GestionTheatreBean!ejb.sessions.GestionTheatreRemote");
		GestionTheatre gt = (GestionTheatre) obj;

		// ******** Configuration du théâtre ********
		System.out.println("******** Configuration du théâtre ********");
		try {
			System.out.println("-Creation categorie 'Balcon Droit' (abbrev: 'BD', majoration:20 , nb:30)");
			gt.creationCategoriePlace("Balcon Droit", 30, "BD", 20.0);
			System.out.println("-Creation categorie 'Balcon Gauche' (abbrev: 'BG', majoration:20 , nb:30)");
			gt.creationCategoriePlace("Balcon Gauche", 30, "BG", 20.0);
			System.out.println("-Creation categorie 'Balcon Centre' (abbrev: 'BC', majoration:25 , nb:10)");
			gt.creationCategoriePlace("Balcon Centre", 10, "BC", 25.0);
			System.out.println("-Creation categorie 'Orchestre' (abbrev: 'O', majoration:30 , nb:50)");
			gt.creationCategoriePlace("Orchestre", 50, "O", 30.0);
		} catch (CategoriePlaceDejaCreeeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (PlaceDejaCreeeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ******** Configuration des spectacles ********
		System.out.println("******** Configuration des spectacles ********");
		try {
			System.out.println("-Creation spectacle 'Springsteen Tour 2019' ('2019-03-27 19:00:00'), prixBase: 45.0,"
					+ " 30 places en Balcon Droit, 30 places en Balcon Gauche, 40 places Orchestre ");
			gt.creationSpectacle("Springsteen Tour 2019", java.sql.Timestamp.valueOf("2019-03-27 19:00:00"), 45);
			System.out.println("-Creation spectacle 'Les fourberies de Scapin' ('2019-03-28 20:30:00'), prixBase: 45.0,"
					+ " 10 places en Balcon Centre,  50 places Orchestre ");
			gt.creationSpectacle("Les fourberies de Scapin", java.sql.Timestamp.valueOf("2019-03-28 20:30:00"), 45);
		} catch (SpectacleDejaCreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ******** Configuration des places disponibles du spectacle
		// Springsteen Tour 2019 ********
		try {
			gt.ouverturePlace("Balcon Droit", "Springsteen Tour 2019", 30);
			gt.ouverturePlace("Balcon Gauche", "Springsteen Tour 2019", 30);
			gt.ouverturePlace("Orchestre", "Springsteen Tour 2019", 40);
		} catch (LimiteNbPlacesDisponiblesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SpectacleInconnuCreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CategoriePlaceInconnuException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ******** Configuration des places disponibles du spectacle Les
		// fourberies de Scapin ********
		try {
			gt.ouverturePlace("Orchestre", "Les fourberies de Scapin", 50);
			gt.ouverturePlace("Balcon Centre", "Les fourberies de Scapin", 10);
		} catch (LimiteNbPlacesDisponiblesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SpectacleInconnuCreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CategoriePlaceInconnuException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ******** Création des clients ********
		System.out.println("******** Création des clients ********");
		try {
			gt.creationClientAbonne("dupont@gmail.com", "Dupont", "Jean", 3);
			System.out.println("- Creation client abonne :'dupont@gmail.com' ( Jean Dupont , 3 ans de fidelite ) ");
			gt.creationClientTarifRed("dubois@gmail.com", "Dubois", "Paul", GestionTheatre.TypeRed.ETUDIANT);
			System.out.println("- Creation client tarif reduit :'dubois@gmail.com' ( Paul Dubois , etudiant )");
			gt.creationClientStandard("durant@gmail.com", "Durant", "Marc");
			System.out.println("- Creation client standard :'dupont@gmail.com' ( Marc Durant)");
		} catch (ClientAbonneDejaCreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientTarifRedDejaCreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientStandardDejaCreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ******** Utilisation du service GestionBillet ********
		System.out.println("******** Partie gestion des billets ********");
		Object obj2 = ctx
				.lookup("ejb:billeterie/billeterieSessions//" + "GestionBilletsBean!ejb.sessions.GestionBilletsRemote");
		GestionBillets gb = (GestionBillets) obj2;

		Collection<CategoriePlace> lcat = new ArrayList<CategoriePlace>();
		try {
			lcat = gb.listerCategorie("Les fourberies de Scapin");
		} catch (SpectacleInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Les catégories de places ouvertes pour Les fourberies de Scapin sont");
		for (CategoriePlace cat : lcat) {
			System.out.println("- " + cat.getNomCat());
		}
		try {
			System.out.println("Nbre de places disponibles en Orchestre pour Les fourberies de Scapin : "
					+ gb.nbPlaceDispo("Orchestre", "Les fourberies de Scapin"));
		} catch (SpectacleInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CategoriePlaceInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			gb.achatDePlace("dupont@gmail.com", 2, "Orchestre", "Les fourberies de Scapin");
			System.out.println(
					"client : 'dupont@gmail.com, achat de 2 places en orchestre pour Les fourberies de Scapin");
			gb.achatDePlace("dubois@gmail.com", 3, "Orchestre", "Les fourberies de Scapin");
			System.out.println(
					"client : 'dubois@gmail.com, achat de 3 places en orchestre pour Les fourberies de Scapin");
		} catch (CategoriePlaceNonDisponibleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CategoriePlaceInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpectacleInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PlaceInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collection<Billet> billets1 = new ArrayList<Billet>();
		Collection<Billet> billets2 = new ArrayList<Billet>();
		try {
			billets1 = gb.infoBillets("Les fourberies de Scapin", "dubois@gmail.com");
			billets2 = gb.infoBillets("Les fourberies de Scapin", "dupont@gmail.com");
		} catch (SpectacleInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientInconnuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Billets obtenus de dubois@gmail.com pour Les Fourberies de Scapin");
		for (Billet b : billets1) {
			System.out.println(b);
		}
		System.out.println("Billets obtenus de dupont@gmail.com pour Les Fourberies de Scapin");
		for (Billet b : billets2) {
			System.out.println(b);

		}
	}
}

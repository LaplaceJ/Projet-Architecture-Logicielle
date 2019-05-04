package web.controleurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ejb.sessions.*;
import ejb.entites.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 0
@WebServlet(value = { "reserver", "resultatReservation", "index" })
public class Controlleur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@javax.ejb.EJB
	private GestionBilletsLocal gb;

	public Controlleur() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		String maVue = "/index.jsp"; // vue par défaut

		if (url.endsWith("/index")){
			maVue = "/index.jsp";
			
			
		} else if (url.endsWith("/reserver")) {
			maVue = "/reserver.jsp";
			// Récupération des données
			String emailClient = request.getParameter("client");
			String nomSpec = request.getParameter("spectacle");
			// Attribution des données à envoyer dans la vue
			request.setAttribute("emailClient", emailClient);
			request.setAttribute("nomSpec", nomSpec);

			// Recupération du prix du spectacle

			Spectacle spec = null;
			try {
				spec = gb.infoSpectacle(nomSpec);
			} catch (SpectacleInconnuException e) {
			}

			request.setAttribute("prixDeBase", spec.getPrixDeBase());

			// Récupération des catégories de places disponible
			Collection<CategoriePlace> lcp = null;
			try {
				lcp = gb.listerCategorie(nomSpec);
			} catch (SpectacleInconnuException e) {

			}

			Collection<Integer> placeDisponible = new ArrayList<Integer>();

			request.setAttribute("lCatPlace", lcp);

			// Pour chaque catégorie de place on veut le nombre de place
			// disponible

			for (CategoriePlace cp : lcp) {
				try {
					placeDisponible.add(gb.nbPlaceDispo(cp.getNomCat(), nomSpec));
				} catch (SpectacleInconnuException e) {
					
					e.printStackTrace();
				} catch (CategoriePlaceInconnuException e) {

					e.printStackTrace();
				}
			}

			request.setAttribute("lCatPlaceDispo", placeDisponible);

		} else if (url.endsWith("resultatReservation")) {
			maVue = "/resultatReservation.jsp";

			String emailClient = request.getParameter("client");
			String nomSpec = request.getParameter("spectacle");
			String nomCat = request.getParameter("nomCat");
			String nbPlace = request.getParameter("nbPlace");
			String bool = "Commande acceptée";

			request.setAttribute("emailClient", emailClient);
			request.setAttribute("nomSpec", nomSpec);
			request.setAttribute("nomCat", nomCat);
			request.setAttribute("nbPlace", nbPlace);
			try {
				gb.achatDePlace(emailClient, Integer.parseInt(nbPlace), nomCat, nomSpec);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CategoriePlaceNonDisponibleException e) {
				bool = "Commande refusée";

			} catch (CategoriePlaceInconnuException e) {
				e.printStackTrace();
			} catch (SpectacleInconnuException e) {
				e.printStackTrace();
			} catch (ClientInconnuException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PlaceInconnuException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("bool", bool);

			List<Billet> billets = null;
			try {
				// get les info
				billets = (List<Billet>) gb.infoBillets(nomSpec, emailClient);
				Collections.sort(billets, new SortBilletComparator());
				// les mettres dans la vue
				request.setAttribute("lbillets", billets);
			} catch (ClientInconnuException e) {
				e.printStackTrace();
			} catch (SpectacleInconnuException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(maVue);
		dispatcher.forward(request, response);
	}
	
	
	private boolean testIndex(String email , String spectacle){
		
		return true  ; 
	}
}

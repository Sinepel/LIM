import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import informations.*;
import users.*;




@WebServlet("/servlet/VenteInfo")
public class VenteInfo extends HttpServlet
{
	public void service( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		Connection con;
		DataSource ds ;
		
		int userIDVendeur = Integer.parseInt(req.getParameter("userID"));
		int prixDeVente = Integer.parseInt(req.getParameter("prix"));
		//prixDeVente = (prixDeVente - 100 ) * (-1);
		int nbBonsAVendre = Integer.parseInt(req.getParameter("nbBons"));
		String date_achat = req.getParameter("date");
		int marketID = Integer.parseInt(req.getParameter("marketID"));
		int marcheInverse = Integer.parseInt(req.getParameter("inverse"));
		out.println(userIDVendeur);
		out.println(prixDeVente);
		out.println(nbBonsAVendre);
		out.println(date_achat);
		out.println(marketID);
		out.println(marcheInverse);
		
		try {
			//Récupération du POOL (LIM_POOL)
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("LIM_POOL");
			con = ds.getConnection();

			
			//Appel de la méthode pour rajouter un ordre
			try 
			{	

				int compteurOffreDispo=0;
				// SELECTION DES ORDRES QUI ONT UN PRIX INFERIEUR A CELUI OFFERT PAR L'UTILISATEUR
				Statement chercherPrix = con.createStatement();
				//ResultSet rs = chercherPrix.executeQuery("SELECT * FROM ordre WHERE (100-prix) >= "+prix+" AND id = "+marcheInverse+";"); 
				ResultSet rs = chercherPrix.executeQuery("SELECT ordre.bonsRestants, ordre.id_ordre,ordre.prix, ordre.prix as prixInverse, ordre.nbbons, ordre.date_achat, ordre.id, ordre.user_id, utilisateur.pseudo FROM ordre, utilisateur where ordre.user_id = utilisateur.user_id AND id = "+marketID+" AND prix >= "+prixDeVente+" AND bonsRestants > 0 AND etat='A' ORDER BY ordre.prix DESC"); 
				
				UserDataBean userDataBeanVendeur = new UserDataBean();
				InformationDataBean infoDB = new InformationDataBean();
				User monUserVendeur = userDataBeanVendeur.getUtilisateurId(userIDVendeur);
				int nbBonsRestants = nbBonsAVendre;
				while (rs.next() && nbBonsRestants !=0)
				{	
					UserDataBean userDataBeanAcheteur = new UserDataBean();
					User monUserAcheteur = userDataBeanAcheteur.getUtilisateur(rs.getString("pseudo"));
					//vérifier le nombre de bons proposés par l'ordre en vente et le nombre de bons souhaités par l'acheteur
					if((rs.getInt("bonsRestants") >= nbBonsRestants) && (monUserAcheteur.getEspece() >= (rs.getInt("prix")*nbBonsRestants))){
						userDataBeanVendeur.enleverBons(nbBonsRestants);
						userDataBeanAcheteur.ajouterBons(nbBonsRestants);
						userDataBeanVendeur.ajouterEspece(prixDeVente*nbBonsRestants);
						//userDataBeanVendeur.ajouterEspece(prix*nbBons);
						userDataBeanAcheteur.enleverEspece(Integer.parseInt(rs.getString("prix"))*nbBonsRestants);
						
						//Modifier le nombre de bons restants de l'ordre
						infoDB.modifOrdre(nbBonsRestants, Integer.parseInt(rs.getString("id_ordre")));
			
						//UTILISER LA SURCHARGE POUR METTRE LE NOMBRE DE BONS ET LE NOMBRE DE BONS RESTANTS A ACHETER
						infoDB.ajouterOrdre((prixDeVente-100)*(-1),nbBonsAVendre,marcheInverse,userIDVendeur,0,"V");
						nbBonsRestants = 0;
					}
					//S'il faut plusieurs ordre pour avoir le nombre de bons nécessaire.
					else if((rs.getInt("bonsRestants")<nbBonsRestants))
						{
							userDataBeanVendeur.enleverBons(Integer.parseInt(rs.getString("bonsRestants")));
							userDataBeanAcheteur.ajouterBons(Integer.parseInt(rs.getString("bonsRestants")));
							userDataBeanVendeur.ajouterEspece(prixDeVente*Integer.parseInt(rs.getString("bonsRestants")));
							userDataBeanAcheteur.enleverEspece(Integer.parseInt(rs.getString("prix"))*Integer.parseInt(rs.getString("bonsRestants")));
							infoDB.modifOrdre(Integer.parseInt(rs.getString("bonsRestants")), Integer.parseInt(rs.getString("id_ordre")));
							nbBonsRestants -= Integer.parseInt(rs.getString("bonsRestants"));

						}	
					
					//si le nombre de bons proposés est égal ou supérieur au nombre de bons souhaités alors enlever le nbre de bons, ajouter ces derniers 
					//à l'acheteur, et gérer les espèces
					out.println("\nOFFRE(S) TROUVÉE(S)");
					out.println("Le prix trouvé: "+rs.getString("prixInverse"));
					out.println("Nombre de bons à vendre: "+rs.getString("nbbons"));
					compteurOffreDispo++;
					userDataBeanAcheteur.fermerConnexion();
				} 
				
				out.println("\nCE QUE J'OFFRE");
				out.println("Prix désiré: "+prixDeVente);
				out.println("Bons désiré: "+ nbBonsAVendre);
				
				
				//GERER LE FAIT QUE MEME S'IL Y A PAS ASSEZ DE BONS POUR ACHETER ENTRE DIFFERENTS ORDRE, CREER UN ORDRE AVEC LE NOMBRE DE BONS
				//NECESSAIRE QUI RESTE A ACHETER
				
				if (nbBonsRestants > 0 && nbBonsRestants < nbBonsAVendre && compteurOffreDispo != 0 && (monUserVendeur.getEspece() >= (prixDeVente*nbBonsRestants)))
				{
					//UTILISER LA SURCHARGE POUR METTRE LE NOMBRE DE BONS ET LE NOMBRE DE BONS RESTANTS A ACHETER
					infoDB.ajouterOrdre((prixDeVente-100)*(-1),nbBonsAVendre,marcheInverse,userIDVendeur,nbBonsRestants,"V");
					res.sendRedirect(req.getHeader("Referer"));


				}
				if(compteurOffreDispo == 0 && (monUserVendeur.getEspece() >= (prixDeVente*nbBonsRestants)))
				{
					infoDB.ajouterOrdre((prixDeVente-100)*(-1),nbBonsAVendre,marcheInverse,userIDVendeur,"V");
					//Gestion de la redirection vers la page d'origine			
					res.sendRedirect(req.getHeader("Referer"));
				}
				else
				{
					//Gestion de la redirection vers la page d'origine			
					res.sendRedirect(req.getHeader("Referer"));
				}
				userDataBeanVendeur.fermerConnexion();
				infoDB.fermerConnexion();
				
			}
			catch (SQLException e){out.println("bonjour"+e.toString());}
			catch (Exception e){out.println("dfdfd"+e.toString());}
			
			
			
			
			
			
		}
		catch (NamingException e) {out.println("erere"+e.toString());} 
		catch (SQLException e) {out.println("egegeeg"+e.toString());}	
		
		
	
	}
		
	
}

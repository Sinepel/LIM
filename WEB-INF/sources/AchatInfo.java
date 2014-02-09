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




@WebServlet("/servlet/AchatInfo")
public class AchatInfo extends HttpServlet
{
	public void service( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		Connection con;
		DataSource ds ;
		
		int userID = Integer.parseInt(req.getParameter("userID"));
		int prix = Integer.parseInt(req.getParameter("prix"));
		int nbBons = Integer.parseInt(req.getParameter("nbBons"));
		String date_achat = req.getParameter("date");
		int marketID = Integer.parseInt(req.getParameter("marketID"));
		int marcheInverse = Integer.parseInt(req.getParameter("inverse"));
		out.println(userID);
		out.println(prix);
		out.println(nbBons);
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

				/* AJOUTER UNE COLONNE BONS_RESTANTS DANS LA TABLE ORDRE POUR CONNAITRE LE NB DE BONS RESTANTS
				 * APRES UN ACHAT ET AFFICHER CEUX LA AU LIEU DES BONS DE BASE.
				 * 
				 * BDD: ALTER TABLE ordre ADD COLUMN bonsRestants INT;
				 * 
				 * Si il y a un ordre inverse qui correspond au prix, modifiez le nombre de bons et d'espece des deux joueurs.
				 * Sinon créer l'ordre.
				 * 
				 * */
				 int compteurOffreDispo=0;
				// SELECTION DES ORDRES QUI ONT UN PRIX INFERIEUR A CELUI OFFERT PAR L'UTILISATEUR
				Statement chercherPrix = con.createStatement();
				//ResultSet rs = chercherPrix.executeQuery("SELECT * FROM ordre WHERE (100-prix) >= "+prix+" AND id = "+marcheInverse+";"); 
				ResultSet rs = chercherPrix.executeQuery("SELECT ordre.bonsRestants, ordre.id_ordre,ordre.prix, 100 - ordre.prix as prixInverse, ordre.nbbons, ordre.date_achat, ordre.id, ordre.user_id, utilisateur.pseudo, ordre.etat FROM ordre, utilisateur where ordre.user_id = utilisateur.user_id AND id = "+marcheInverse+" AND 100 - prix <= "+prix+" AND bonsRestants > 0 and ordre.user_id <> "+userID+" ORDER BY ordre.prix DESC"); 
				
				UserDataBean userDataBeanAcheteur = new UserDataBean();
				InformationDataBean infoDB = new InformationDataBean();
				User monUserAcheteur = userDataBeanAcheteur.getUtilisateurId(userID);
				int nbBonsRestants = nbBons;
				while (rs.next() && nbBonsRestants != 0)
				{	
					UserDataBean userDataBeanVendeur = new UserDataBean();
					User monUserVendeur = userDataBeanVendeur.getUtilisateur(rs.getString("pseudo"));
					//vérifier le nombre de bons proposés par l'ordre en vente et le nombre de bons souhaités par l'acheteur
					if((rs.getInt("bonsRestants") >= nbBonsRestants) && (monUserAcheteur.getEspece() >= (rs.getInt("prix")*nbBonsRestants))){
						userDataBeanAcheteur.ajouterBons(nbBonsRestants);
												
						if(!rs.getString("etat").equals("V"))
						{
							userDataBeanVendeur.enleverEspece(Integer.parseInt(rs.getString("prix"))*nbBonsRestants);
							userDataBeanVendeur.ajouterBons(nbBonsRestants);

						}
						else
						{
							userDataBeanVendeur.ajouterEspece(Integer.parseInt(rs.getString("prixInverse"))*nbBonsRestants);
							userDataBeanVendeur.enleverBons(nbBonsRestants);
						}
						userDataBeanAcheteur.enleverEspece(Integer.parseInt(rs.getString("prixInverse"))*nbBonsRestants);
						//userDataBeanVendeur.ajouterEspece(prix*nbBons);
						
						//Modifier le nombre de bons restants de l'ordre
						infoDB.modifOrdre(nbBonsRestants, Integer.parseInt(rs.getString("id_ordre")));
			
						//UTILISER LA SURCHARGE POUR METTRE LE NOMBRE DE BONS ET LE NOMBRE DE BONS RESTANTS A ACHETER
						infoDB.ajouterOrdre(prix,nbBons,marketID,userID,0,"A");
						nbBonsRestants = 0;
					}
					//S'il faut plusieurs ordre pour avoir le nombre de bons nécessaire.
					else if( (rs.getInt("bonsRestants")<nbBonsRestants) && (monUserAcheteur.getEspece() >= (rs.getInt("prix")*nbBonsRestants)))
						{
							userDataBeanAcheteur.ajouterBons(Integer.parseInt(rs.getString("bonsRestants")));
							if(!rs.getString("etat").equals("V"))
							{
								userDataBeanVendeur.ajouterBons(Integer.parseInt(rs.getString("bonsRestants")));
								userDataBeanVendeur.enleverEspece(Integer.parseInt(rs.getString("prix"))*Integer.parseInt(rs.getString("bonsRestants")));

							}
							else
							{
								userDataBeanVendeur.ajouterEspece(Integer.parseInt(rs.getString("prixInverse"))*Integer.parseInt(rs.getString("bonsRestants")));
								userDataBeanVendeur.enleverBons(Integer.parseInt(rs.getString("bonsRestants")));
							}
							userDataBeanAcheteur.enleverEspece(Integer.parseInt(rs.getString("prixInverse"))*Integer.parseInt(rs.getString("bonsRestants")));
							infoDB.modifOrdre(Integer.parseInt(rs.getString("bonsRestants")), Integer.parseInt(rs.getString("id_ordre")));
							nbBonsRestants -= Integer.parseInt(rs.getString("bonsRestants"));

						}	
					
					//si le nombre de bons proposés est égal ou supérieur au nombre de bons souhaités alors enlever le nbre de bons, ajouter ces derniers 
					//à l'acheteur, et gérer les espèces
					out.println("\nOFFRE(S) TROUVÉE(S)");
					out.println("Le prix trouvé: "+rs.getString("prixInverse"));
					out.println("Nombre de bons à vendre: "+rs.getString("nbbons"));
					compteurOffreDispo++;
					userDataBeanVendeur.fermerConnexion();
				} 
				
				out.println("\nDEMANDE");
				out.println("Prix désiré: "+prix);
				out.println("Bons désiré: "+ nbBons);
				
				
				//GERER LE FAIT QUE MEME S'IL Y A PAS ASSEZ DE BONS POUR ACHETER ENTRE DIFFERENTS ORDRE, CREER UN ORDRE AVEC LE NOMBRE DE BONS
				//NECESSAIRE QUI RESTE A ACHETER
				
				if (nbBonsRestants > 0 && nbBonsRestants < nbBons && compteurOffreDispo != 0 && (monUserAcheteur.getEspece() >= (prix*nbBonsRestants)))
				{
					//UTILISER LA SURCHARGE POUR METTRE LE NOMBRE DE BONS ET LE NOMBRE DE BONS RESTANTS A ACHETER
					infoDB.ajouterOrdre(prix,nbBons,marketID,userID,nbBonsRestants,"A");
					res.sendRedirect(req.getHeader("Referer"));


				}
				if(compteurOffreDispo == 0 && (monUserAcheteur.getEspece() >= (prix*nbBonsRestants)))
				{
					infoDB.ajouterOrdre(prix,nbBons,marketID,userID,"A");
					//Gestion de la redirection vers la page d'origine			
					res.sendRedirect(req.getHeader("Referer"));
				}
				else
				{
					//Gestion de la redirection vers la page d'origine			
					res.sendRedirect(req.getHeader("Referer"));
				}
				userDataBeanAcheteur.fermerConnexion();
				infoDB.fermerConnexion();
				
			}
			catch (SQLException e){out.println("bonjour"+e.toString());}
			catch (Exception e){out.println("dfdfd"+e.toString());}
			
			
			
			
			
			
		}
		catch (NamingException e) {out.println("erere"+e.toString());} 
		catch (SQLException e) {out.println("egegeeg"+e.toString());}	
		
		
	
	}
		
	
}

import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import informations.*;




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
				 
				// SELECTION DES ORDRES QUI ONT UN PRIX INFERIEUR A CELUI OFFERT PAR L'UTILISATEUR
				Statement chercherPrix = con.createStatement();
				//ResultSet rs = chercherPrix.executeQuery("SELECT * FROM ordre WHERE (100-prix) >= "+prix+" AND id = "+marcheInverse+";"); 
				ResultSet rs = chercherPrix.executeQuery("SELECT ordre.id_ordre, 100 - ordre.prix as prix, ordre.nbbons, ordre.date_achat, ordre.id, ordre.user_id, utilisateur.pseudo FROM ordre, utilisateur where ordre.user_id = utilisateur.user_id AND id = "+marcheInverse+" AND 100 - prix <= "+prix+" ORDER BY ordre.prix ASC"); 
				while (rs.next())
				{
					out.println("\nLe prix trouvé: "+rs.getString("prix"));
					out.println("\nNombre de bons à vendre: "+rs.getString("nbbons"));
				} 
				
				InformationDataBean infoDB = new InformationDataBean();
				//infoDB.ajouterOrdre(prix,nbBons,marketID,userID);
				
				
				
			}
			catch (SQLException e){out.println("bonjour"+e.toString());}
			catch (Exception e){out.println("dfdfd"+e.toString());}
			
			//Gestion de la redirection vers la page d'origine			
			//res.sendRedirect(req.getHeader("Referer"));
			
			
			
			
		}
		catch (NamingException e) {out.println("erere"+e.toString());} 
		catch (SQLException e) {out.println("egegeeg"+e.toString());}	
		
		
	
	}
		
	
}

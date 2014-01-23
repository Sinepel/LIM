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
		out.println(userID);
		out.println(prix);
		out.println(nbBons);
		out.println(date_achat);
		out.println(marketID);
		
		try {
			//Récupération du POOL (LIM_POOL)
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("LIM_POOL");
			con = ds.getConnection();

			
			//Appel de la méthode pour rajouter un ordre
			try 
			{	

				InformationDataBean infoDB = new InformationDataBean();
				infoDB.ajouterOrdre(prix,nbBons,date_achat,marketID,userID);
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

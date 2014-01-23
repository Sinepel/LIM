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
		
		String userID = req.getParameter("userID");
		int prix = Integer.parseInt(req.getParameter("prix"));
		int nbBons = Integer.parseInt(req.getParameter("nbBons"));
		String date_achat ; //TODO
		String marketID = req.getParameter("marketID");
		
		try {
			//Récupération du POOL (LIM_POOL)
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("LIM_POOL");
			con = ds.getConnection();

			
			//Appel de la méthode pour rajouter un ordre
			
			
			//Gestion de la redirection vers la page d'origine			
			res.sendRedirect(req.getContextPath()+"/"+req.getHeader("Referer"));
			
			
			con.close();	
			
		}
		catch (NamingException e) {out.println(e.toString());} 
		catch (SQLException e) {out.println(e.toString());}	
		
		
	
	}
		
	
}

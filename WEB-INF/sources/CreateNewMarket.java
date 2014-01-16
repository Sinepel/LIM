import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;




@WebServlet("/servlet/CreateNewMarket")
public class CreateNewMarket extends HttpServlet
{
	public void service( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		PrintWriter out = res.getWriter();
		
		try {
		//Récupération du POOL (LIM_POOL)
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("LIM_POOL");
		Connection con = ds.getConnection();

		//Préparation de la requete
		Statement stmt= con.createStatement();
		//PreparedStatement preparedStatement = con.prepareStatement("Select * from information INNER JOIN categorie ON information.id_categorie = categorie.id_categorie ORDER BY ? ? LIMIT 10;");

		}

		catch (NamingException e) {} 
		catch (SQLException e) {}	
		
		String information = req.getParameter("information");
		String inverse = req.getParameter("inverse");
		String date = req.getParameter("date");
		
		out.println(information + inverse + date);
	}
		
	
}


import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;




@WebServlet("/servlet/NewCat")
public class NewCat extends HttpServlet
{
	public void doPost( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		PreparedStatement preparedStatement;
	
		Connection con;
		DataSource ds;
		String categorie = req.getParameter("categorie");
		out.println(categorie);

		
		try {
			//Récupération du POOL (LIM_POOL)
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("LIM_POOL");
			con = ds.getConnection();

			//Préparation de la requete
				
			String sql = "INSERT INTO categorie (libelle) VALUES (?);";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1,categorie);
			preparedStatement.executeUpdate();

			
			res.sendRedirect(req.getHeader("referer"));
			
			preparedStatement.close(); con.close();		
			
		}

		catch (NamingException e) {out.println(e.toString());} 
		catch (SQLException e) {out.println(e.toString());}	
		
		
	
	}
		
	
}


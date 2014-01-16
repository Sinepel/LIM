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
	public void doPost( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		PrintWriter out = res.getWriter();
		PreparedStatement preparedStatement;
		Connection con;
		DataSource ds ;
		
		try {
		//Récupération du POOL (LIM_POOL)
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		ds = (DataSource) envCtx.lookup("LIM_POOL");
		con = ds.getConnection();

		//Préparation de la requete
		//Statement stmt= con.createStatement();
		String sql = "INSERT INTO information (question,echeance) VALUES (?,?);";
		preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		String information = req.getParameter("information");
		String inverse = req.getParameter("inverse");
		String dateString = req.getParameter("date");
		
		out.println(information + inverse + dateString);
		out.println("COUCOU");
		
		
		preparedStatement.setString(1, information);
		java.sql.Date date = java.sql.Date.valueOf(dateString);
		preparedStatement.setDate(2, date);
		
		out.println(preparedStatement.toString());
		preparedStatement.executeUpdate();
		
		ResultSet rs = preparedStatement.getGeneratedKeys();
		if (rs.next())
		{
		int key2 = rs.getInt(1);
		out.println("La clé: "+key2);
		}

		}

		catch (NamingException e) {out.println(e.toString());} 
		catch (SQLException e) {out.println(e.toString());}	
		
		
	
	}
		
	
}


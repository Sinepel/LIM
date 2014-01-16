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
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		PreparedStatement preparedStatement;
		PreparedStatement preparedStatementInverse;
		PreparedStatement prepatedStatementUpdate;
		Connection con;
		DataSource ds ;
		
		try {
			//Récupération du POOL (LIM_POOL)
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("LIM_POOL");
			con = ds.getConnection();

			//Préparation de la requete
			
			String sql = "INSERT INTO information (question,echeance) VALUES (?,?);";
			String sqlInverse = "INSERT INTO information (question,echeance,id_1) VALUES (?,?,?);";
			String sqlUpdate = "UPDATE information SET id_1 = ? WHERE id= ?";
			preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatementInverse = con.prepareStatement(sqlInverse, Statement.RETURN_GENERATED_KEYS);
			prepatedStatementUpdate = con.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
			
			String information = req.getParameter("information");
			String inverse = req.getParameter("inverse");
			String dateString = req.getParameter("date");
			
			out.println(information + inverse + dateString);
					
			//GESTION DE LA PREMIERE REQUETE
			preparedStatement.setString(1, information);
			java.sql.Date date = java.sql.Date.valueOf(dateString);
			preparedStatement.setDate(2, date);
			
			out.println(preparedStatement.toString());
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			int key=0;
				if (rs.next())
				{
					key = rs.getInt(1);
					out.println("La clé: "+key);
				}
			
			//GESTION DE LA SECONDE REQUETE
			preparedStatementInverse.setString(1,inverse);
			preparedStatementInverse.setDate(2, date);
			preparedStatementInverse.setInt(3,key);
			out.println(preparedStatementInverse.toString());
			preparedStatementInverse.executeUpdate();
			rs = preparedStatementInverse.getGeneratedKeys();
			int key2 = 0;
			if (rs.next())
				{
					key2 = rs.getInt(1);
					out.println("La clé: "+key2);
				}
			// GESTION DE L'UPDATE DE LA PREMIERE REQUETE POUR LUI METTRE SON ID INVERSE
			prepatedStatementUpdate.setInt(1, key2);
			prepatedStatementUpdate.setInt(2, key);
 
			out.println(prepatedStatementUpdate.toString());
			prepatedStatementUpdate.executeUpdate();	
			
			//Gestion de la redirection vers la page d'origine
			
			res.sendRedirect(req.getContextPath()+"?add=ok");		
			
		}

		catch (NamingException e) {out.println(e.toString());} 
		catch (SQLException e) {out.println(e.toString());}	
		
		
	
	}
		
	
}


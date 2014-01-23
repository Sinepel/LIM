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
		
		try {
			//Récupération du POOL (LIM_POOL)
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("LIM_POOL");
			con = ds.getConnection();

			
			
			//Gestion de la redirection vers la page d'origine			
			res.sendRedirect(req.getContextPath()+"/"+req.getHeader("Referer"));
			
			
			con.close();	
			
		}
		catch (NamingException e) {out.println(e.toString());} 
		catch (SQLException e) {out.println(e.toString());}	
		
		
	
	}
		
	
}

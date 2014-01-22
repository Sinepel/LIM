import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import users.UserDataBean;




@WebServlet("/servlet/CreateUser")
public class CreateUser extends HttpServlet
{
	public void doPost( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		try{
			String pseudo = req.getParameter("firstname");
			String mail = req.getParameter("email");
			String mdp = req.getParameter("password");
			UserDataBean monUserDataBean = null;
			
			try {
				monUserDataBean = new UserDataBean();
			}
			catch (Exception E) {}
			monUserDataBean.ajouterUtilisateur(pseudo,mdp,mail);
			monUserDataBean.fermerConnexion();
			
			res.sendRedirect(req.getContextPath());		
		}
		catch (SQLException e) {}	
		 
	
	}
		
	
}

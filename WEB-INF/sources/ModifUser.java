import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import users.UserDataBean;
import users.User;

@WebServlet("/servlet/ModifUser")
public class ModifUser extends HttpServlet
{
	public void doPost( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
			String pseudo = req.getParameter("usernameHidden");
			String mail = req.getParameter("email");
			String mdp = req.getParameter("password");
			
			try {
				UserDataBean monUserDataBean = new UserDataBean();
				User monUser = monUserDataBean.getUtilisateur(pseudo);
				monUserDataBean.modifierUtilisateur(pseudo,mail,mdp);
				monUserDataBean.fermerConnexion();
			}
			catch (Exception E) {
				out.println(E.toString());
			}
			
			res.sendRedirect(req.getContextPath());		
	}	
	
}

import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import users.UserDataBean;

@WebServlet("/servlet/ModifUser")
public class ModifUser extends HttpServlet
{
	public void doPost( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
			String mail = req.getParameter("email");
			String mdp = req.getParameter("password");
			UserDataBean monUserDataBean = null;
			
			try {
				monUserDataBean = new UserDataBean();
				monUserDataBean.modifierUtilisateur(mail,mdp);
				monUserDataBean.fermerConnexion();
			}
			catch (Exception E) {
				out.println(E.toString());
				}
			
			//res.sendRedirect(req.getContextPath());		
	}	
	
}

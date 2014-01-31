import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import informations.*;

@WebServlet("/servlet/Final")
public class Final extends HttpServlet
{
	public void doPost( HttpServletRequest req, HttpServletResponse res ) 
		throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
			int marketId = Integer.parseInt(req.getParameter("marketID"));
			int inverse = Integer.parseInt(req.getParameter("inverse"));
			
			try {
				InformationDataBean monInfoDataBean = new InformationDataBean();
				monInfoDataBean.finalisationInformation(marketId,inverse);
				
				monInfoDataBean.fermerConnexion();
			}
			catch (Exception E) {
				out.println(E.toString());
			}
			
			res.sendRedirect(req.getContextPath());		
	}	
	
}

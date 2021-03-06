import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

@WebServlet("/Graphique")
public class Graphique extends HttpServlet
{
	public void service( HttpServletRequest req, HttpServletResponse res )
		throws ServletException, IOException
	{
		try {
			if(	req.getParameter("id")!=null && !req.getParameter("id").equals("") && !req.getParameter("id").equals("0") ) 
			{

				String id 		= 	req.getParameter("id");
			
				String retour	= 	"";
				
			    Context initCtx = 	new InitialContext();
			    Context envCtx 	= 	(Context) initCtx.lookup("java:comp/env");
			    DataSource ds 	= 	(DataSource) envCtx.lookup("LIM_POOL");
			    Connection con 	= 	ds.getConnection();

				Statement st 	= 	con.createStatement();
				Statement upST 	= 	con.createStatement();
				ResultSet rs 	= 	st.executeQuery("SELECT SUM(prix * nbbons) / SUM(nbbons) AS total, to_char(date_achat, 'YYYY-MM-DD') AS date FROM ordre WHERE id=" + id + " GROUP BY date_achat ORDER BY date_achat ASC;");
				
				retour 			+= 	"[";
				while(rs.next()) {
					retour 		+= "{ \"jour\": \"" + rs.getString("date") + "\", \"valeur\": \"" + ((double)Math.round(rs.getDouble("total") * 100) / 100) + "\" },";
				}
				if(retour.charAt(retour.length()-1) == ',')
					retour 		= retour.substring(0, retour.length()-1);
				retour			+= "]";

				res.getWriter().println(retour);

				con.close();
			}
		} catch(Exception e) {}
	}
}

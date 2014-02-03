import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

@WebServlet("/GraphiqueOrdresJour")
public class GraphiqueOrdresJour extends HttpServlet
{
	public void service( HttpServletRequest req, HttpServletResponse res )
		throws ServletException, IOException
	{
		try {
			

			
				String retour	= 	"";
				
			    Context initCtx = 	new InitialContext();
			    Context envCtx 	= 	(Context) initCtx.lookup("java:comp/env");
			    DataSource ds 	= 	(DataSource) envCtx.lookup("LIM_POOL");
			    Connection con 	= 	ds.getConnection();

				Statement st 	= 	con.createStatement();
				Statement upST 	= 	con.createStatement();
				ResultSet rs 	= 	st.executeQuery("select count(*) as total, to_char(date_achat, 'YYYY-MM-DD') AS date from ordre WHERE date(now()) - date(date_achat) <= 7 group by date ORDER BY date ASC;");
				
				retour 			+= 	"[";
				while(rs.next()) {
					retour 		+= "{ \"jour\": \"" + rs.getString("date") + "\", \"valeur\": \"" + rs.getInt("total") + "\" },";
				}
				if(retour.charAt(retour.length()-1) == ',')
					retour 		= retour.substring(0, retour.length()-1);
				retour			+= "]";

				res.getWriter().println(retour);

				con.close();
			
		} catch(Exception e) {}
	}
}

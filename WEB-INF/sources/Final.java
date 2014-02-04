import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import informations.*;
//IMPORT MAIL
import java.util.Properties; 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
				
				//La méthode prend les gagnants et leur rajoute 100*NbBons qu'ils ont acquis dans cette info.
				//Elle renvoie ensuite une HashMap avec les emails et les especes
				HashMap<InternetAddress, Integer> hashMap =monInfoDataBean.finalisationInformation(marketId,inverse);
				//Récupération des emails qui sont les clés du HashMap dans un HashSet qu'on peut parcourir avec un itérateur
				Set<InternetAddress> setEmail = hashMap.keySet();
				
				//Création du tableau de destinataires to[]
				InternetAddress[] to = setEmail.toArray(new InternetAddress[setEmail.size()]);
				
				//Affichage test du tableau
				for (int i = 0; i<to.length; i++)
				{
					out.println(to[i]);
				}
				
				
				//MAIL
				final String username = "constantin.boulanger@gmail.com";
				final String password = "constantin";
		 
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
		 
				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		 
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("no-reply@LIM.fr"));
					message.setRecipients(Message.RecipientType.TO,to);
					message.setSubject("Vous avez gagné - Lille Information Market");
					message.setText("Cher utilisateur,"
						+ "\n\nVous venez de gagner un des marché auxquels vous avez participé."
						+" \nNous vous avons donc crédité vos espèces en conséquence (100 x le nombre de bons acquis pour cette information)"
						+" \n\nVous pouvez voir le récapitulatif du marché à cette adresse : http://localhost:8080/LIM/market.jsp?id="+marketId+"");
		 
					Transport.send(message);
		 
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
				

			
				
				monInfoDataBean.fermerConnexion();
			}
			catch (Exception E) {
				out.println(E.toString());
			}
			
			res.sendRedirect(req.getHeader("referer"));	
	}	
	
}

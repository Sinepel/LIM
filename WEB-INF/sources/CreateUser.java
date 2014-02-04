import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import users.UserDataBean;
//IMPORT MAIL
import java.util.Properties; 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




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
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mail));
					message.setSubject("Inscription - Lille Information Market");
					message.setText("Cher utilisateur,"
						+ "\n\nVous venez de vous inscrire sur le Lille Information Market."
						+" \nVoici vos identifiants: "
						+" \nPseudo: "+pseudo
						+" \nMot de passe: "+mdp
						+" \nAdresse mail: "+mail
						+" \n\nL'équipe Lille Information Market.");
		 
					Transport.send(message);
		 
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			
			res.sendRedirect(req.getContextPath());		
		}
		catch (SQLException e) {}	
		 
	
	}
		
	
}

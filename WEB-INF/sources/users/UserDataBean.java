package users;
//compile :javac -sourcepath sources/ -cp classes/ -d classes/ sources/users/UserDataBean.java 

import java.sql.*;
import java.util.*;
import java.net.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDataBean{
	private Connection con;
	private PreparedStatement getUser;
	private PreparedStatement setBonUser;
	private User monUser;
	
	public UserDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","postgres","postgres");
		getUser = con.prepareStatement("SELECT pseudo, espece, bons, role FROM utilisateur WHERE pseudo = ?;");
		setBonUser = con.prepareStatement("UPDATE utilisateur SET bons = bons + ? WHERE pseudo = ?;");
	}
	
	public User getUtilisateur(String pseudo) throws SQLException{
		User monUtilisateur = new User();
		
		getUser.setString(1, pseudo);
		ResultSet rs = getUser.executeQuery(); 
		
		while(rs.next()){
			monUtilisateur.setPseudo(rs.getString("pseudo"));
			monUtilisateur.setRole(rs.getString("role"));
			monUtilisateur.setEspece(rs.getInt("espece"));
			monUtilisateur.setBons(rs.getInt("bons"));
		}
		monUser = monUtilisateur;
		return monUtilisateur;
	}
	
	public void ajouterBons(int nbBons) throws SQLException{
		String pseudo = monUser.getPseudo();
		setBonUser.setInt(1, nbBons);
		setBonUser.setString(2, pseudo);
		setBonUser.executeUpdate();
		
	}
	
	protected void finalize() {
	// attempt to close database connection
	try {
		getUser.close();
		con.close();
		}

	// process SQLException on close operation
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}	
	}
	
	public void fermerConnexion() {
		this.finalize();
	}

	
	
}



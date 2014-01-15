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
	
	public UserDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","postgres","postgres");
		getUser = con.prepareStatement("SELECT pseudo, espece, bons, role FROM utilisateur WHERE pseudo = ?");
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
		return monUtilisateur;
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
	

	
	
}



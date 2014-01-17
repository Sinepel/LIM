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
	private PreparedStatement setAjouterBon;
	private PreparedStatement setEnleverBon;
	private PreparedStatement setAjouterEspece;
	private PreparedStatement setEnleverEspece;
	private User monUser;
	
	public UserDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","postgres","postgres");
		getUser = con.prepareStatement("SELECT user_id, pseudo, espece, bons, role FROM utilisateur WHERE pseudo = ?;");
		setAjouterBon = con.prepareStatement("UPDATE utilisateur SET bons = bons + ? WHERE pseudo = ?;");
		setEnleverBon = con.prepareStatement("UPDATE utilisateur SET bons = bons - ? WHERE pseudo = ?;");
		setAjouterEspece = con.prepareStatement("UPDATE utilisateur SET espece = espece + ? WHERE pseudo = ?;");
		setEnleverEspece = con.prepareStatement("UPDATE utilisateur SET espece = espece - ? WHERE pseudo = ?;");
	}
	
	public User getUtilisateur(String pseudo) throws SQLException{
		User monUtilisateur = new User();
		
		getUser.setString(1, pseudo);
		ResultSet rs = getUser.executeQuery(); 
		
		while(rs.next()){
			monUtilisateur.setId(rs.getInt("user_id"));
			monUtilisateur.setPseudo(rs.getString("pseudo"));
			monUtilisateur.setRole(rs.getString("role"));
			monUtilisateur.setEspece(rs.getInt("espece"));
			monUtilisateur.setBons(rs.getInt("bons"));
		}
		monUser = monUtilisateur;
		return monUser;
	}
	
	public void ajouterBons(int nombre) throws SQLException{
		String pseudo = monUser.getPseudo();
		setAjouterBon.setInt(1, nombre);
		setAjouterBon.setString(2, pseudo);
		setAjouterBon.executeUpdate();
		monUser.setBons(monUser.getBons()+nombre);
	}
	
	public void enleverBons(int nombre) throws SQLException{
		String pseudo = monUser.getPseudo();
		setEnleverBon.setInt(1, nombre);
		setEnleverBon.setString(2, pseudo);
		setEnleverBon.executeUpdate();
		monUser.setBons(monUser.getBons()-nombre);
	}
	
	public void ajouterEspece(int nombre) throws SQLException {
		String pseudo = monUser.getPseudo();
		setAjouterEspece.setInt(1, nombre);
		setAjouterEspece.setString(2, pseudo);
		setAjouterEspece.executeUpdate();
		monUser.setEspece(monUser.getEspece()+nombre);
	}
	
	public void enleverEspece(int nombre) throws SQLException{
		String pseudo = monUser.getPseudo();
		setEnleverEspece.setInt(1, nombre);
		setEnleverEspece.setString(2, pseudo);
		setEnleverEspece.executeUpdate();
		monUser.setEspece(monUser.getEspece()-nombre);
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



package informations;

import java.sql.*;
import java.util.*;
import java.net.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class InformationDataBean{
	
	private Connection con;
	private PreparedStatement getInformation;
	private Information monInformation;
	
	
	public UserDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","postgres","postgres");
		getInformation = con.prepareStatement("SELECT id, question, echeance, id_categorie, id_1 FROM information WHERE id = ?;");
	}
	
	public Information getInformationClick(int idInfo) throws SQLException{
		Information monInformation = new Information();
		
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
		setAjouterBon.close();
		setEnleverBon.close();
		setAjouterEspece.close();
		setEnleverEspece.close();
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

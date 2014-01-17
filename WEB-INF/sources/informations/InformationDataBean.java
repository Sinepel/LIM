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
			monInformation.setIdInformation(rs.getInt("id"));
			monInformation.setQuestion(rs.getString("question"));
			monInformation.setEcheance(rs.getString("echeance"));
			monInformation.setCategorie(rs.getInt("id_categorie"));
			monInformation.setIdInfoInverse(rs.getInt("id_1"));
		}
		return monInformation;
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

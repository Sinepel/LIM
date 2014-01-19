package informations;

//BEAN BDD TOOLS à utiliser pour créer le tableau des ordres
import tools.BDDTools;
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
	
	
	public InformationDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","constantin","moi");
		getInformation = con.prepareStatement("SELECT id, question, echeance, information.id_categorie, id_1, categorie.libelle FROM information,categorie WHERE information.id_categorie = categorie.id_categorie AND id = ?");
	}
	
	public Information getInformationClick(int idInfo) throws SQLException{
		Information monInformation = new Information();
		BDDTools tool = new BDDTools();
		
		getInformation.setInt(1,idInfo);
		ResultSet rs = getInformation.executeQuery(); 
		
		while(rs.next()){
			monInformation.setIdInformation(rs.getInt("id"));
			monInformation.setQuestion(rs.getString("question"));
			monInformation.setEcheance(rs.getString("echeance"));
			monInformation.setCategorie(rs.getInt("id_categorie"));
			monInformation.setIdInfoInverse(rs.getInt("id_1"));
			monInformation.setCategorieLibellle(rs.getString("libelle"));
		}
		return monInformation;
	}

	protected void finalize() {
	// attempt to close database connection
	try {
		getInformation.close();
		
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

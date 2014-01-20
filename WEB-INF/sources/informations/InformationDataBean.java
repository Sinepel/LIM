package informations;

//BEAN BDD TOOLS à utiliser pour créer le tableau des ordres
import tools.BDDTools;
import java.sql.*;
import java.util.*;
import java.net.*;
//ALORS ?

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class InformationDataBean{
	
	private Connection con;
	private PreparedStatement getInformation;
	private PreparedStatement getOrdresSql;
	private Information monInformation;
	
	
	public InformationDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","postgres","postgres");
		getInformation = con.prepareStatement("SELECT id, question, echeance, information.id_categorie, id_1, categorie.libelle FROM information,categorie WHERE information.id_categorie = categorie.id_categorie AND id = ?");
		getOrdresSql = con.prepareStatement("SELECT ordre.id_ordre, ordre.prix, ordre.nbbons, ordre.date_achat, utilisateur.pseudo FROM ordre, utilisateur where ordre.user_id = utilisateur.user_id AND id = ?");
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
			monInformation.setTableauOrdres(this.getOrdres(idInfo));			
		}
		return monInformation;
	}

	private String getOrdres(int idInfo) throws SQLException{
		getOrdresSql.setInt(1,idInfo);
		ResultSet rs = getOrdresSql.executeQuery();
		
		StringBuffer mesOrdres = new StringBuffer();
		mesOrdres.append("<table class=\"table\">\n");
		mesOrdres.append("<thead><tr><th>#</th><th>Prix</th><th>Nombre de bons</th><th>Date d'achat</th><th>Acheteur</th></thead><tbody>");
		
		int nblig = 0;
		
		while(rs.next()){
			String idOrdre = rs.getString("id_ordre");
			int ordrePrix = rs.getInt("prix");	
			int ordreNbbons = rs.getInt("nbbons");
			String ordreDateAchat = rs.getString("date_achat");
			String ordreAcheteur = rs.getString("pseudo");			
			mesOrdres.append("<tr><td><a href=\"market.jsp?id="+idOrdre+"\">"+idOrdre+"</td><td>"+ordrePrix+"</a></td><td>"+ordreDateAchat+"</td><td>"+ordreNbbons+"</td><td>"+ordreAcheteur+"</td></tr>");
		}
		
		mesOrdres.append("</tobdy></table>\n");
		return mesOrdres.toString();
		
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

package informations;

//BEAN BDD TOOLS à utiliser pour créer le tableau des ordres
import tools.BDDTools;
import java.sql.*;
import java.util.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
//ALORS ?

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class InformationDataBean{
	
	private Connection con;
	private PreparedStatement getInformation;
	private PreparedStatement getOrdresSql;
	private PreparedStatement getOrdresInverseSql;
	private PreparedStatement ajoutOrdreSql;
	private Information monInformation;
	private ArrayList<OrdreBean> mesOrdres;
	BDDTools tool = new BDDTools();
	
	
	public InformationDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		//con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","constantin","moi");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","postgres","postgres");
		getInformation = con.prepareStatement("SELECT id, question, echeance, information.id_categorie, id_1, categorie.libelle FROM information,categorie WHERE information.id_categorie = categorie.id_categorie AND id = ?;");
		getOrdresSql = con.prepareStatement("SELECT ordre.id_ordre, ordre.prix, ordre.nbbons, ordre.date_achat, ordre.id, ordre.user_id, utilisateur.pseudo FROM ordre, utilisateur, ordre.bonsRestants where ordre.user_id = utilisateur.user_id AND id = ? ORDER BY ordre.prix DESC;");
		getOrdresInverseSql = con.prepareStatement("SELECT ordre.id_ordre, 100 - ordre.prix as prix, ordre.nbbons, ordre.date_achat, ordre.id, ordre.user_id, utilisateur.pseudo, ordre.bonsRestants FROM ordre, utilisateur where ordre.user_id = utilisateur.user_id AND id = ? ORDER BY ordre.prix ASC;");
		ajoutOrdreSql = con.prepareStatement("INSERT into ordre(prix,nbbons,date_achat,id,user_id,bonsRestants) values(?,?,now(),?,?,?)");
	}
	
	public Information getInformationClick(int idInfo) throws SQLException{
		Information monInformation = new Information();
		
		
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
			monInformation.setTableauOrdresInverses(this.getOrdresInverse(rs.getInt("id_1")));	
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
			int ordreNbbons = rs.getInt("bonsRestants");
			String ordreDateAchat = tool.getDateFormat(rs.getString("date_achat"));
			String ordreAcheteur = rs.getString("pseudo");			
			mesOrdres.append("<tr class=\"success\"><td><a href=\"market.jsp?id="+idOrdre+"\">"+idOrdre+"</td><td>"+ordrePrix+"</a></td><td>"+ordreNbbons+"</td><td>"+ordreDateAchat+"</td><td>"+ordreAcheteur+"</td></tr>");
		}
		mesOrdres.append("</tobdy></table>\n");
		
		return mesOrdres.toString();
	}
	
	private String getOrdresInverse(int idInfoInverse) throws SQLException{
		
		getOrdresInverseSql.setInt(1,idInfoInverse);
		ResultSet rs = getOrdresInverseSql.executeQuery();
		
		StringBuffer mesOrdresInverses = new StringBuffer();
		mesOrdresInverses.append("<table class=\"table\">\n");
		mesOrdresInverses.append("<thead><tr><th>#</th><th>Prix</th><th>Nombre de bons</th><th>Date d'achat</th><th>Acheteur</th></thead><tbody>");
		
		int nblig = 0;
		
		while(rs.next()){
			String idOrdre = rs.getString("id_ordre");
			int ordrePrix = rs.getInt("prix");	
			int ordreNbbons = rs.getInt("bonsRestants");
			String ordreDateAchat = tool.getDateFormat(rs.getString("date_achat"));
			String ordreAcheteur = rs.getString("pseudo");			
			mesOrdresInverses.append("<tr class=\"danger\"><td><a href=\"market.jsp?id="+idOrdre+"\">"+idOrdre+"</td><td>"+ordrePrix+"</a></td><td>"+ordreNbbons+"</td><td>"+ordreDateAchat+"</td><td>"+ordreAcheteur+"</td></tr>");
		}
		
		mesOrdresInverses.append("</tobdy></table>\n");
		return mesOrdresInverses.toString();
	}
	
	public ArrayList<OrdreBean> getMesOrdresInverses(int IdInfoInverse) throws SQLException{
		
		getOrdresInverseSql.setInt(1,IdInfoInverse);
		ResultSet rs = getOrdresInverseSql.executeQuery();
		
		while(rs.next()){
			OrdreBean monOrdre = new OrdreBean();
			monOrdre.setIdOrdre(rs.getInt("id_ordre"));
			monOrdre.setPrix(rs.getInt("prix"));
			monOrdre.setNbBons(rs.getInt("nbbons"));
			monOrdre.setIdInformation(rs.getInt("id"));
			monOrdre.setIdUser(rs.getInt("user_id"));
			monOrdre.setDateAchat(rs.getString("date_achat"));
			
			mesOrdres.add(monOrdre);
		}
		return mesOrdres;
	}
	
	public void ajouterOrdre(int prix,int nbbons,int id,int user_id) throws Exception, SQLException{
		
		ajoutOrdreSql.setInt(1,prix);
		ajoutOrdreSql.setInt(2,nbbons);
		//ajoutOrdreSql.setDate(3,date2);
		ajoutOrdreSql.setInt(3,id);
		ajoutOrdreSql.setInt(4,user_id);
		ajoutOrdreSql.setInt(5,nbbons);
		
		ajoutOrdreSql.executeUpdate();
		
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

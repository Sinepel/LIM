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
import javax.sql.*;
import javax.naming.*;

import javax.mail.internet.InternetAddress;

public class InformationDataBean{
	
	private Connection con = null;
	private PreparedStatement getInformation;
	private PreparedStatement getOrdresSql;
	private PreparedStatement getOrdresInverseSql;
	private PreparedStatement ajoutOrdreSql;
	private PreparedStatement modifOrdreSql;
	private PreparedStatement getNbOrdres;
	private PreparedStatement defEtatInfo;
	private PreparedStatement recupIdUsers;
	private PreparedStatement upEspeceGagnant;
	private PreparedStatement miseAjourStatutUser;
	private Information monInformation;
	private ArrayList<OrdreBean> mesOrdres;
	private HashMap<InternetAddress,Integer> dicoMailEspece;
	BDDTools tool = new BDDTools();
	
	
	public InformationDataBean() throws Exception{
		//Récupération du POOL (LIM_POOL)
		Context iniCtx = new InitialContext();
		Context envCtx = (Context) iniCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("LIM_POOL");
		con = ds.getConnection();
		//Class.forName("org.postgresql.Driver");
		//con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","constantin","moi");
		//con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","postgres","postgres");
		getInformation = con.prepareStatement("SELECT id, question, echeance, information.id_categorie, id_1, etat, date_creation, createur, categorie.libelle FROM information,categorie WHERE information.id_categorie = categorie.id_categorie AND id = ?;");
		getOrdresSql = con.prepareStatement("SELECT ordre.id_ordre, ordre.prix, ordre.nbbons, ordre.date_achat, ordre.id, ordre.user_id, utilisateur.pseudo, ordre.bonsRestants FROM ordre, utilisateur where ordre.user_id = utilisateur.user_id AND id = ? AND bonsRestants > 0 AND etat='A' ORDER BY ordre.prix DESC;");
		getOrdresInverseSql = con.prepareStatement("SELECT ordre.id_ordre, 100 - ordre.prix as prix, ordre.nbbons, ordre.date_achat, ordre.id, ordre.user_id, utilisateur.pseudo, ordre.bonsRestants FROM ordre, utilisateur where ordre.user_id = utilisateur.user_id AND id = ? AND bonsRestants > 0 ORDER BY ordre.prix ASC;");
		ajoutOrdreSql = con.prepareStatement("INSERT into ordre(prix,nbbons,date_achat,id,user_id,bonsRestants,etat) values(?,?,now(),?,?,?,?)");
		modifOrdreSql = con.prepareStatement("UPDATE ordre set bonsRestants = bonsRestants - ? where id_ordre = ?");
		getNbOrdres = con.prepareStatement("SELECT count(*) AS nbOrdre FROM ordre where id = ?");
		defEtatInfo = con.prepareStatement("UPDATE information SET etat = ? WHERE id = ?");
		recupIdUsers = con.prepareStatement("SELECT ordre.user_id,utilisateur.mail,SUM(nbbons - bonsrestants) AS nbOrdresInfo FROM ordre LEFT JOIN utilisateur ON ordre.user_id = utilisateur.user_id where id = ? GROUP BY ordre.user_id, utilisateur.mail ORDER BY nbOrdresInfo DESC;");
		upEspeceGagnant = con.prepareStatement("UPDATE utilisateur set espece = espece + ? WHERE user_id = ?");
		miseAjourStatutUser = con.prepareStatement("UPDATE utilisateur set role = 'market-maker' WHERE user_id = ?");
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
			monInformation.setUserCreation(rs.getInt("createur"));
			monInformation.setCategorieLibellle(rs.getString("libelle"));
			monInformation.setEtat(rs.getString("etat"));
			monInformation.setDateCreation(rs.getString("date_creation"));
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
	/**
	 * Renvoyer la dernière clé générée, peut-être utile 
	 **/
	public void ajouterOrdre(int prix,int nbbons,int id,int user_id, String etat) throws Exception, SQLException
	{		
		ajoutOrdreSql.setInt(1,prix);
		ajoutOrdreSql.setInt(2,nbbons);
		//ajoutOrdreSql.setDate(3,date2);
		ajoutOrdreSql.setInt(3,id);
		ajoutOrdreSql.setInt(4,user_id);
		ajoutOrdreSql.setInt(5,nbbons);
		ajoutOrdreSql.setString(6,etat);
		
		ajoutOrdreSql.executeUpdate();
	}
	
	public void ajouterOrdre(int prix, int nbbons, int id, int user_id, int nbBonsRestants, String etat) throws Exception, SQLException
	{
		ajoutOrdreSql.setInt(1,prix);
		ajoutOrdreSql.setInt(2,nbbons);
		ajoutOrdreSql.setInt(3,id);
		ajoutOrdreSql.setInt(4,user_id);
		ajoutOrdreSql.setInt(5,nbBonsRestants);
		ajoutOrdreSql.setString(6,etat);
		
		ajoutOrdreSql.executeUpdate();
	}
	
	public void modifOrdre(int bonsARetirer, int ordre_id) throws Exception, SQLException
	{
		modifOrdreSql.setInt(1, bonsARetirer);
		modifOrdreSql.setInt(2, ordre_id);
		modifOrdreSql.executeUpdate();
	}
	
	public int getNombreOrdres(int idInformation) throws Exception, SQLException
	{
		getNbOrdres.setInt(1,idInformation);
		ResultSet rs = getNbOrdres.executeQuery();
		rs.next();
		return Integer.parseInt(rs.getString("nbOrdre"));
	}
	
	public HashMap finalisationInformation(int idGagnant, int idPerdant)throws Exception, SQLException
	{
		
		//modif info gagnante
		defEtatInfo.setString(1, "G");
		defEtatInfo.setInt(2, idGagnant);
		defEtatInfo.executeUpdate();
		
		//modif info perdante
		defEtatInfo.setString(1, "P");
		defEtatInfo.setInt(2, idPerdant);
		defEtatInfo.executeUpdate();
		
		//Mise à jour des especes des gagnants
		recupIdUsers.setInt(1,idGagnant);
		ResultSet rs = recupIdUsers.executeQuery();
		dicoMailEspece = new HashMap<InternetAddress,Integer>();
		int i = 0;
		while(rs.next()){
			//mettre à jour le role de l'utilisateur en market-maker ( les deux premiers)
			if(i < 2){
				miseAjourStatutUser.setInt(1, rs.getInt("user_id"));
				miseAjourStatutUser.executeUpdate();
			}
			int especes = (rs.getInt("nbOrdresInfo")) * 100;
			upEspeceGagnant.setInt(1, especes);
			upEspeceGagnant.setInt(2, rs.getInt("user_id"));
			dicoMailEspece.put(new InternetAddress(rs.getString("mail")),especes);			
			upEspeceGagnant.executeUpdate();
			i++;
		}
		return dicoMailEspece;
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

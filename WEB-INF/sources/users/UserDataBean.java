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
	private PreparedStatement getUserId;
	private PreparedStatement getNbOrdresInfo;
	private PreparedStatement getNbOrdresInfo2;
	private PreparedStatement getMarchesEnCours;
	private PreparedStatement setAjouterBon;
	private PreparedStatement setEnleverBon;
	private PreparedStatement setAjouterEspece;
	private PreparedStatement setEnleverEspece;
	private PreparedStatement setAjouterUtilisateur;
	private Statement modifUser;
	private User monUser;
	
	public UserDataBean() throws Exception{
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost/lim","constantin","moi");
		getUser = con.prepareStatement("SELECT user_id, pseudo, espece, bons, role, mail FROM utilisateur WHERE pseudo = ?;");
		getUserId = con.prepareStatement("SELECT pseudo, espece, bons, role FROM utilisateur WHERE user_id= ?;");
		getNbOrdresInfo = con.prepareStatement("SELECT SUM(nbbons - bonsrestants) AS nbOrdresInfo FROM ordre WHERE user_id = ? AND id IN(?,?) AND etat='A';");
		getNbOrdresInfo2 = con.prepareStatement("SELECT SUM(nbbons - bonsrestants) AS nbOrdresInfo FROM ordre WHERE user_id = ? AND id IN(?,?) AND etat='V';");
		getMarchesEnCours = con.prepareStatement("SELECT DISTINCT information.id,information.question FROM information INNER JOIN ordre ON information.id = ordre.id WHERE bonsrestants > 0 AND user_id = ? AND information.etat='N';");
		setAjouterBon = con.prepareStatement("UPDATE utilisateur SET bons = bons + ? WHERE pseudo = ?;");
		setEnleverBon = con.prepareStatement("UPDATE utilisateur SET bons = bons - ? WHERE pseudo = ?;");
		setAjouterEspece = con.prepareStatement("UPDATE utilisateur SET espece = espece + ? WHERE pseudo = ?;");
		setEnleverEspece = con.prepareStatement("UPDATE utilisateur SET espece = espece - ? WHERE pseudo = ?;");
		setAjouterUtilisateur = con.prepareStatement("INSERT into utilisateur(pseudo,mdp,mail,espece,bons,role) values(?,md5(?),?,10000,0,'user')");
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
			monUtilisateur.setMail(rs.getString("mail"));
		}
		monUser = monUtilisateur;
		return monUser;
	}
	
	public User getUtilisateurId(int idUser) throws SQLException{
		User monUtilisateur = new User();
		
		getUserId.setInt(1, idUser);
		ResultSet rs = getUserId.executeQuery(); 
		
		while(rs.next()){
			monUtilisateur.setId(idUser);
			monUtilisateur.setPseudo(rs.getString("pseudo"));
			monUtilisateur.setRole(rs.getString("role"));
			monUtilisateur.setEspece(rs.getInt("espece"));
			monUtilisateur.setBons(rs.getInt("bons"));
		}
		monUser = monUtilisateur;
		return monUser;
	}
	
	public int getNbOrdresInformation(int idInformation, int idInfoInverse) throws SQLException{
		int A = 0;
		int V = 0;
		int retour; 
		getNbOrdresInfo.setInt(1, monUser.getId());
		getNbOrdresInfo2.setInt(1, monUser.getId());
		getNbOrdresInfo.setInt(2, idInformation);
		getNbOrdresInfo2.setInt(2, idInformation);
		getNbOrdresInfo.setInt(3, idInfoInverse);
		getNbOrdresInfo2.setInt(3, idInfoInverse);
		ResultSet rs = getNbOrdresInfo.executeQuery(); 
		rs.next();
		if(rs.getString("nbOrdresInfo") == null)
			A = 0;
		else
			A = Integer.parseInt(rs.getString("nbOrdresInfo"));
		rs=getNbOrdresInfo2.executeQuery();
		rs.next();
		if(rs.getString("nbOrdresInfo") == null)
			V = 0;
		else
			V = Integer.parseInt(rs.getString("nbOrdresInfo"));
		
		retour = A - V;
		return retour;
		
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
	
	public void ajouterUtilisateur(String pseudo, String mdp, String mail) throws SQLException{
		setAjouterUtilisateur.setString(1,pseudo);
		setAjouterUtilisateur.setString(2,mdp);
		setAjouterUtilisateur.setString(3,mail);
		setAjouterUtilisateur.executeUpdate();
	}
	
	public void modifierUtilisateur(String pseudo, String email, String password) throws SQLException{
		modifUser = con.createStatement();
		if(!email.equals("")){
			modifUser.executeUpdate("UPDATE utilisateur SET mail = '"+email+"' WHERE pseudo = '"+pseudo+"'");
		}
		if(!password.equals("")){
			modifUser.executeUpdate("UPDATE utilisateur SET mdp = md5('"+password+"') WHERE pseudo = '"+pseudo+"'");
		}
	}
	
	public String getInformationEnCours(int id_user) throws SQLException{
		getMarchesEnCours.setInt(1,id_user);
		ResultSet rs = getMarchesEnCours.executeQuery();
		
		StringBuffer mesInfos = new StringBuffer();
		
		
		int nblig = 0;
		
		while(rs.next()){
			String question = rs.getString("question");
			int idInfo = rs.getInt("id");			
			mesInfos.append("<p><a href=\"market.jsp?id="+idInfo+"\">"+question+"</a></p>");
		}
		
		return mesInfos.toString();
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



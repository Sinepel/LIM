package informations;
import java.sql.*;

public class Information{
	private int id_information;
	private int id_categorie;
	private int id_question_inverse;
	private int id_userCreation;
	private String etat;
	private String question;
	private String echeance;
	private String dateCreation;
	private String categorieLibelle;
	private String mesOrdres;
	private String mesOrdresInverses;
	
	public Information(){}
	
	public int getIdInformation(){
		return id_information;
	}
	public void setIdInformation(int id_information){
		this.id_information = id_information;
	}
	public String getQuestion(){
		return this.question;
	}
	public void setQuestion(String question){
		this.question = question;
	}
	public String getEcheance(){
		return this.echeance;
	}
	public void setEcheance(String echeance){
		this.echeance = echeance;
	}
	public int getCategorie(){
		return id_categorie;
	}
	public void setCategorie(int categorie){
		this.id_categorie = categorie;
	}
	public int getIdInfoInverse(){
		return this.id_question_inverse;
	}
	public void setIdInfoInverse(int id_question_inverse){
		this.id_question_inverse = id_question_inverse;
	}
	public String getCategorieLibelle(){
		return this.categorieLibelle;
	}
	public void setCategorieLibellle(String categorieLibelle){
		this.categorieLibelle = categorieLibelle;
	}
	public String getDateCreation(){
		return this.dateCreation;
	}
	public void setDateCreation(String dateCreation){
		this.dateCreation = dateCreation;
	}
	public int getUserCreation(){
		return this.id_userCreation;
	}
	public void setUserCreation(int idUserCrea){
		this.id_userCreation = idUserCrea;
	}
	
	public void setTableauOrdres(String mesOrdres){
		this.mesOrdres = mesOrdres;
	}
	public String getTableauOrdres(){
		return this.mesOrdres;
	}
	
	public void setTableauOrdresInverses(String mesOrdresInverses){
		this.mesOrdresInverses = mesOrdresInverses;
	}
	public String getTableauOrdresInverses(){
		return this.mesOrdresInverses;
	}

	public void setEtat(String etat){
		this.etat = etat;
	}
	public String getEtat(){
		return this.etat;
	}

}

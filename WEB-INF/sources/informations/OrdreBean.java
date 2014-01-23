package informations;
import java.sql.*;

public class Ordre{
	
	private int id_ordre;
	private int prix;
	private int nbBons;
	private int id_information;
	private int id_user;
	private String date_achat;
	
	public Ordre(){} 
	
	public void setIdOrdre(int id_ordre){
		this.id_ordre = id_ordre;
	}
	public void setPrix(int prix){
		this.prix = prix;
	}
	public void setNbBons(int nbBons){
		this.nbBons = nbBons;
	}
	public void setIdInformation(int id_information){
		this.id_information = id_information;
	}
	public void setIdUser(int id_user){
		this.id_user = id_user;
	}
	public void setDateAchat(String date_achat){
		this.date_achat = date_achat;
	}
	
	public int getIdOrdre(){
		return this.id_ordre;
	}
	public int getPrix(){
		return this.prix;
	}
	public int getNbBons(){
		return this.nbBons;
	}
	public int getIdInformation(){
		return this.id_information;
	}
	public int getIdUser(){
		return this.id_user;
	}
	public String getDateAchat(){
		return this.date_achat;
	}

}

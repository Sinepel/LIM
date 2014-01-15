package users;

public class User{
	private String pseudo;
	private String role;
	private int espece;
	private int bons;
	
	public User(){}
	
	public String getPseudo(){
		return pseudo;
	}
	public void setPseudo(String pPseudo){
		this.pseudo = pPseudo;
	}
	public String getRole(){
		return role;
	}
	public void setRole(String role){
		this.role = role;
	}
	public int getEspece(){
		return espece;
	}
	public void setEspece(int espece){
		this.espece = espece;
	}
	public int getBons(){
		return bons;
	}
	public void setBons(int bons){
		this.bons = bons;
	}
	
		
}

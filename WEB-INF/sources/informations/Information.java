package informations;

public class Information{
	private int id_information;
	private int id_categorie;
	private int id_question_inverse;
	private int id_userCreation;
	private String question;
	private String echeance;
	private String dateCreation;
	
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
}

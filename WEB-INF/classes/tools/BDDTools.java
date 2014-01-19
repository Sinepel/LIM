/*
------------------------------------------------
Bean de mise en forme HTML d'une requête SQL
Auteur : Constantin Boulanger
Date   : 15/01/2014
------------------------------------------------
*/

package tools;

import java.sql.*;

public class BDDTools
{
    /**
       Renvoie une table HTML contenant l'ensemble du resultSet. Les
       noms des colonnes peuvent être mis ou pas. la
       premiere colonne doit etre l'id qui sera utilisé pour faire un
       lien http.
		@param rs le resulset à transformer en table html
		@param colname indique si les noms des cols seront affichés
		@param ligNb   indique si le nombre de lignes est affiché en fin de table
		@param link    indique si on fait un lien sur la premiere colonne (id)
		@param pagination	indique si on veut la pagination du tableau
    **/
    public String getHTMLSimpleTableCategory(ResultSet rs,boolean colname,boolean ligNb, boolean link)
    throws Exception
    {
		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd= rs.getMetaData();
		int nbCols=rsmd.getColumnCount();
		sb.append("<table class=\"table\">\n");
		// entete des colonnes
		if (colname)
		{
			sb.append("<thead><tr><th>#</th><th>Titre</th><th>Date de fin</th></thead><tbody>");
			
		}
		// valeurs des colonnes
		int nblig=1;
		String id = null;
		String catID = null;
		while(rs.next())
			{
				id = rs.getString("id");
				catID = rs.getString("id_categorie");
				
				sb.append("<tr><td>"+id+"</td><td><a href=\"market.jsp?id="+id+"\">"+rs.getString("question")+"</a></td><td>"+rs.getString("echeance")+"</td></tr>");
				nblig++;
			}
		sb.append("</tobdy></table>\n");
		if (ligNb) 
		   sb.append("<div class=\"alert alert-info\">Il y a "+(nblig-1)+" lignes</div>");
		   
		
		
		return sb.toString();
    }
    
    public String getHTMLSimpleTable(ResultSet rs,boolean colname,boolean ligNb, boolean link)
    throws Exception
    {
		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd= rs.getMetaData();
		int nbCols=rsmd.getColumnCount();
		sb.append("<table class=\"table\">\n");
		// entete des colonnes
		if (colname)
		{
			sb.append("<thead><tr><th>#</th><th>Titre</th><th>Date de fin</th><th>Catégorie</th></tr></thead><tbody>");
			
		}
		// valeurs des colonnes
		int nblig=1;
		String id = null;
		String catID = null;
		while(rs.next())
			{
				id = rs.getString("id");
				catID = rs.getString("id_categorie");
				
				sb.append("<tr><td>"+id+"</td><td><a href=\"market.jsp?id="+id+"\">"+rs.getString("question")+"</a></td><td>"+this.getDateFormat(rs.getString("echeance"))+"</td><td><a href=\"category.jsp?id="+catID+"\">"+rs.getString("libelle")+"</a></td></tr>");
				nblig++;
			}
		sb.append("</tobdy></table>\n");
		if (ligNb) 
		   sb.append("<div class=\"alert alert-info\">Il y a "+(nblig-1)+" lignes</div>");
		   
		
		
		return sb.toString();
    }

    /*-----------------------------------------------------------*/

    /**
       Renvoie une table HTML sous forme de fiche représentant la
       ligne sur laquelle est positionné le resultSet. Attention à ne
       pas oublier de faire appel à next() avant l'appel de cette
       méthode.
    **//*
    public String getHTMLSimpleFiche(ResultSet rs)
    throws Exception
    {
	StringBufferx sb = new StringBuffer();
	ResultSetMetaData rsmd= rs.getMetaData();
	int nbCols=rsmd.getColumnCount();
	sb.append("<center><table>\n");
	// entete des colonnes
	for (int i=1;i<=nbCols;i++)
	    {
		sb.append("<tr>");
		if (i%2==0) sb.append("<td class=paire>");
		else sb.append("<td class=impaire>");
		sb.append("<b>"+(rsmd.getColumnName(i)).toUpperCase()+"</b>");
		sb.append("</td>");
		if (i%2==0) sb.append("<td class=paire>");
		else sb.append("<td class=impaire>");
		sb.append(rs.getString(i));
		sb.append("</td>");
		sb.append("</tr>\n");
	    }
	sb.append("</table></center>");
	return sb.toString();
    }*/
    
    public String getHTMLSimpleTableOrdres(ResultSet rs,boolean colname,boolean ligNb, boolean link)
    throws Exception
    {
		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd= rs.getMetaData();
		int nbCols=rsmd.getColumnCount();
		sb.append("<table class=\"table\">\n");
		// entete des colonnes
		if (colname)
		{
			sb.append("<thead><tr><th>#</th><th>Titre</th><th>Date de fin</th><th>Catégorie</th></tr></thead><tbody>");
			
		}
		// valeurs des colonnes
		int nblig=1;
		while(rs.next())
			{
				sb.append("<tr><td>"+rs.getString("prix")+"</td><td>"+rs.getString("bons")+"</td><td>"+rs.getString("date_achat")+"</td></tr>");
				nblig++;
			}
		sb.append("</tobdy></table>\n");
		if (ligNb) 
		   sb.append("<p>La table contient "+(nblig-1)+" lignes\n");
		   
		
		
		return sb.toString();

	}
	public String getDateFormat(String dateBadFormat)
	{
		String date = "";
		String str[]=dateBadFormat.split("-");
		date = str[2]+"/"+str[1]+"/"+str[0];
		
		return (date);
	}
}

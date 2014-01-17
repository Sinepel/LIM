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
		while(rs.next())
			{
				sb.append("<tr><td>"+rs.getString("id")+"</td><td>"+rs.getString("question")+"</td><td>"+rs.getString("echeance")+"</td><td>"+rs.getString("libelle")+"</td></tr>");
				nblig++;
			}
		sb.append("</tobdy></table>\n");
		if (ligNb) 
		   sb.append("<p>La table contient "+(nblig-1)+" lignes\n");
		   
		
		
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
				sb.append("<tr><td>"+rs.getString("prix")+"</td><td>"+rs.getString("question")+"</td><td>"+rs.getString("echeance")+"</td><td>"+rs.getString("libelle")+"</td></tr>");
				nblig++;
			}
		sb.append("</tobdy></table>\n");
		if (ligNb) 
		   sb.append("<p>La table contient "+(nblig-1)+" lignes\n");
		   
		
		
		return sb.toString();

	}
}

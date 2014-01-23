<!-- page4.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
   <TITLE>Page de test de configuration</TITLE>
   <%@ page 
         contentType="text/html; charset=ISO-8859-15"
         import="java.sql.* , javax.sql.* , javax.naming.* , tools.SessionTrack"
	 session="false"
         errorPage="erreur.jsp" %>
   <link rel=stylesheet href=style.css type=text/css>		
</HEAD>
<BODY>



<h1>Page de suivi de session </h1>

Cette page permet de voir comment effectuer un suivi des sessions
actives sur le serveur. Elle utilise le bean SessionTrack


<p>


Nb sessions :<%=SessionTrack.getSessionNumber()%><p>

Liste des sessions actives : <p><%=SessionTrack.getSessionList()%>

<p>

<a href=menu.html>Retour</a>

<%@ include file="basDePage.html" %>

</BODY>
</HTML>

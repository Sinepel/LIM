<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="utf-8">
	<%@ page pageEncoding="UTF-8" %>
	<%@ page import="java.sql.*" %>
    <%@ page import="javax.servlet.http.*" %>
    <%@ page import="java.net.*" %>    
    <%@ page import="javax.naming.*" %>
    <%@ page import="javax.sql.*" %>
    
    <!-- JAVA BEAN POUR FAIRE LE TABLEAU D'INFORMATIONS -->
    <jsp:useBean id="tool" scope="application" class="tools.BDDTools" />
    <jsp:useBean id="recupUser" scope="request" class="users.UserDataBean" />
    <jsp:useBean id="user" scope="page" class="users.User" />
    
    <% 
		user = recupUser.getUtilisateur(request.getRemoteUser());
		//recupUser.ajouterBons(2);
		recupUser.fermerConnexion();
		int id = user.getId();
		String pseudo = user.getPseudo();
		int bons = user.getBons();
		int espece = user.getEspece();
		String role = user.getRole();
	%>	
	
    
  <title>Lille Information Market</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Le marché d'information par la DA2I">
  <meta name="author" content="Constantin Boulanger - Florent Pulcian">

	<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
	<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
	<!--script src="js/less-1.3.3.min.js"></script-->
	<!--append ‘#!watch’ to the browser URL, then refresh the page. -->
	
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="img/favicon.png">  
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/scripts.js"></script>
</head>

<body>
	
<%

//Récupération du POOL (LIM_POOL)
Context iniCtx = new InitialContext();
Context envCtx = (Context) iniCtx.lookup("java:comp/env");
DataSource ds = (DataSource) envCtx.lookup("LIM_POOL");
Connection con = ds.getConnection();

//Préparation de la requete
Statement stmt= con.createStatement();



String date = request.getParameter("date");
%>


<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
					
			<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<li class="active"><a href="index.jsp">Accueil</a></li>
				<li><a href="logout.jsp">Déconnexion</a></li>
			</ul>
			<h1 class="muted">Lille Information Market</h1>
			<hr>
       </div>
			
			
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-8 column">
			<h2>Informations qui se terminent le : <%= date %></h2>
			
			<%
					ResultSet rs=stmt.executeQuery("Select * from information WHERE echeance = '"+date+"';");
					out.println(tool.getHTMLSimpleTableDate(rs,true,true,false));
					
			%>

		</div>
		<div class="col-md-4 column">
			<ul class="nav nav-pills nav-stacked">
                <li><a href="index.jsp"><i class="fa fa-home fa-fw"></i>Accueil</a></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-list-alt fa-fw"></i>Widgets</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-file-o fa-fw"></i>Pages</a></li>-->
                <li class="active"><a href="all.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>Tous les marchés</a></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-table fa-fw"></i>Table</a></li>-->
                <li><% if (role.equals("market-maker") || role.equals("admin")){ out.print("<li><a href=\"NewMarket.jsp\"><i class=\"fa fa-tasks fa-fw\"></i>Créer un marché</a></li>");} %></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-tasks fa-fw"></i>Créer un marché</a></li>-->
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-calendar fa-fw"></i>Calender</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-book fa-fw"></i>Library</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-pencil fa-fw"></i>Applications</a></li>-->
                <li><a href="profil.jsp"><i class="fa fa-cogs fa-fw"></i>Votre Profil</a></li>
				<li><% if (role.equals("admin")){ out.print("<li><a href=\"/admin\"><i class=\"fa fa-tasks fa-fw\"></i>Administration</a></li>");} %></li>

            </ul>
            <div class="panel-group" id="panel-404098">
				<div class="panel panel-default">
					<div class="panel-heading">
						 <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-404098" href="#panel-element-807612">Liste des catégories</a>
					</div>
					<div id="panel-element-807612" class="panel-collapse collapse">
						<div class="panel-body">
							<%
								rs = stmt.executeQuery("Select id_categorie,libelle from categorie;");
								while (rs.next())
								{
									out.println("<p><a href=\"category.jsp?id="+rs.getString("id_categorie")+"\">"+rs.getString("libelle")+"</a></p>");
								}
							
							%>
						</div>
					</div>
				</div>
				
			</div>
            
			
			<% rs.close(); stmt.close(); con.close(); %>		
							
				
			
			<div class="panel-group" id="panel-404098">
				
				<div class="panel panel-default">
					<div class="panel-heading">
						 <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-404098" href="#panel-element-603060">Vos Informations</a>
					</div>
					<div id="panel-element-603060" class="panel-collapse collapse">
						<div class="panel-body">
							<p>Pseudo: <%= user.getPseudo()%></p>
							<p>Vos bons: <%= user.getBons()%></p>
							<p>Votre argent: <%= user.getEspece()%></p>
							<p>Votre rôle: <%= user.getRole()%></p>
							<p>Taux de réussite: 41%</p>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<hr>	
<div class="row">
    <div class="col-lg-12">
      <div class="col-md-8">
        <a href="#">FAQ</a>    
        <a href="#">À Propos</a>    
	</div>
	<div class="col-md-4">
		<p class="muted pull-right">© 2014 Lille Information Market. &copy; <br>Constantin Boulanger & Florent Pulcian</p>
	</div>
</div>

</div>
</body>
</html>

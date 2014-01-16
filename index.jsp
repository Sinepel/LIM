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
		recupUser.ajouterBons(2);
		recupUser.fermerConnexion();
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
//PreparedStatement preparedStatement = con.prepareStatement("Select * from information INNER JOIN categorie ON information.id_categorie = categorie.id_categorie ORDER BY ? ? LIMIT 10;");







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
			<h2>Le marché d'information</h2>
			<div class="tabbable" id="tabs-919597">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-356429" data-toggle="tab">Nouvelles informations</a>
					</li>
					<li>
						<a href="#panel-216330" data-toggle="tab">Bientôt Terminées</a>
					</li>
					
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-356429">
						<%
							ResultSet rs=stmt.executeQuery("Select * from information LEFT JOIN categorie ON information.id_categorie = categorie.id_categorie ORDER BY id ASC LIMIT 10;");
							out.println(tool.getHTMLSimpleTable(rs,true,true,false));
							rs = null;
					%>
					</div>
					<div class="tab-pane" id="panel-216330">
						<%
							rs=stmt.executeQuery("Select * from information LEFT JOIN categorie ON information.id_categorie = categorie.id_categorie ORDER BY echeance ASC LIMIT 10;");
							out.println(tool.getHTMLSimpleTable(rs,true,true,false));
							rs = null;
						%>
					</div>
					
					
					
					
				</div>
			</div>
			
			
		</div>
		<div class="col-md-4 column">
			<div class="panel-group" id="panel-404098">
				<div class="panel panel-default">
					<div class="panel-heading">
						 <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-404098" href="#panel-element-807612">Menu Utilisateur</a>
					</div>
					<div id="panel-element-807612" class="panel-collapse collapse">
						<div class="panel-body">
							<ul>
								<p><% if (user.getRole().equals("market-maker")){ out.print("<a href=\"NewMarket.jsp\">Nouveau Marché</a>");} %></p>
							</ul>
						</div>
					</div>
				</div>
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
</div>
</body>
</html>

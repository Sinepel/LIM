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
PreparedStatement tableauOrdres = con.prepareStatement("select * from ordre INNER JOIN utilisateur ON ordre.user_id = utilisateur.user_id where utilisateur.user_id="+id+";");







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
			<h2>Votre profil</h2>
			
			<!-- MET TON CODE ICI BIATCH -->
			
			<div class="tabbable" id="tabs-919597">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-356429" data-toggle="tab">Vos statistiques</a>
					</li>
					<li>
						<a href="#panel-216330" data-toggle="tab">Modifier votre profil</a>
					</li>
					<li>
						<a href="#panel-123456" data-toggle="tab">Vos achats</a>
					</li>
					
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-356429">
						
						
						
						
						<ul class="list-group">
						  <li class="list-group-item">Pseudo: <%= pseudo%></li>
						  <li class="list-group-item">Vos bons: <%= bons%></li>
						  <li class="list-group-item">Votre argent: <%= espece%></li>
						  <li class="list-group-item">Votre rôle: <%= role%></li>
						  <li class="list-group-item">Taux de réussite: 41%</li>
						</ul>
				</div>
				
				<div class="tab-pane" id="panel-216330">						
						<form class="form-horizontal" action='' method="POST">
						  <fieldset disabled>
							 <div class="control-group">
							  <!-- Username -->
							  <label class="control-label"  for="username">Pseudo</label>
							  <div class="controls">
								<input type="text" id="username" name="username" placeholder="" class="input-xlarge" value="<%= pseudo %>">
							  </div>
							</div>
							</fieldset>
						 <fieldset>
							<div class="control-group">
							  <!-- E-mail -->
							  <label class="control-label" for="email">E-mail</label>
							  <div class="controls">
								<input type="text" id="email" name="email" placeholder="" class="input-xlarge">
								<p class="help-block">Veuillez mettre votre mail pour être prévenu de la finalité de vos actions</p>
							  </div>
							</div>
						 
							<div class="control-group">
							  <!-- Password-->
							  <label class="control-label" for="password">Mot de passe</label>
							  <div class="controls">
								<input type="password" id="password" name="password" placeholder="" class="input-xlarge">
								
							  </div>
							</div>
						 
							<div class="control-group">
							  <!-- Password -->
							  <label class="control-label"  for="password_confirm">Mot de passe (Confirmation)</label>
							  <div class="controls">
								<input type="password" id="password_confirm" name="password_confirm" placeholder="" class="input-xlarge">
								<p class="help-block">Veuillez retaper le mot de passe</p>
							  </div>
							</div>
						 
							<div class="control-group">
							  <!-- Button -->
							  <div class="controls">
								<button class="btn btn-success">Modifier votre profil</button>
							  </div>
							</div>
						  </fieldset>
						</form>						
					</div>
					
					
				<div class="tab-pane" id="panel-123456">
					
					<%
						ResultSet rs= tableauOrdres.executeQuery();
						out.println(tool.getHTMLSimpleTableOrdres(rs,true,true,false));
					%>
				</div>
					
					
					
					
				</div>
			</div>
			
			
			
			
		</div>
		<div class="col-md-4 column">
			
			<ul class="nav nav-pills nav-stacked">
                <li class="active"><a href="index.jsp"><i class="fa fa-home fa-fw"></i>Accueil</a></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-list-alt fa-fw"></i>Widgets</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-file-o fa-fw"></i>Pages</a></li>-->
                <li><a href="all.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>Tous les marchés</a></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-table fa-fw"></i>Table</a></li>-->
                <li><% if (role.equals("market-maker") || role.equals("admin")){ out.print("<li><a href=\"NewMarket.jsp\"><i class=\"fa fa-tasks fa-fw\"></i>Créer un marché</a></li>");} %></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-tasks fa-fw"></i>Créer un marché</a></li>-->
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-calendar fa-fw"></i>Calender</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-book fa-fw"></i>Library</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-pencil fa-fw"></i>Applications</a></li>-->
                <li><a href="profil.jsp"><i class="fa fa-cogs fa-fw"></i>Votre Profil</a></li>
				<li><% if (role.equals("admin")){ out.print("<li><a href=\"/admin\"><i class=\"fa fa-tasks fa-fw\"></i>Administration</a></li>");} %></li>

            </ul>
			
			
		</div>
	</div>
</div>
</body>
</html>
<%
	tableauOrdres.close();
	rs.close();
	con.close();
	stmt.close();
%>

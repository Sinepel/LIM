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
    <%@ page import="java.util.Date" %>
    <%@ page import="java.text.DateFormat" %>
    <%@ page import="java.text.SimpleDateFormat" %>
    
    <!-- JAVA BEAN POUR FAIRE LE TABLEAU D'INFORMATIONS -->
    <jsp:useBean id="tool" scope="application" class="tools.BDDTools" />
    <jsp:useBean id="recupUser" scope="request" class="users.UserDataBean" />
    <jsp:useBean id="recupInformation" scope="page" class="informations.InformationDataBean" />
    <jsp:useBean id="user" scope="page" class="users.User" />
    <jsp:useBean id="info" scope="page" class="informations.Information" />
    
    <% 
		String idMarche = request.getParameter("id");
		int idMarcheInt = Integer.parseInt(idMarche);
		user = recupUser.getUtilisateur(request.getRemoteUser());
		info = recupInformation.getInformationClick(idMarcheInt);
		int nbOrdre = recupInformation.getNombreOrdres(idMarcheInt);
		recupInformation.fermerConnexion();
		recupUser.fermerConnexion();
		
	%>	
	
    
  <title>L'information - Lille Information Market</title>
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
	
		
	<!-- SCRIPT POUR LE GRAPHIQUE -->
	<link rel="stylesheet" href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css">
	<script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
	<script src="http://cdn.oesmith.co.uk/morris-0.4.3.min.js"></script>
	
</head>

<body>
<script>
$( document ).ready(function() {
	
	var id = $("input#marketID").val();

	$.getJSON(
		"Graphique", 
		{
			"id": id,
		}, 
		function(res){
			new Morris.Line({
		 		element: 'graph',
				data: res,
				xkey: 'jour',
				ykeys: ['valeur'],
				labels: ['Prix moyen '],
				//dateFormat: function(x) { return $.datepicker.formatDate("dd/mm/yy", new Date(x)); }
			});
		}
	)
});
</script>
	
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
			<h2><%= info.getQuestion()%></h2>
			<p>Je veux <a href="market.jsp?id=<%=info.getIdInfoInverse()%>">l'information inverse</a></p>
			<p>Le marché se termine le: <a href="date.jsp?date=<%= tool.getDateFormat(info.getEcheance()) %>"><%= tool.getDateFormat(info.getEcheance()) %></a></p>
			<p>Catégorie : <a href="category.jsp?id=<%=info.getCategorie() %>"><%= info.getCategorieLibelle() %></a></p>
			<h3>Offres</h3>
			<%= info.getTableauOrdresInverses() %>	
			<h3>Demandes</h3>
			<%= info.getTableauOrdres() %>
			<% 	int marcheInverse = info.getIdInfoInverse(); %>
			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<h3>Acheter</h3>
					<form action="servlet/AchatInfo" method="post" class="form" role="form">
						
						<input  class="form-control" type="hidden" id="userID" name="userID" value="<%= user.getId() %>">
						<input  class="form-control" type="hidden" id="marketID" name="marketID" value="<%= idMarcheInt %>">
						<input  class="form-control" type="hidden" id="inverse" name="inverse" value="<%= marcheInverse %>">
						<input  class="form-control" type="hidden" id="nbOrdre" name="nbOrdre" value="<%= nbOrdre %>">
						
						<div class="form-group">
							<input class="form-control" type="number" id="prix" min="1" max="99"  name="prix" placeholder="Prix unique d'un bon" required>
						</div>
						<div class="form-group">
							<input  class="form-control" type="number" id="nbBons" min="1" name="nbBons" placeholder="Nombre de bons" required>
						</div>

						
						<div class="row">
							<div class="col-md-6">
								<button type="submit" class="btn btn-primary btn-block btn-success">Acheter</button>
							</div>	
							<div class="col-md-6">
								<button type="reset" class="btn btn-primary btn-block btn-danger">Annuler</button>
							</div>
						</div>
					</form>
					
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 column">
					<h3>Graphique des achats</h3>
					<div id="graph"><% if(nbOrdre == 0) { %> <p>Il n'y pas encore eu d'achat pour cette information </p><% } %></div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4 column">
			
			<ul class="nav nav-pills nav-stacked">
                <li><a href="index.jsp"><i class="fa fa-home fa-fw"></i>Accueil</a></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-list-alt fa-fw"></i>Widgets</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-file-o fa-fw"></i>Pages</a></li>-->
                <li class="active"><a href="all.jsp"><i class="fa fa-bar-chart-o fa-fw"></i>Tous les marchés</a></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-table fa-fw"></i>Table</a></li>-->
                <li><% if (user.getRole().equals("market-maker") || user.getRole().equals("admin")){ out.print("<li><a href=\"NewMarket.jsp\"><i class=\"fa fa-tasks fa-fw\"></i>Créer un marché</a></li>");} %></li>
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-tasks fa-fw"></i>Créer un marché</a></li>-->
                <!--<li><a href="http://www.jquery2dotnet.com"><i class="fa fa-calendar fa-fw"></i>Calender</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-book fa-fw"></i>Library</a></li>
                <li><a href="http://www.jquery2dotnet.com"><i class="fa fa-pencil fa-fw"></i>Applications</a></li>-->
                <li><a href="profil.jsp"><i class="fa fa-cogs fa-fw"></i>Votre Profil</a></li>
				<li><% if (user.getRole().equals("admin")){ out.print("<li><a href=\"/admin\"><i class=\"fa fa-tasks fa-fw\"></i>Administration</a></li>");} %></li>

            </ul>
			
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

</div>
</body>
</html>

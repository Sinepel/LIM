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
Statement stmt= con.createStatement();

ResultSet testLecture = stmt.executeQuery("Select * from information INNER JOIN categorie ON information.id_categorie = categorie.id_categorie ORDER BY echeance DESC LIMIT 10;");




%>


<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
					
			<div class="masthead">
			<ul class="nav nav-pills pull-right">
				<li class="active"><a href="#">Accueil</a></li>
				<li><a href="servlet/logout">Déconnexion</a></li>
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
					<li>
						<a href="#panel-356429" data-toggle="tab">Nouvelles informations</a>
					</li>
					<li>
						<a href="#panel-216330" data-toggle="tab">Bientôt Terminées</a>
					</li>
					<li class="active">
						<a href="#panel-000000" data-toggle="tab">Test Lecture.</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane" id="panel-356429">
						<table class="table">
				<thead>
					<tr>
						<th>
							#
						</th>
						<th>
							Titre
						</th>
						<th>
							Date de fin
						</th>
						<th>
							Catégorie
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							1
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							01/04/2012
						</td>
						<td>
							Default
						</td>
					</tr>
					<tr class="active">
						<td>
							1
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							01/04/2012
						</td>
						<td>
							Approved
						</td>
					</tr>
					<tr class="success">
						<td>
							2
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							02/04/2012
						</td>
						<td>
							Declined
						</td>
					</tr>
					<tr class="warning">
						<td>
							3
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							03/04/2012
						</td>
						<td>
							Pending
						</td>
					</tr>
					<tr class="danger">
						<td>
							4
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							04/04/2012
						</td>
						<td>
							Call in to confirm
						</td>
					</tr>
				</tbody>
			</table>
					</div>
					<div class="tab-pane" id="panel-216330">
						<table class="table">
				<thead>
					<tr>
						<th>
							#
						</th>
						<th>
							Titre
						</th>
						<th>
							Date de fin
						</th>
						<th>
							Catégorie
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							1
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							01/04/2012
						</td>
						<td>
							Default
						</td>
					</tr>
					<tr class="active">
						<td>
							1
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							01/04/2012
						</td>
						<td>
							Approved
						</td>
					</tr>
					<tr class="success">
						<td>
							2
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							02/04/2012
						</td>
						<td>
							Declined
						</td>
					</tr>
					<tr class="warning">
						<td>
							3
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							03/04/2012
						</td>
						<td>
							Pending
						</td>
					</tr>
					<tr class="danger">
						<td>
							4
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							04/04/2012
						</td>
						<td>
							Call in to confirm
						</td>
					</tr>
				</tbody>
			</table>
					</div>
					
					<div class="tab-pane active" id="panel-000000">
						<table class="table">
				<thead><tr><th>#</th><th>Titre</th><th>Date de fin</th><th>Catégorie</th></tr>
				</thead>
				<tbody>
					<tr>
						<td>
							1
						</td>
						<td>
							TB - Monthly
						</td>
						<td>
							01/04/2012
						</td>
						<td>
							Default
						</td>
					</tr>
					
					<%
							while(testLecture.next())
							{
								out.println("<tr><td>"+testLecture.getString("id")+"</td><td>"+testLecture.getString("question")+"</td><td>"+testLecture.getString("echeance")+"</td><td>"+testLecture.getString("libelle")+"</td></tr>");
							}
						%>
					
					
				</tbody>
			</table>
						
						
						
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
								<li><a href="#">Votre profil</a></li>
								<li><a href="#">Etc.</a></li>
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
							<p>Vos bons: 1000</p>
							<p>Votre argent: 234 $</p>
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

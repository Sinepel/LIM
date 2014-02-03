<!DOCTYPE html>
<html lang="en">

<head>
		<%@ page pageEncoding="UTF-8" %>
	<%@ page import="java.sql.*" %>
    <%@ page import="javax.servlet.http.*" %>
    <%@ page import="java.net.*" %>    
    <%@ page import="javax.naming.*" %> 
    <%@ page import="javax.sql.*" %>    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Administration - Lille Information Market</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="css/simple-sidebar.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <script src="js/jquery-1.10.2.js"></script>

    	<script type="text/javascript" src="../js/morris.js"></script>
	<script type="text/javascript" src="../js/raphael.js"></script>
	<script type="text/javascript" src="../js/jquery.ui.datepicker.min.js"></script>
	<link href="../css/morris.css" rel="stylesheet">

</head>

<body>

<script>
$( document ).ready(function() {
	

	$.getJSON(
		"../GraphiqueOrdresJour",  
		function(res){
			new Morris.Line({
		 		element: 'ordresJour',
				data: res,
				xkey: 'jour',
				ykeys: ['valeur'],
				labels: ['Nombre d\'ordres '],
				dateFormat: function(x) { return $.datepicker.formatDate("dd/mm/yy", new Date(x)); }
			});
		}
	)
});
</script>
    <div id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand"><a href="<%= request.getContextPath() %>" title="Retour au site">Lille Information Market</a>
                </li>
                <li><a href="index.jsp">Index</a>
                </li>
                <li><a href="manageMarket.jsp">Marchés</a>
                </li>
                <li><a href="manageCat.jsp">Catégories</a>
                </li>
                <li><a href="manageMembers.jsp">Membres</a>
                </li>
                
            </ul>
        </div>

        <!-- Page content -->
        <div id="page-content-wrapper">
            <div class="content-header">
                <h1>
                    <a id="menu-toggle" href="#" class="btn btn-default"><i class="icon-reorder"></i></a>Administration</h1>
            </div>
            <!-- Keep all page content within the page-content inset div! -->
            <div class="page-content inset">
                <div class="row">
                    <div class="col-md-12">
                        <p class="lead">Bienvenue sur l'interface d'administration du Lille Information Market. Celui-ci vous permettra de gérer, simplement, le site, les membres et les informations.</p>
                    </div>
							<%
							Context iniCtx = new InitialContext();
							Context envCtx = (Context) iniCtx.lookup("java:comp/env");
							DataSource ds = (DataSource) envCtx.lookup("LIM_POOL");
							Connection con = ds.getConnection();
							//Préparation de la requete
							Statement stmt= con.createStatement();
							PreparedStatement totalUser = con.prepareStatement("Select count(*) as totalUser from utilisateur;");
							ResultSet rs = totalUser.executeQuery();
							rs.next();
							String totalUsers = rs.getString("totalUser");
							
							PreparedStatement totalInfo = con.prepareStatement("Select count(*) as totalInfo from information WHERE etat='N';");
							rs = totalInfo.executeQuery();
							rs.next();
							String totalInfos = rs.getString("totalInfo");
							
							
							PreparedStatement totalOrdre = con.prepareStatement("Select count(*) as totalOrdre from ordre WHERE date_achat=now();");
							rs = totalOrdre.executeQuery();
							rs.next();
							String ordresToday= rs.getString("totalOrdre");
							con.close();
							
							%>
                    <div class="col-md-6">
						<h3>Statistiques globales</h3>
                        <div class="well">
							<p>Nombre d'utilisateurs: <%= totalUsers %></p>
							<p>Nombre d'informations en cours: <%= totalInfos %></p>
							<p>Nombre d'ordres passés aujourd'hui: <%= ordresToday %></p>
							
                        </div>
                    </div>
                    <div class="col-md-12">
						<h3>Nombre d'ordre passé par jour (7 derniers jours)</h3>
                        
                        <div id="ordresJour"></div>
                        
                    </div>
                    
                </div>
            </div>
        </div>

    </div>

    <!-- JavaScript -->
    <script src="js/bootstrap.js"></script>

    <!-- Custom JavaScript for the Menu Toggle -->
    <script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("active");
    });
    </script>
</body>

</html>

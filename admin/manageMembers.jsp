<!DOCTYPE html>
<html lang="en">

<head>
	<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
    <%@ page import="javax.servlet.http.*" %>
    <%@ page import="java.net.*" %>    
    <%@ page import="javax.naming.*" %>
    <%@ page import="javax.sql.*" %>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Administration - Lille Information Market</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="css/simple-sidebar.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">

</head>

<body>

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
                    
                    <div class="col-md-6"><h3>Listes des membres</h3>
                        <p class="well">
							<%//Récupération du POOL (LIM_POOL)
							Context iniCtx = new InitialContext();
							Context envCtx = (Context) iniCtx.lookup("java:comp/env");
							DataSource ds = (DataSource) envCtx.lookup("LIM_POOL");
							Connection con = ds.getConnection();
							//Préparation de la requete
							Statement stmt= con.createStatement();
							PreparedStatement membres = con.prepareStatement("Select * from utilisateur;");

							ResultSet rs=membres.executeQuery();
							while (rs.next())
							{
								out.println(rs.getString("pseudo"));
							}
						
							membres.close();
							rs.close(); 
							stmt.close(); 
							con.close();%>
                        </p>
                    </div>
                    <div class="col-md-6"><h3>Ajouter un membre</h3>
                        <form action="../servlet/CreateUser" method="post" accept-charset="utf-8" class="form well" role="form" id="form">
                    
					<input type="text" id="firstname" name="firstname" value="" class="form-control input-lg" placeholder="Pseudo" required />
                    <input type="text" id="email" name="email" value="" class="form-control input-lg" placeholder="Votre mail" required />
                    <input type="password" name="password" value="" class="form-control input-lg" placeholder="Mot de passe" required />
                    <input type="password" name="confirm_password" value="" class="form-control input-lg" placeholder="Confirmation du mot de passe"   required />                    
                    <div class="row">
						<div class="col-md-6">
							<button class="btn btn-lg btn-primary btn-block signup-btn" type="submit">Créer son compte</button>
						</div>
						<div class="col-md-6">
							<button class="btn btn-lg btn-primary btn-block signup-btn btn-danger" onclick="location.href='<%= request.getContextPath() %>'" type="reset">Annuler</button>
						</div>
                    </div>
			</form>
                    </div>
                   
                </div>
            </div>
        </div>

    </div>

    <!-- JavaScript -->
    <script src="js/jquery-1.10.2.js"></script>
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

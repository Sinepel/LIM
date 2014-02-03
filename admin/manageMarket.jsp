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
     <jsp:useBean id="tool" scope="application" class="tools.BDDTools" />
    <jsp:useBean id="recupUser" scope="request" class="users.UserDataBean" />
    <jsp:useBean id="user" scope="page" class="users.User" />
    <%
    		user = recupUser.getUtilisateur(request.getRemoteUser());
	%>

    <title>Administration - Lille Information Market</title>
    <!-- JavaScript -->
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.js"></script>
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
                   
                    <div class="col-md-6"><h3>Listes des marchés</h3>
                       
							<%//Récupération du POOL (LIM_POOL)
							Context iniCtx = new InitialContext();
							Context envCtx = (Context) iniCtx.lookup("java:comp/env");
							DataSource ds = (DataSource) envCtx.lookup("LIM_POOL");
							Connection con = ds.getConnection();
							//Préparation de la requete
							Statement stmt= con.createStatement();
							PreparedStatement categories = con.prepareStatement("Select id_categorie,libelle FROM categorie;");

							PreparedStatement membres = con.prepareStatement("Select * from information;");
							%><table id="membres" class="table"><%
							ResultSet rs=membres.executeQuery();
							while (rs.next())
							{
								out.println("<tr><td>"+rs.getString("question")+"</td><td>"+rs.getString("echeance")+"</td></tr>");
							}
						
							membres.close();
							%></table>
                        
                    </div>
                    <div class="col-md-6"><h3>Ajouter un marché</h3>
                        	<form id="createMarket" action="../servlet/CreateNewMarket" method="POST" class="form well">
				<input type="hidden" name="createur" id="createur" value="<%= user.getId() %>">
				<label for="information">Entrez l'information</label>
				<input type="text" class="form-control" id="information" name="information" placeholder="Entrez l'information que vous souhaitez" required data-validation-required-message="Vous devez saisir une information">
				
				<label for="inverse">Entrez l'inverse</label>
				 <input type="text" class="form-control" id="inverse" name="inverse" placeholder="Entrez l'inverse" required data-validation-required-message="Vous devez saisir l'inverse">
				
				<label for="date">Entrez la date</label>
				<input type="date" class="form-control" name="date" id="date" required data-validation-required-message="Veuillez saisir une date d'écheance de l'information">
				
				<label>Catégorie</label>
				<select class="form-control" name="categorie" required>
					
					<% 
						rs = categories.executeQuery();
						while (rs.next())
						{
							out.println("<option value="+rs.getString("id_categorie")+">"+rs.getString("libelle")+"</option>");
						}
					
					
					%>
				</select>
				
			  
			  <br>
			  <button type="submit" class="btn btn-default">Créer</button>
			</form>
                    </div>
                   
                </div>
            </div>
        </div>

    </div>




    <!-- Custom JavaScript for the Menu Toggle -->
    <script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("active");
    });
    </script>
</body>

</html>
<% rs.close(); stmt.close(); con.close(); %>

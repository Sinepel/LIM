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

<%!
public int nullIntconv(String str)
{   
    int conv=0;
    if(str==null)
    {
        str="0";
    }
    else if((str.trim()).equals("null"))
    {
        str="0";
    }
    else if(str.equals(""))
    {
        str="0";
    }
    try{
        conv=Integer.parseInt(str);
    }
    catch(Exception e)
    {
    }
    return conv;
}
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
			<h2>Toutes les informations</h2>

<%

    //Récupération du POOL (LIM_POOL)
Context iniCtx = new InitialContext();
Context envCtx = (Context) iniCtx.lookup("java:comp/env");
DataSource ds = (DataSource) envCtx.lookup("LIM_POOL");
Connection conn = ds.getConnection();

    ResultSet rsPagination = null;
    ResultSet rsRowCnt = null;
    
    PreparedStatement psPagination=null;
    PreparedStatement psRowCnt=null;
    
    int iShowRows=5;  // Number of records show on per page
    int iTotalSearchRecords=10;  // Number of pages index shown
    
    int iTotalRows=nullIntconv(request.getParameter("iTotalRows"));
    int iTotalPages=nullIntconv(request.getParameter("iTotalPages"));
    int iPageNo=nullIntconv(request.getParameter("iPageNo"));
    int cPageNo=nullIntconv(request.getParameter("cPageNo"));
    
    int iStartResultNo=0;
    int iEndResultNo=0;
    
    if(iPageNo==0)
    {
        iPageNo=0;
    }
    else{
        iPageNo=Math.abs((iPageNo-1)*iShowRows);
    }
    

    
    String sqlPagination="SELECT * FROM information INNER JOIN categorie ON information.id_categorie = categorie.id_categorie OFFSET "+iPageNo+" LIMIT "+iShowRows+"";

    psPagination=conn.prepareStatement(sqlPagination);
    rsPagination=psPagination.executeQuery();
    
    //// this will count total number of rows
     String sqlRowCnt="SELECT count(*) as cnt from information;";
     psRowCnt=conn.prepareStatement(sqlRowCnt);
     rsRowCnt=psRowCnt.executeQuery();
     
     if(rsRowCnt.next())
      {
         iTotalRows=rsRowCnt.getInt("cnt");
      }
%>
<html>
<head>
<title>Pagination of JSP page</title>

</head>
<body>
<form name="frm">
<input type="hidden" name="iPageNo" value="<%=iPageNo%>">
<input type="hidden" name="cPageNo" value="<%=cPageNo%>">
<input type="hidden" name="iShowRows" value="<%=iShowRows%>">
<table width="100%" cellpadding="0" cellspacing="0" border="0" >
<%  out.println(tool.getHTMLSimpleTable(rsPagination,true,true,false)); %>
<%
  //// calculate next record start record  and end record 
        try{
            if(iTotalRows<(iPageNo+iShowRows))
            {
                iEndResultNo=iTotalRows;
            }
            else
            {
                iEndResultNo=(iPageNo+iShowRows);
            }
           
            iStartResultNo=(iPageNo+1);
            iTotalPages=((int)(Math.ceil((double)iTotalRows/iShowRows)));
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

%>
<tr>
<td colspan="3">
<div align="center">
	<ul class="pagination">
<%
        //// index of pages 
        
        int i=0;
        int cPage=0;
        if(iTotalRows!=0)
        {
			cPage=((int)(Math.ceil((double)iEndResultNo/(iTotalSearchRecords*iShowRows))));
        
			int prePageNo=(cPage*iTotalSearchRecords)-((iTotalSearchRecords-1)+iTotalSearchRecords);
			if((cPage*iTotalSearchRecords)-(iTotalSearchRecords)>0)
			{
				%>
				<li><a href="all.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>"> << Précédent</a></li>
				<%
			}
        
			for(i=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));i<=(cPage*iTotalSearchRecords);i++)
			{
			  if(i==((iPageNo/iShowRows)+1))
			  {
				  %>
				   <li class="active"><a href="all.jsp?iPageNo=<%=i%>"><b><%=i%></b></a></li>
				  <%
				  }
			  else if(i<=iTotalPages)
			  {
				  %>
				   <li><a href="all.jsp?iPageNo=<%=i%>"><%=i%></a></li>
				  <% 
			  }
			}
			if(iTotalPages>iTotalSearchRecords && i<iTotalPages)
			{
				 %>
				 <li><a href="all.jsp?iPageNo=<%=i%>&cPageNo=<%=i%>"> >> Suivant</a></li>
				 <%
			}
		}
		  %>
		</ul>
		
			<p>Information <%=iStartResultNo%> à <%=iEndResultNo%>   sur <%=iTotalRows%> </p>
</form>
</body>
</html>
<%
    try{
         if(psPagination!=null){
             psPagination.close();
         }
         if(rsPagination!=null){
             rsPagination.close();
         }
         
         if(psRowCnt!=null){
             psRowCnt.close();
         }
         if(rsRowCnt!=null){
             rsRowCnt.close();
         }
         
         if(conn!=null){
          conn.close();
         }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>
	</div></div>
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
<div id="footer">
      <div class="container">
      <p class="muted credit">Créé par Constantin Boulanger & Florent Pulcian</p>
      </div>
    </div>

</div>
</body>
</html>

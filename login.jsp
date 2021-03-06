<html>
  <head>
	  	<%@ page pageEncoding="UTF-8" %>
	<% String path = request.getContextPath(); %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - LIM</title>
     <!-- Le styles -->
    <link href="<%=path%>/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
 
    </style>
  </head>
	
  <body>
	  <div class="container"></div>
   <form class="form-signin" action="j_security_check" method="post">
      <h2 class="form-signin-heading">Connexion</h2>
      <input type="text" name="j_username" class="input-block-level" placeholder="Pseudo">
      <input type="password" name="j_password" class="input-block-level" placeholder="Mot de Passe">
      <input class="btn btn-large btn-primary" type="submit">
      <a href="addUser.jsp">Créer un compte</a>
		</div>
  </body>
</html>





<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Connexion - ServletUpload</title>
     <!-- Le styles -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <%@ page pageEncoding="UTF-8" %>
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
	  <div class="container" id="wrap">
	  <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <form action="servlet/CreateUser" method="post" accept-charset="utf-8" class="form" role="form">   <legend>Inscription</legend>
                    <h4>It's free and always will be.</h4>
                    
					<input type="text" name="firstname" value="" class="form-control input-lg" placeholder="Pseudo"  />
                    <input type="text" name="email" value="" class="form-control input-lg" placeholder="Votre mail"  />
                    <input type="password" name="password" value="" class="form-control input-lg" placeholder="Mot de passe"  />
                    <input type="password" name="confirm_password" value="" class="form-control input-lg" placeholder="Confirmation du mot de passe"  />                    
                    <button class="btn btn-lg btn-primary btn-block signup-btn" type="submit">Créer son Compte :)</button>
            </form>          
          </div>
	</div>            
	</div>
</div>
  </body>
</html>


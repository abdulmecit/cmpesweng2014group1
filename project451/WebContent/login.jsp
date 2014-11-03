<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>NuttyTest</title>

    <!-- Bootstrap core CSS -->
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	

    <!-- Add custom CSS here -->
    <style>
        body {margin-top: 60px;}
    </style>

  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href=".">Nutty Test</a>
        </div>
		  <ul class="nav navbar-nav navbar-right">
		    <li><a href="login.jsp">Login</a></li>
		    <li><a href="signup.jsp">Sign Up</a></li>
		  </ul>
        

      </div><!-- /.container -->
    </nav>

    <div class="container">
      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
          <h2>Login</h2>
          <form class="form-horizontal" action="" method="POST">
			  <div class="form-group">
			    <label for="inputEmail" class="col-sm-4 control-label">E-mail:</label>
			    <div class="col-sm-6">
			      <input type="email" class="form-control" id="inputEmail" name="inputEmail" style="width:80%" maxlength="30" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputPassword" class="col-sm-4 control-label">Password:</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="inputPassword" name="inputPassword" style="width:80%" maxlength="30" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-primary" id="loginButton">Login</button>
			    </div>
			  </div>
			  <br>
		      <font color="blue" id="errorMsg"></font>
			</form>
        </div>
      </div>
    </div>  
    
    <script>

	/* 

    $("#registerButton").on('click', function (event) {
    	event.preventDefault();
    	window.location.href = "signup.jsp";
  	}); */	

    $("#loginButton").on('click', function (event) {
    	event.preventDefault(); 
    	$.ajax({
			  type: "POST",
			  url: "LoginServlet",
			  data: { 
				  email: $( "#inputEmail" ).val(), 
				  password: $( "#inputPassword" ).val()
				  }
			})
			.done(function(msg) {
				  document.getElementById('errorMsg').innerHTML = msg;
				  if(msg != "Success!") 
					  alert("Success");
			});
  	});	
	</script>

  </body>
</html>
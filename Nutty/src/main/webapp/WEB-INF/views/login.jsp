<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Nutty</title>

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
          <a class="navbar-brand" href=".">Nutty</a>
        </div>
		  <ul class="nav navbar-nav navbar-right">
		    <% 	if (session.getAttribute("isLogged") == null || ((Boolean)(session.getAttribute("isLogged")) == false)){ %>		  
	    		<li><a href="login">Login</a></li>
	    		<li><a href="signup">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="profile/${user.id}">My Profile</a></li>	    	
	    		<li><a href="logout">Logout</a></li>
	    	<%}%>	    				    
		  </ul>
        

      </div><!-- /.container -->
    </nav>

    <div class="container">
      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
          <h2>Login</h2>
          <form id="user" class="form-horizontal" method="POST">
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
		      <font color="blue">${message.message}</font>
			</form>
        </div>
      </div>
    </div>  

  </body>
</html>
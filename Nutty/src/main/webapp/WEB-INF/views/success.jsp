<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cmpesweng2014.group1.nutty.model.Message"%>
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
	    		<li><a href="logout">Logout</a></li>
	    		<li id="settings" class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a><ul role="menu" class="dropdown-menu"><li id="popular"><a href="/nutty/user/homesettings">Profile Settings</a></li><li id="app"><a href="/nutty/user/preferences">Food Preferences</a></li></li>
	    	<%}%>	    				    
		  </ul>  

      </div><!-- /.container -->
    </nav>
       
   <div class="container">
      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
			<p>${message.message}</p>
			<br>
			<p>Click <a href=".">here</a> to return to the main page.<p>
			<p>Click <a href="addRecipe">here</a> to add recipe.<p>
		</div>
	 </div>
   </div>
</body>
</html>
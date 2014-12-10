<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Nutty</title>

	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
	<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	
	<!-- Autocomplete CSS -->	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<style type="text/css">
		@import url(http://fonts.googleapis.com/css?family=Varela+Round);
	</style>

	<!-- Add custom CSS here -->
	<style>
	
	body {
		margin-top: 80px;
	}
	
	</style>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href=/nutty>Nutty</a>
        </div>
		  <ul class="nav navbar-nav navbar-right">
		    <% 	if (session.getAttribute("isLogged") == null || ((Boolean)(session.getAttribute("isLogged")) == false)){ %>		  
	    		<li><a href="/nutty/login">Login</a></li>
	    		<li><a href="/nutty/signup">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="/nutty/user/profile/${user.id}">My Profile</a></li>	    	
	    		<li><a href="/nutty/logout">Logout</a></li>
	    		<li id="settings" class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a><ul role="menu" class="dropdown-menu"><li id="popular"><a href="/nutty/user/homesettings">Profile Settings</a></li><li id="app"><a href="/nutty/user/preferences">Food Preferences</a></li></li>
	    	<%}%>	    				    
		  </ul>
<!-- 		  <ul class="nav navbar-nav navbar-search navbar-right"> -->
<!--             <input type="text" class="search-query span3" placeholder="Search"><ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins> -->
<!--           </ul> -->
        
      </div><!-- /.container -->
    </nav>
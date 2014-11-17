<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
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
	    		<li><a href="login.jsp">Login</a></li>
	    		<li><a href="signup.jsp">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="logout.jsp">Logout</a></li>
	    	<%}%>	    				    
		  </ul>  

      </div><!-- /.container -->
    </nav>
    
   <div class="container">
      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
			<p>You've successfully signed up.</p>
			<br>
			<p id="elma"></p>
			<br>
			<p>${message}<p>
			<br>
			<p>${message.isSuccess} ${message.message}</p>
			<br>		
			<p>Click <a href="index">here</a> to go back where you've left off.<p>
			<br>
			<p>Click <a href=".">here</a> to return to the main page.<p>
		</div>
	 </div>
   </div>
</body>

<script>  	  
	var msg = "${param.message}";
	alert(msg);
	$(document).ready(function () {
	    $('#elma').html(msg);
	});
</script>
</html>
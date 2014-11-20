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
	    		<li><a href="login">Login</a></li>
	    		<li><a href="signup">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="logout">Logout</a></li>
	    	<%}%>	    				    
		  </ul>
        

      </div><!-- /.container -->
    </nav>

    <div class="container">

      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
          <h2>Settings</h2>
		  <div>
		    <p id="name">Name: ${user.name} <button id="changeName"> <img alt="Edit" src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/256/edit.png" height="12px"> </button></p>		  
			<form:form class="form-horizontal" action="homesettings/changeName" method="POST" modelAttribute="user">
		    	<div class="formName">
		    	</div>
		      	<br>
			</form:form>
		  <div>
		    <p id="surname">Surname: ${user.surname} <button id="changeSurname"> <img alt="Edit" src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/256/edit.png" height="12px"> </button></p>
			<form:form class="form-horizontal" method="POST" modelAttribute="user">
		    	<div class="formSurname"></div>
		      	<br>
			</form:form>		  
		  </div>
		  <div>
		    <p id="email">E-mail: ${user.email} <button id="changeEmail"> <img alt="Edit" src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/256/edit.png" height="12px"> </button></p>
		    <form:form class="form-horizontal" method="POST" modelAttribute="user">
		    	<div class="formEmail">
		    	</div>
		      	<br>
			</form:form>
		  </div>
		  <div>
		    <p id="password">Password: ******* <button> <img alt="Edit" src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/256/edit.png" height="12px"> </button></p>
		  	<form:form class="form-horizontal" method="POST" modelAttribute="user">
		    	<div class="formPassword">
		    	</div>
		      	<br>
			</form:form>
		  </div>
		  <div>
		    <p id="birthday">Birthday: ${user.birthday} <button> <img alt="Edit" src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/256/edit.png" height="12px"> </button></p>
		  	<form:form class="form-horizontal" method="POST" modelAttribute="user">
		    	<div class="formBirthday">
		    	</div>
		      	<br>
			</form:form>
		  </div>
		  <div>
		    <p id="gender">Gender: ${user.gender} <button> <img alt="Edit" src="https://cdn2.iconfinder.com/data/icons/windows-8-metro-style/256/edit.png" height="12px"> </button></p>
		  	<form:form class="form-horizontal" method="POST" modelAttribute="user">
		    	<div class="formGender">
		    	</div>
		      	<br>
			</form:form>
		  </div>
		  <br>
	      <font color="blue">${message.message}</font>
        </div>
      </div>
	</div>
	
	<script type="text/javascript">
	
	$(document).ready(function () {
		$( ".formName" ).fadeOut();
		$( ".formName" ).append("<div class='form-group'><label for='inputName' class='col-sm-4 control-label'>Name:</label><div class='col-sm-6'><input type='text' class='form-control' id='inputName' name='inputName' style='width:80%' maxlength='30' value='${user.name}' required></div></div><div class='form-group'><div class='col-sm-offset-2 col-sm-10'><button type='submit' class='btn btn-primary' id='changeNameApply'>Apply</button></div></div>");
		$( ".formSurname" ).fadeOut();
		$( ".formSurname" ).append("<div class='form-group'><label for='inputSurname' class='col-sm-4 control-label'>Surname:</label><div class='col-sm-6'><input type='text' class='form-control' id='inputSurname' name='inputSurname' style='width:80%' maxlength='30' value='${user.surname}' required></div></div><div class='form-group'><div class='col-sm-offset-2 col-sm-10'><button type='submit' class='btn btn-primary' id='changeSurnameApply'>Apply</button></div></div>");
		$( ".formEmail" ).fadeOut();
		$( ".formEmail" ).append("<div class='form-group'><label for='inputEmail' class='col-sm-4 control-label'>E-mail:</label><div class='col-sm-6'><input type='email' class='form-control' id='inputEmail' name='inputEmail' style='width:80%' maxlength='30' value='${user.email}' required></div></div><div class='form-group'><div class='col-sm-offset-2 col-sm-10'><button type='submit' class='btn btn-primary' id='changeEmailApply'>Apply</button></div></div>");
	});
	
	var changeNameOpen;	
	$("#changeName").on('click', function (event) {
		if(!changeNameOpen){
			changeNameOpen = true;	
			$( ".formName" ).fadeIn();
		}else{
			changeNameOpen = false;
			$( ".formName" ).fadeOut();
		}
	});
	
	var changeSurnameOpen;
	$("#changeSurname").on('click', function (event) {
		if(!changeSurnameOpen){
			changeSurnameOpen = true;	
			$( ".formSurname" ).fadeIn();
		}else{
			changeSurnameOpen = false;
			$( ".formSurname" ).fadeOut();
		}
	});
	
	var changeEmailOpen;
	$("#changeEmail").on('click', function (event) {
		if(!changeEmailOpen){
			changeEmailOpen = true;	
			$( ".formEmail" ).fadeIn();
		}else{
			changeEmailOpen = false;
			$( ".formEmail" ).fadeOut();
		}
	});
	
	</script>
</body>
</html>
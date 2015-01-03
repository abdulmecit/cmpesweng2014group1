<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true"/>
    <title>Nutty</title>
    
  </head>
  <body>
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
			      
			      <!--
					  Below we include the Login Button social plugin. This button uses
					  the JavaScript SDK to present a graphical Login button that triggers
					  the FB.login() function when clicked.
					-->
					
					<fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
					</fb:login-button>
					
					<div id="status">
					</div>			      
			    	<p><a href="javascript:forgot()">Forgot Password?</a></p>
			  	</div>
			 </div>
	  	</form>      
	  	
	  	<form:form class="form-horizontal" action="forgotPass" 
				method="POST">
			<div class="formEmail">
				<div>
				    <label for="email" class="col-sm-4 control-label">E-mail:</label>
				    <div class="col-sm-6">
				      <input type="email" class="form-control" id="email" name="email" style="width:80%" maxlength="30">
				      <input type="hidden" class="form-control" id="user_id" name="user_id" value="${user.id}">
				    </div>
				  </div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary">Send E-mail</button>
					</div>
				</div>
			</div>
		</form:form>
	  	<br>
      	<font color="blue">${message.message}</font>
		</div>		
      </div>
    </div>

  </body>
  
  <script type="text/javascript">
  
  $(document).ready(function() {
		$(".formEmail").hide();
	});
  
  function forgot(){
	  var email = "";
	  if($('#inputEmail').val() != null){
		  email = $('#inputEmail').val();
	  }
	  $('#email').val(email);
	  $(".formEmail").fadeIn();
  }
  
  </script>
  
</html>
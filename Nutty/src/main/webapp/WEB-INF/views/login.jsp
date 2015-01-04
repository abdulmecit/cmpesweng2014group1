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
  	  var login=false;
 	  // This is called with the results from from FB.getLoginStatus().
 	  function statusChangeCallback(response) {
 		  if(login){
  			 testAPI();
 		  }
 		  login = true;
 	  }
 	  
 	  // This function is called when someone finishes with the Login
 	  // Button.  See the onlogin handler attached to it in the sample
 	  // code below.
 	  function checkLoginState() {
 	    FB.getLoginStatus(function(response) {
 	      statusChangeCallback(response);
 	    });
 	  }

 	  window.fbAsyncInit = function() {
 	  FB.init({
 	    appId      : '595039627289255',
 	    cookie     : false,  // enable cookies to allow the server to access 
 	                        // the session
 	    xfbml      : true,  // parse social plugins on this page
 	    version    : 'v2.1' // use version 2.1
 	  });

 	  // Now that we've initialized the JavaScript SDK, we call 
 	  // FB.getLoginStatus().  This function gets the state of the
 	  // person visiting this page and can return one of three states to
 	  // the callback you provide.  They can be:
 	  //
 	  // 1. Logged into your app ('connected')
 	  // 2. Logged into Facebook, but not your app ('not_authorized')
 	  // 3. Not logged into Facebook and can't tell if they are logged into
 	  //    your app or not.
 	  //
 	  // These three cases are handled in the callback function.

 	  FB.getLoginStatus(function(response) {
 	    statusChangeCallback();
 	  });

 	  };

 	  // Load the SDK asynchronously
 	  (function(d, s, id) {
 	    var js, fjs = d.getElementsByTagName(s)[0];
 	    if (d.getElementById(id)) return;
 	    js = d.createElement(s); js.id = id;
 	    js.src = "//connect.facebook.net/en_US/sdk.js";
 	    fjs.parentNode.insertBefore(js, fjs);
 	  }(document, 'script', 'facebook-jssdk'));
 	  
 	  // Here we run a very simple test of the Graph API after login is
 	  // successful.  See statusChangeCallback() for when this call is made.
 	  function testAPI() {
 	    console.log('Welcome!  Fetching your information.... ');
 	    FB.api('/me', function(response) {
 	      console.log('Successful login for: ' + response.name);
// 	       document.getElementById('status').innerHTML =
// 	         'Thanks for logging in, ' + response.name + '!';
 		var name = response.first_name;
 		var surname = response.last_name;
 		var gender;
 		if(response.gender == "male"){
 			gender=0;
 		}else{
 			gender=1;
 		}
 		var email = response.email;
 		var pass = "facebook" + response.id;
 		
 		$.ajax({
 			type: "POST",
 			url: "singUpOrLogin",
 			data: {
 				email:email
 			}
 			}).done(function(response) {
 				if(response == '0'){ //signup
 					$.ajax({
 						  type: "POST",
 						  url: "signup",
 						  data: { 
 							  inputName:name,
 							  inputSurname:surname,
 							  inputEmail: email, 
 							  inputPassword1: pass,
 							  gender: gender,
 							  birthday_year: 0,
 							  birthday_month: 0,
 							  birthday_day:0,
 							  link:"http://i.imgur.com/YyzTO03.jpg"
 							  }
 						})
 						.done(function(session) {
 							window.location.href = "success";
 						});
 				}else{
 					$.ajax({
 						type: "POST",
 					  	url: "login",
 					  	data: { 
 							inputEmail: email,
 							inputPassword: pass
 						}
 					})
 					.done(function(session) {
 						window.location.href = "success";
 					});
 				}
 			});
 		
 		console.log(response);
 	    });
 	  }
  </script>
  
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true"/>
    <title>Nutty</title>
    
    <script>
  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.,
      testAPI();
    } else if (response.status === 'not_authorized') {
      // The person is logged into Facebook, but not your app.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into Facebook.';
    }
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
    cookie     : true,  // enable cookies to allow the server to access 
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
    statusChangeCallback(response);
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
      console.log('Successful login for: ' + response);
//       document.getElementById('status').innerHTML =
//         'Thanks for logging in, ' + response.name + '!';
	var name = response.first_name;
	var surname = response.last_name;
	var gender = response.gender;
	var email = response.email;
	var pass = "facebook" + response.id;
	
    });
  }
</script>
    
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
			  	</div>  <!-- TODO forgotPass isminde bi controller lazim -->
			  	<form:form class="form-horizontal" action="forgotPass" 
						method="POST">
					<div class="formEmail">
						<div>
						    <label for="email" class="col-sm-4 control-label">E-mail:</label>
						    <div class="col-sm-6">
						      <input type="email" class="form-control" id="email" name="email" style="width:80%" maxlength="30">
						    </div>
						  </div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary">Send E-mail</button>
							</div>
						</div>
					</div>
				</form:form>
			  </div>
			  <br>
		      <font color="blue">${message.message}</font>
			</form>
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
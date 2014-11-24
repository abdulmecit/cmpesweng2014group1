<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>Nutty</title>

<!-- Bootstrap core CSS -->
<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>


<!-- Add custom CSS here -->
<style>
body {
	margin-top: 80px;
}
</style>

</head>

<body>

	<!------------------------ navigation bar --------------------------->

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href=".">Nutty</a>
		</div>
		<ul class="nav navbar-nav navbar-right">
			<%
				if (session.getAttribute("user_id") == null
						|| Integer.valueOf(session.getAttribute("user_id")
								.toString()) == 0) {
			%>
			<li><a href="login.jsp">Login</a></li>
			<li><a href="signup.jsp">Sign Up</a></li>
			<%
				} else {
			%>
			<li><a href="ownProfile.jsp">My Profile</a></li>
			<li><a href="logout.jsp">Logout</a></li>
			<%
				}
			%>
		</ul>
	</div>
	</nav>

	<div class="container">
		<h3 align="center">Add Recipe</h3>
		<div class="row row well">
			<form method="POST">
				<!------------------------  Get Directions  --------------------------->

				<div class="col-xs-6">
					<h4 align="left">Directions</h4>
					<input type="text" name="myInputs[]"
						style="width: 300px; height: 300px"">
				</div>
				<!------------------------  Get Ingredients  --------------------------->
				<div class="col-xs-6">
					<h4 align="left">Amount - Ingredients</h4>

					<div id="dynamicInput">
						<p>
							<input type="text" name="myInputs[]"
								placeholder="5g,2 pounds,etc." style="width: 70px;""> <input
								type="text" name="myInputs[]" placeholder="sugar,etc."
								style="width: 200px;">
						</p>
						<p>
							<input type="text" name="myInputs[]"
								placeholder="5g,2 pounds,etc." style="width: 70px;""> <input
								type="text" name="myInputs[]" placeholder="sugar,etc."
								style="width: 200px;">
						</p>
						<p>
							<input type="text" name="myInputs[]"
								placeholder="5g,2 pounds,etc." style="width: 70px;""> <input
								type="text" name="myInputs[]" placeholder="sugar,etc."
								style="width: 200px;">
						</p>
						<p>
							<input type="text" name="myInputs[]"
								placeholder="5g,2 pounds,etc." style="width: 70px;""> <input
								type="text" name="myInputs[]" placeholder="sugar,etc."
								style="width: 200px;">
						</p>
						<p>
							<input type="text" name="myInputs[]"
								placeholder="5g,2 pounds,etc." style="width: 70px;""> <input
								type="text" name="myInputs[]" placeholder="sugar,etc."
								style="width: 200px;">
						</p>
					</div>
					<input type="button" value="Add another ingredients"
						class="btn btn-primary" onClick="addInput('dynamicInput');">

					<br> <br> <input type="submit" value="Submit"
						class="btn btn-primary" />
				</div>
			</form>
		</div>
	</div>


	<!---------------------------  Functions  ------------------------------>
	<script type="text/javascript">
		var counter = 1;
		function addInput(divName) {
			var newdiv1 = document.createElement('div');
			//var newdiv2 = document.createElement('div');
			newdiv1.innerHTML = "<p><input type='text' name='myInputs[]' placeholder='5g,2 pounds,etc.' style='width: 70px;'>"
					+ " <input type='text' name='myInputs[]' placeholder='sugar,etc.' style='width: 200px;'></p>";
			document.getElementById(divName).appendChild(newdiv1);
			//document.getElementById(divName).appendChild(newdiv2);
			counter++;
		}
	</script>
	</body>
</html>
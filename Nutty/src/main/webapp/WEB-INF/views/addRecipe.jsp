<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.sql.*"%>
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
		<form method="POST">
			<div class="panel panel-default">
				<div class="panel-heading clearfix">
					<h2 class="panel-title pull-left" style="padding-top: 7.5px;">Create
						New Recipes</h2>
					<input type="submit" value="Submit" class="btn btn-primary"
						style="float: right;">
				</div>

				<div class="panel-body">
					<!------------------------  Get Ingredients  --------------------------->
					<div class="col-sm-6">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h4 class="panel-title pull-left" style="padding-top: 7.5px;">Amount
									/ Ingredients</h4>
								<input type="button" value="Another ingredient"
									class="btn btn-primary"
									onClick="addInput('dynamicInput','dynamicInput2');"
									style="float: right;">
							</div>

							<div class="panel-body">
								<div id="dynamicInput" class="col-sm-4" align="left">
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="2pounds,etc">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="2pounds,etc">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="2pounds,etc">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="2pounds,etc">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="2pounds,etc">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="2pounds,etc">
									</p>
								</div>
								<div id="dynamicInput2" class="col-sm-8" align="left">
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="sugar,etc.">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="sugar,etc.">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="sugar,etc.">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="sugar,etc.">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="sugar,etc.">
									</p>
									<p>
										<input type="text" class="form-control" name="myInputs[]"
											placeholder="sugar,etc.">
									</p>
								</div>
							</div>
						</div>
					</div>

					<!------------------------  Get Directions  --------------------------->

					<div class="col-sm-6">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h4 class="panel-title " style="padding-top: 7.5px;">Directions</h4>
							</div>
							<div class="panel-body">
								<textarea class="form-control" rows="13"></textarea>
							</div>
						</div>
					</div>
				</div>
		</form>


		<!---------------------------  Functions  ------------------------------>
		<script type="text/javascript">
			var counter = 1;
			function addInput(divName, div2) {
				var newdiv1 = document.createElement('div');
				var newdiv2 = document.createElement('div');
				newdiv1.innerHTML = "<p> <input type='text' class='form-control' name='myInputs[]' placeholder='2pounds,etc'> </p>"
				newdiv2.innerHTML = "<p> <input type='text' class='form-control' name='myInputs[]' placeholder='sugar,etc'> </p>";
				document.getElementById(divName).appendChild(newdiv1);
				document.getElementById(div2).appendChild(newdiv2);
				counter++;
			}
		</script>
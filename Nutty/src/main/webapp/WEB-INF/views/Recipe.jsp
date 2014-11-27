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
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet">
<link href="path/to/css/star-rating.min.css" media="all"
	rel="stylesheet" type="text/css" />
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="path/to/js/star-rating.min.js" type="text/javascript"></script>

<!-- Add custom CSS here -->
<style type="text/css">
@import url(http://fonts.googleapis.com/css?family=Varela+Round);

.slides {
	padding: 0;
	width: 457px;
	height: 315px;
	display: block;
	margin: 0 auto;
	position: relative;
}

.slide {
	top: 0;
	opacity: 0;
	width: 457px;
	height: 315px;
	display: block;
	position: absolute;
	transform: scale(0);
	transition: all .7s ease-in-out;
}

.slide img {
	width: 100%;
	height: 100%;
}

.slides * {
	user-select: none;
	-ms-user-select: none;
	-moz-user-select: none;
	-khtml-user-select: none;
	-webkit-user-select: none;
	-webkit-touch-callout: none;
}

.slides input {
	display: none;
}

.slide-container {
	display: block;
}

.nav label {
	width: 50px;
	height: 100%;
	display: none;
	position: absolute;
	opacity: 0;
	z-index: 9;
	cursor: pointer;
	transition: opacity .2s;
	color: #FFF;
	font-size: 56pt;
	text-align: center;
	line-height: 325px;
	font-family: "Varela Round", sans-serif;
	background-color: rgba(255, 255, 255, .3);
	text-shadow: 0px 0px 15px rgb(119, 119, 119);
}

.slide:hover+.nav label {
	opacity: 0.5;
}

.nav label:hover {
	opacity: 1;
}

.nav .next {
	right: 0;
}

input:checked+.slide-container  .slide {
	opacity: 1;
	transform: scale(1);
	transition: opacity 1s ease-in-out;
}

input:checked+.slide-container .nav label {
	display: block;
}

.nav-dots {
	width: 100%;
	bottom: 6px;
	height: 11px;
	display: block;
	position: absolute;
	text-align: center;
}

.nav-dots .nav-dot {
	top: -5px;
	width: 11px;
	height: 11px;
	margin: 0 4px;
	position: relative;
	border-radius: 100%;
	display: inline-block;
	background-color: rgba(0, 0, 0, 0.6);
}

.nav-dots .nav-dot:hover {
	cursor: pointer;
	background-color: rgba(0, 0, 0, 0.8);
}

input#img-1:checked ~ .nav-dots label#img-dot-1, input#img-2:checked ~
	.nav-dots label#img-dot-2, input#img-3:checked ~ .nav-dots label#img-dot-3,
	input#img-4:checked ~ .nav-dots label#img-dot-4, input#img-5:checked ~
	.nav-dots label#img-dot-5, input#img-6:checked ~ .nav-dots label#img-dot-6
	{
	background: rgba(0, 0, 0, 0.8);
}

body {
	margin-top: 80px;
}

#info-wrap {
	/* overflow to handle inner floating block */
	overflow: hidden;
}

.info {
	width: 50%;
	float: left;
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



	<!------------------------ star --------------------------->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<h2 class="panel-title pull-center" style="padding-top: 10px;">RECIPE
					*****</h2>
				by Chef John
			</div>
			<div class="panel-body">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="col-sm-6 col-centered">
							<ul class="slides">
								<input type="radio" name="radio-btn" id="img-1" checked />
								<li class="slide-container">
									<div class="slide">
										<img
											src="http://blog.fotografium.com/wp-content/uploads/2011/09/318.jpg" />
									</div>
									<div class="nav">
										<label for="img-6" class="prev">&#x2039;</label> <label
											for="img-2" class="next">&#x203a;</label>
									</div>
								</li>

								<input type="radio" name="radio-btn" id="img-2" />
								<li class="slide-container">
									<div class="slide">
										<img
											src="https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRikkxvA53g0Vc8XJuH2uIvWBMq8FGj5yFl5Gz-gLRJsk0XhwE" />
									</div>
									<div class="nav">
										<label for="img-1" class="prev">&#x2039;</label> <label
											for="img-3" class="next">&#x203a;</label>
									</div>
								</li>

								<input type="radio" name="radio-btn" id="img-3" />
								<li class="slide-container">
									<div class="slide">
										<img
											src="http://blog.fotografium.com/wp-content/uploads/2011/09/1218.jpg" />
									</div>
									<div class="nav">
										<label for="img-2" class="prev">&#x2039;</label> <label
											for="img-4" class="next">&#x203a;</label>
									</div>
								</li>

								<input type="radio" name="radio-btn" id="img-4" />
								<li class="slide-container">
									<div class="slide">
										<img
											src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3mwbgHPPWvhTK9lRaqmsLDG3nDJ-SxqjzgPocywPCPpMghR1dNw" />
									</div>
									<div class="nav">
										<label for="img-3" class="prev">&#x2039;</label> <label
											for="img-5" class="next">&#x203a;</label>
									</div>
								</li>

								<!-- <a> taginin içine buton koydum onun içine de img, dolayısıyle butonun yönlendirceği adresi href ile verebiliyoruz. -->
								<input type="radio" name="radio-btn" id="img-5" />
								<li class="slide-container">
									<div class="slide">
										<img src="http://i.imgur.com/If2Ma.jpg" />
									</div>
									<div class="nav">
										<label for="img-4" class="prev">&#x2039;</label> <label
											for="img-6" class="next">&#x203a;</label>
									</div>
								</li>

								<input type="radio" name="radio-btn" id="img-6" />
								<li class="slide-container">
									<div class="slide">
										<img src="http://i.imgur.com/If2Ma.jpg" />
									</div>
									<div class="nav">
										<label for="img-5" class="prev">&#x2039;</label> <label
											for="img-1" class="next">&#x203a;</label>
									</div>
								</li>

								<li class="nav-dots"><label for="img-1" class="nav-dot"
									id="img-dot-1"></label> <label for="img-2" class="nav-dot"
									id="img-dot-2"></label> <label for="img-3" class="nav-dot"
									id="img-dot-3"></label> <label for="img-4" class="nav-dot"
									id="img-dot-4"></label> <label for="img-5" class="nav-dot"
									id="img-dot-5"></label> <label for="img-6" class="nav-dot"
									id="img-dot-6"></label></li>
							</ul>
						</div>
						<div class="col-sm-6 col-centered">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h2 class="panel-title text-center" style="padding-top: 7.5px;">RATING
									</h2>
								</div>
								<div class="panel-body" style="height: 270px">

									<div class="col-sm-6 col-centered">
										<div class="btn-group btn-info">
											<button type="button" class="btn btn-info btn-lg">Health</button>
											<button type="button"
												class="btn dropdown-toggle btn-lg btn-info"
												name="healthRate" data-toggle="dropdown"
												aria-expanded="false">
												<span class="caret"></span> <span class="sr-only"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="#">Health : 5</a></li>
												<li><a href="#">Health : 4</a></li>
												<li><a href="#">Health : 3</a></li>
												<li><a href="#">Health : 2</a></li>
												<li><a href="#">Health : 1</a></li>
											</ul>
										</div>

										<br> <br>

										<div class="btn-group btn-warning">
											<button type="button" class="btn btn-warning btn-lg">Taste</button>
											<button type="button"
												class="btn dropdown-toggle btn-lg btn-warning"
												name="tasteRate" data-toggle="dropdown"
												aria-expanded="false">
												<span class="caret"></span> <span class="sr-only"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="#">Taste : 5</a></li>
												<li><a href="#">Taste : 4</a></li>
												<li><a href="#">Taste : 3</a></li>
												<li><a href="#">Taste : 2</a></li>
												<li><a href="#">Taste : 1</a></li>
											</ul>
										</div>

										<br> <br>

										<div class="btn-group btn-success">
											<button type="button" class="btn btn-success btn-lg">Ease</button>
											<button type="button"
												class="btn dropdown-toggle btn-lg btn-success"
												name="easeRate" data-toggle="dropdown" aria-expanded="false">
												<span class="caret"></span> <span class="sr-only"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="#">Ease : 5</a></li>
												<li><a href="#">Ease : 4</a></li>
												<li><a href="#">Ease : 3</a></li>
												<li><a href="#">Ease : 2</a></li>
												<li><a href="#">Ease : 1</a></li>
											</ul>
										</div>
										<br> <br>


										<div class="btn-group btn-danger">
											<button type="button" class="btn btn-danger btn-lg">
												Cost</button>
											<button type="button"
												class="btn dropdown-toggle btn-lg btn-danger"
												name="costRate" data-toggle="dropdown" aria-expanded="false">
												<span class="caret"></span> <span class="sr-only"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="#">Cost : 5</a></li>
												<li><a href="#">Cost : 4</a></li>
												<li><a href="#">Cost : 3</a></li>
												<li><a href="#">Cost : 2</a></li>
												<li><a href="#">Cost : 1</a></li>
											</ul>

										</div>
									</div>
									<div class="col-sm-6 col-centered">
										<!-- Standard button -->
										<button type="button" class="btn btn-default">
											<span class="glyphicons glyphicons-ok-2"> </span> Eat
										</button>
										<!-- Provides extra visual weight and identifies the primary action in a set of buttons -->
										<button type="button" class="btn btn-primary">
											<span class="glyphicons glyphicons-thumbs-up"></span> Like
										</button>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col-sm-4">
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h4 class="panel-title pull-left" style="padding-top: 7.5px;">Versions</h4>
							<input type="button" value="Create New version"
								class="btn btn-primary" onClick="addInput('dynamicInput');"
								style="float: right;">
						</div>
						<div class="panel-body"></div>
					</div>
				</div>
				<div class="col-sm-4">
					<div id="info-wrap">
						<div class="info" align="center">
							<p>&nbsp;</p>
							<h3>Ingredients:</h3>
							<!--		<p><h8 id="full_name"></h8></p>  -->
							<h5>.</h5>
							<h5>.</h5>
							<h5>.</h5>
							<h3>Directions:</h3>
							<h5>.</h5>
							<h5>.</h5>
							<h5>.</h5>
						</div>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h4 class="panel-title pull-left" style="padding-top: 7.5px;">Comments</h4>
							<input type="button" value="Comment" class="btn btn-primary"
								onClick="addInput('dynamicInput');" style="float: right;">
						</div>
						<div class="panel-body"></div>
					</div>
				</div>
			</div>
		</div>


		<script type="text/javascript">
			var healtRate = 0; // change recipe healthRate
			var costRate = 0;
			var tasteRate = 0;
			var easeRate = 0;

			$(document)
					.ready(
							function() {
								if (healtRate != 0) {
									$("#healthRate")
											.parents('.btn-group')
											.find('.dropdown-toggle')
											.html(
													rate
															+ ' <span class="caret"></span>');
									document.getElementById('deneme').innerHTML = newSelText[1];
								}
								if (costRate != 0) {

								}
								if (tasteRate != 0) {

								}
								if (healtRate != 0) {

								}
							});
			$(".dropdown-menu li a").click(
					function() {
						var selText = $(this).text();
						var newSelText = selText.split(' : ');
						var name = newSelText[0]
						var rate = newSelText[1];
						$(this).parents('.btn-group').find('.dropdown-toggle')
								.html(rate + ' <span class="caret"></span>');
						if (name == "Health") {
							healtRate = rate;
						} else if (name == "Cost") {
							costRate = rate;
						} else if (name == "Taste") {
							tasteRate = rate;
						} else if (name == "Ease") {
							easeRate = rate;
						}
					});
		</script>
</html>
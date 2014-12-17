<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style>
body {
	margin-top: 60px;
}

@import url(http://fonts.googleapis.com/css?family=Varela+Round);

.slides {
	padding: 0;
	width: 490px;
	height: 360px;
	display: block;
	margin: 0 auto;
	position: relative;
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

.slide {
	top: 0;
	opacity: 0;
	width: 490px;
	height: 300px;
	display: block;
	position: absolute;
	transform: scale(0);
	transition: all .7s ease-in-out;
}

.slide img {
	width: 100%;
	height: 100%;
}

.nav label {
	width: 80px;
	height: 100%;
	display: none;
	position: absolute;
	opacity: 0;
	z-index: 9;
	cursor: pointer;
	transition: opacity .2s;
	color: #FFF;
	font-size: 80pt;
	text-align: center;
	line-height: 380px;
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
	bottom: 9px;
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
	input#img-4:checked ~ .nav-dots label#img-dot-4 {
	background: rgba(0, 0, 0, 0.8);
}
</style>

</head>
<body>
	<div class="container" id="searchResults"
		style="background-color: #eeeeee;"></div>

	<br>
	<br>

	<div class="container" style="height: 400px">
		<ul class="slides">
			<input type="radio" name="radio-btn" id="img-1" checked />
			<li class="slide-container">
				<div class="slide">
					<h3 align="center">Delicious</h3>
					<a href="recipe/1"><img
						src="http://2.bp.blogspot.com/-fHFQXiIq6IQ/TegYuDrnq-I/AAAAAAAAQAQ/AaB71suJeFk/s1600/Kobe+Beef-0094.jpg" /></a>
				</div>
				<div class="nav">
					<label for="img-4" class="prev">&#x2039;</label> <label for="img-2"
						class="next">&#x203a;</label>
				</div>
			</li>

			<input type="radio" name="radio-btn" id="img-2" />
			<li class="slide-container">
				<div class="slide">
					<h3 align="center">Healthy</h3>
					<a href="recipe/1"><img
						src="http://sandiegobargainmama.com/wp-content/uploads/2011/09/balanced-meal.png" /></a>
				</div>
				<div class="nav">
					<label for="img-1" class="prev">&#x2039;</label> <label for="img-3"
						class="next">&#x203a;</label>
				</div>
			</li>

			<input type="radio" name="radio-btn" id="img-3" />
			<li class="slide-container">
				<div class="slide">
					<h3 align="center">Easy</h3>
					<a href="recipe/1"><img
						src="http://www.damakdiyari.com.tr/content/images/thumbs/0000588_sahanda-menemen.jpeg" /></a>
				</div>
				<div class="nav">
					<label for="img-2" class="prev">&#x2039;</label> <label for="img-4"
						class="next">&#x203a;</label>
				</div>
			</li>

			<input type="radio" name="radio-btn" id="img-4" />
			<li class="slide-container">
				<div class="slide">
					<h3 align="center">Cheap</h3>
					<a href="recipe/1"><img
						src="http://s3.amazonaws.com/gmi-digital-library/41e7d5f1-05de-415e-a31a-3c48d828d81a.jpg" /></a>
				</div>
				<div class="nav">
					<label for="img-3" class="prev">&#x2039;</label> <label for="img-1"
						class="next">&#x203a;</label>
				</div>
			</li>

			<li class="nav-dots"><label for="img-1" class="nav-dot"
				id="img-dot-1"></label> <label for="img-2" class="nav-dot"
				id="img-dot-2"></label> <label for="img-3" class="nav-dot"
				id="img-dot-3"></label> <label for="img-4" class="nav-dot"
				id="img-dot-4"></label></li>
		</ul>
	</div>


	<%
		if (session.getAttribute("isLogged") == null
				|| ((Boolean) (session.getAttribute("isLogged")) == false)) {
		} else {
	%>
	<div class="panel panel-default"
		style="margin-right: 80px; margin-left: 80px">
		<div class="panel-body">
			<div class="row">
				<div class="col-sm-9">

					<div>
						<ul class="nav nav-tabs nav-justified">
							<li role="presentation" class="filter active" id="overall"><a
								class="btn btn-link">News Feed</a></li>
								<li role="presentation" class="filter" id="overall"><a
								class="btn btn-link">Recommendations</a></li>
							<li role="presentation" class="filter" id="taste"><a
								class="btn btn-link">My Recipe</a></li>
							<li role="presentation" class="filter" id="healht"><a
								class="btn btn-link">Recipe From My Friends</a></li>
							<li role="presentation" class="filter" id="cost"><a
								class="btn btn-link">Shared Recipes</a></li>
						</ul>
						<div id="results"></div>
					</div>

				</div>
				<div class="col-sm-3">
					<div class="panel panel-default">
						<div class="panel-body"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
		}
	%>
</body>
<script type="text/javascript">
	var searchFilter;
	$(".filter").click(function() {
		$(this).addClass("active").siblings().removeClass("active");
		searchFilter = this.id;
	});
</script>
</html>

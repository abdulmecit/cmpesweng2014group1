<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE HTML>
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
	max-width: 457px;
	max-height: 315px;
	width: 100%;
	height: auto;
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

#diveder {
	width: 5px;
	height: auto;
	display: inline-block;
	float: right;
}

.wrap {
	/* force the div to properly contain the floated images: */
	position: relative;
	clear: none;
	overflow: hidden;
	float: left;
	margin-left: 10px;
	margin-right: 10px
}

.wrap img {
	position: relative;
	width: 80px;
	height: 70px
}

.wrap .desc {
	display: block;
	position: absolute;
	width: 100%;
	top: 10%;
	text-align: center;
	font-size: 14;
	font-style: oblique;
}
</style>


</head>

<body>

	<!------------------------ navigation bar --------------------------->

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="../">Nutty</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<%
					if (session.getAttribute("isLogged") == null
							|| ((Boolean) (session.getAttribute("isLogged")) == false)) {
				%>
				<li><a href="../login">Login</a></li>
				<li><a href="../signup">Sign Up</a></li>
				<%
					} else {
				%>
				<li><a href="../user/profile/${user.id}">My Profile</a></li>
				<li><a href="../logout">Logout</a></li>
				<li id="settings" class="dropdown"><a href="#"
					data-toggle="dropdown" class="dropdown-toggle"><span
						class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a>
					<ul role="menu" class="dropdown-menu">
						<li id="popular"><a href="../user/homesettings">Profile
								Settings</a></li>
						<li id="app"><a href="../user/preferences">Food
								Preferences</a></li></li>
				<%
					}
				%>
			</ul>

		</div>
		<!-- /.container -->
	</nav>

	<!------------------------ star --------------------------->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<div class="wrap">
					<img
						src="https://dl.dropboxusercontent.com/u/45511253/one-star.png" />
					<h3 class="desc"></h3>
				</div>

				<button type="button" class="btn btn-primary" value="Eat" id="Eat"
					style="float: right; margin-right: 15 px; margin-top: 18px;">
					<span id="textEat" class="ui-button-text">Eat &nbsp </span> <span
						id="eatCheck" class="glyphicon glyphicon-check"
						style="visibility: hidden;"></span>
					<!-- caret for arrow. ui-button-text for button text visible; -->
				</button>

				<button type="button" class="btn btn-primary" value="Like" id="Like"
					style="float: right; margin-right: 15 px; margin-top: 18px;">
					<span id="textLike" class="ui-button-text">Like &nbsp</span> <span
						id="likeCheck" class="glyphicon glyphicon-check"
						style="visibility: hidden;"></span>
					<!-- caret for arrow. ui-button-text for button text -->
				</button>

				<h2 class="panel-title pull-center"
					style="margin-top: 25px; font-size: 24px">
					${recipe.name} <small><em>by <a
							href="../user/profile/${ownerId}">Chef ${ownerName}</a></em></small>
				</h2>
			</div>
			<div class="panel-body">
				<div class=row>
					<div class="col-sm-6 col-centered">
						<ul class="slides">
							<input type="radio" name="radio-btn" id="img-1" checked />
							<li class="slide-container">
								<div class="slide">
									<img src="${photoUrl}" />
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
							<div class="panel-body">
								<div class="row">
									<!----------------------- Rate Buttons ----------------------------->
									<div class="btn-group btn-info "
										style="margin: 0px 5px 25px 22px;">
										<button type="button" class="btn btn-info btn-lg">Health</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-info" id="healthRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="ui-button-text"></span> <span class="caret"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1</a></li>
										</ul>
									</div>

									<div class="btn-group btn-danger"
										style="margin: 0px 5px 25px 5px;">
										<button type="button" class="btn btn-danger btn-lg">Cost</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-danger" id="costRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="caret"></span> <span class="ui-button-text"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1</a></li>
										</ul>
									</div>

									<div class="btn-group btn-warning"
										style="margin: 0px 5px 25px 5px;">
										<button type="button" class="btn btn-warning btn-lg">Taste</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-warning" id="tasteRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="caret"></span> <span class="ui-button-text"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1</a></li>
										</ul>
									</div>

									<div class="btn-group btn-success"
										style="margin: 0px 22px 25px 5px;">
										<button type="button" class="btn btn-success btn-lg">Ease</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-success" id="easeRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="ui-button-text"></span> <span class="caret"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1</a></li>
										</ul>
									</div>
								</div>
								<!----------------------- End of Rate Buttons ----------------------------->
								<div class="row ">
									<div class="col-sm-6">
										<div class="col-sm-9">
											<br>
											<div id="rate"
												style="width: 100%; height: 100%; margin-up: auto; margin-down: auto; overflow: hidden;">

												<div class="progress">
													<div class="progress-bar progress-bar-warning"
														id="prograssbarTaste" role="progressbar" aria-valuenow="0"
														aria-valuemin="0" aria-valuemax="100"></div>
												</div>

												<div class="progress">
													<div class="progress-bar progress-bar-info"
														id="prograssbarHealth" role="progressbar"
														aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
														style="width: 40%"></div>
												</div>
												<div class="progress">
													<div class="progress-bar progress-bar-danger"
														id="prograssbarCost" role="progressbar" aria-valuenow="0"
														aria-valuemin="0" aria-valuemax="100"></div>
												</div>

												<div class="progress">
													<div class="progress-bar progress-bar-success"
														id="prograssbarEase" role="progressbar" aria-valuenow="0"
														aria-valuemin="0" aria-valuemax="100"></div>
												</div>
											</div>
										</div>
										<div class="col-sm-3">
											<h5 style="margin-top: 12px; text-align: left;">${numOfTasteRate}
												users</h5>
											<h5 style="margin-top: 12px; text-align: left;">${numOfHealthRate}
												users</h5>
											<h5 style="margin-top: 12px; text-align: left;">${numOfCostRate}
												users</h5>
											<h5 style="margin-top: 12px; text-align: left;">${numOfEaseRate}
												users</h5>

										</div>

									</div>
									<div class="col-sm-1"></div>
									<div class="col-sm-5" style="margin: auto;">
										<h5 style="margin-top: 20px;">
											<b>Likes: </b> ${noOfLikes} &nbsp&nbsp Eaten: ${noOfEats}
										</h5>
										<h5>
											<b>Created On:</b> ${recipe.createdDate}
										</h5>
										<h5>
											<b>Last Updated On:</b> ${recipe.updatedDate}
										</h5>
										<h5>
											<b>Tags:</b>

											<c:forEach var="tag" items="${tags}">
												<li><a href=""> ${tag.tag_name}</a></li>
											</c:forEach>

										</h5>
									</div>
								</div>
							</div>
						</div>
					</div>



				</div>
				<br> <br> <br>
				<div class=row>
					<div class="col-sm-4">
						<div class="panel panel-default" style="margin-right: 10px;">

							<div class="panel-heading clearfix">
								<h4 class="panel-title pull-left" style="padding-top: 7.5px;">Versions</h4>
								<input type="button" value="Create New version"
									class="btn btn-primary" onClick="addInput('dynamicInput');"
									style="float: right;">
							</div>

							<div class="panel-body">
								<h4>Recipe Tree</h4>
								<p>ParentId: ${parent.recipe_id}</p>
								<p>ParentName: ${parent.name}</p>
								<p>Children:</p>
								<c:forEach var="child" items="${children}"> Child:  Id: <c:out
										value="${child.recipe_id}" /> Name: <c:out
										value="${child.name}" />
									<br>
								</c:forEach>
							</div>
						</div>
					</div>

					<div class="col-sm-8">

						<div id="info-wrap">
							<button type="button" class="btn btn-primary" value="Share"
								id="Share"
								style="float: right; margin-right: 15 px; margin-left: 18px;">
								<span id="textShare" class="ui-button-text">Share &nbsp </span>
								<span id="shareCheck" class="glyphicon glyphicon-check"
									style="visibility: hidden;"></span>
								<!-- caret for arrow. ui-button-text for button text visible; -->
							</button>

							<div id=recipeDetail>
								<h3>Portion:${recipe.portion} &nbsp&nbsp Total
									Calories:${recipe.total_calorie}</h3>
								<h3>Ingredients</h3>
								<c:forEach var="ingredientAmount" items="${ingredientAmounts}">
									<p>${ingredientAmount.amount}&nbsp&nbsp&nbsp
										${ingredientAmount.ing_name}</p>

								</c:forEach>
							</div>

							<h3>Description</h3>
							<p>${recipe.description}</p>
						</div>
					</div>
				</div>
				<!-- end of row -->
				<br> <br> <br>
				<div class=row>
					<div class="panel panel-default"
						style="margin-right: 10px; margin-left: 10px">
						<div class="panel-heading clearfix">
							<form>
								<textarea placeholder="add a comment.." class="form-control"
									name="comment" rows="3" style="resize: none"></textarea>
								<br>
								<button class="btn btn-primary" type="submit" id="comment"
									style="float: right">Comment</button>
							</form>



						</div>
						<ul class="list-group">
							<div id="comments"></div>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>



<script type="text/javascript">
	var healthRate = '${healthRateOfUser}';
	var costRate = '${costRateOfUser}';
	var tasteRate = '${tasteRateOfUser}';
	var easeRate = '${easeRateOfUser}';
	var eaten = '${eatenOfUser}';
	var liked = '${likeOfUser}';
	var shared = '${shareOfUser}';
	var isLogged = '${isLogged}';

	$(document)
			.ready(
					function() {
						$('#prograssbarTaste').css('width',
								'${avgTasteRate}' * 20 + '%').attr(
								'aria-valuenow', '${avgTasteRate}' * 20);
						$('#prograssbarTaste').text(
								Number('${avgTasteRate}').toFixed(1));

						$('#prograssbarHealth').css('width',
								'${avgHealthRate}' * 20 + '%').attr(
								'aria-valuenow', '${avgHealthRate}' * 20);
						$('#prograssbarHealth').text(
								Number('${avgHealthRate}').toFixed(1));

						$('#prograssbarCost').css('width',
								'${avgCostRate}' * 20 + '%').attr(
								'aria-valuenow', '${avgCostRate}' * 20);
						$('#prograssbarCost').text(
								Number('${avgCostRate}').toFixed(1));

						$('#prograssbarEase').css('width',
								'${avgEaseRate}' * 20 + '%').attr(
								'aria-valuenow', '${avgEaseRate}' * 20);
						$('#prograssbarEase').text(
								Number('${avgEaseRate}').toFixed(1));

						$(".desc").text(Number('${avgOverall}').toFixed(1));

						if (healthRate != 0) {
							$("#healthRate").text(healthRate);
						}
						if (costRate != 0) {
							$("#costRate").text(costRate);
						}
						if (tasteRate != 0) {
							$("#tasteRate").text(tasteRate);
						}
						if (easeRate != 0) {
							$("#easeRate").text(easeRate);
						}
						// if liked 'check' is visible
						if (liked == 1) {
							$("#likeCheck").css('visibility', 'visible');
						} else {
							$("#likeCheck").css('visibility', 'hidden');
						}
						// if eat 'check' is visible
						if (eaten == 1) {
							$("#eatCheck").css('visibility', 'visible');
						} else {
							$("#eatCheck").css('visibility', 'hidden');
						}
						if (shared == 1) {
							$("#shareCheck").css('visibility', 'visible');
						} else {
							$("#shareCheck").css('visibility', 'hidden');
						}
					});

	$(".dropdown-menu li a").click(
			function(e) {
				if (isLogged == 'true') {
					var selText = $(this).parents('.btn-group').find(
							'.dropdown-toggle').attr("id");
					var rate = $(this).text();
					$(this).parents('.btn-group').find('.dropdown-toggle')
							.html(rate);
					if (selText == "healthRate") {
						healthRate = rate;
						updateRate("health_rate", healthRate);
					} else if (selText == "costRate") {
						costRate = rate;
						updateRate("cost_rate", costRate);
					} else if (selText == "tasteRate") {
						tasteRate = rate;
						updateRate("taste_rate", tasteRate);
					} else if (name == "easeRate") {
						easeRate = rate;
						updateRate("ease_rate", easeRate);
					}
				}
			});

	$("#Like").click(function(e) {
		if (isLogged == 'true') {
			if (liked == 0) {
				$("#likeCheck").css('visibility', 'visible');
				liked = 1;
			} else {
				$("#likeCheck").css('visibility', 'hidden');
				liked = 0;
			}
			updateRate("likes", liked);
		}
	});

	$("#Eat").click(function(e) {
		if (isLogged == 'true') {
			if (eaten == 0) {
				$("#eatCheck").css('visibility', 'visible');
				eaten = 1;
			} else {
				$("#eatCheck").css('visibility', 'hidden');
				eaten = 0;
			}
			updateRate("eats", eaten);
		}
	});

	$("#Share").click(function(e) {
		if (isLogged == 'true') {
			if (shared == 0) {
				$("#shareCheck").css('visibility', 'visible');
				shared = 1;
			} else {
				$("#shareCheck").css('visibility', 'hidden');
				shared = 0;
			}
			$.ajax({
				type : "POST",
				url : "../shareRecipe",
				data : {
					changed : 'shares',
					user_id : '${user.id}',
					recipe_id : '${recipe.recipe_id}',
					value : shared
				}
			})
		}
	});

	$(document).ready(
			function() {
				$.ajax({
					type : "POST",
					url : "../recipeComments",
					data : {
						recipeId : '${recipe.recipe_id}',
					}
				}).done(
						function(answer) {
							$("#comments").html("");
							if (answer == "") {
								$("#comments").append("<p>No comments :(</p>");
							} else {
								var recipes = answer.split('|');
								for (i = 1; i < recipes.length; i++) {
									dummy = recipes[i].split('>');
									$("#comments").append(
											"<li class='list-group-item'><b>"
													+ dummy[1] + "</b><p>"
													+ dummy[0] + "</p></li>");
								}
							}
						});
			});

	function updateRate(changed, value) {
		$.ajax({
			type : "POST",
			url : "../rateRecipe",
			data : {
				changed : changed,
				user_id : '${user.id}',
				recipe_id : '${recipe.recipe_id}',
				value : value
			}
		})
	};
</script>
</html>
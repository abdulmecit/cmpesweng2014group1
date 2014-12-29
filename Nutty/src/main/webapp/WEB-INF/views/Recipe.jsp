<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style type="text/css">
@import url(http://fonts.googleapis.com/css?family=Varela+Round);

.slides {
	padding: 0;
	width: 457px;
	height: 300px;
	display: block;
	margin: 0 auto;
	position: relative;
}

.slide {
	top: 0;
	opacity: 0;
	width: 457px;
	height: 300px;
	display: block;
	position: absolute;
	transform: scale(0);
	transition: all .7s ease-in-out;
}

.slide img {
	max-width: 457px;
	max-height: 300px;
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
	margin-left: 40px
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
	font-size: 21;
	font-style: oblique;
}
</style>


</head>
<body>

	<!------------------------ star --------------------------->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
				<div class="wrap">
					<img
						src="https://dl.dropboxusercontent.com/u/45511253/one-star.png" />
					<h3 class="desc"></h3>
				</div>
				
				<!------------- Eat/Edit Like/Delete Buttons -------------->
				<button type="button" class="btn btn-primary" id="EatEdit"
					style="float: right; margin-right: 15 px; margin-top: 18px;">
					<span id="textEatEdit" class="ui-button-text"></span> <span
						id="eatCheck" class="glyphicon glyphicon-check"
						style="visibility: hidden;"></span>
					<!-- caret for arrow. ui-button-text for button text visible; -->
				</button>

				<button type="button" class="btn btn-primary" id="LikeDelete"
					style="float: right; margin-right: 15 px; margin-top: 18px;">
					<span id="textLikeDelete" class="ui-button-text"></span> <span
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
									<img src="${photoUrl[0]}" />
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
							<div class="panel-body" style="height: 300px; overflow: scroll;">
								<div id="rateButtons" class="row">
									<!----------------------- Rate Buttons ----------------------------->
									<div class="btn-group btn-info "
										style="margin: 0px 0px 15px 15px;">
										<button type="button" class="btn btn-info btn-lg">Health</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-info" id="healthRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="ui-button-text"></span> <span class="caret"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5 : Healthy</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1 : Unhealthy</a></li>
										</ul>
									</div>

									<div class="btn-group btn-danger"
										style="margin: 0px 0px 15px 15px;">
										<button type="button" class="btn btn-danger btn-lg">Cost</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-danger" id="costRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="caret"></span> <span class="ui-button-text"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5 : Wallet Friendly</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1 : Pricy</a></li>
										</ul>
									</div>

									<div class="btn-group btn-warning"
										style="margin: 0px 0px 15px 15px;">
										<button type="button" class="btn btn-warning btn-lg">Taste</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-warning" id="tasteRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="caret"></span> <span class="ui-button-text"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5 : Yummy</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1 : Tasteless</a></li>
										</ul>
									</div>

									<div class="btn-group btn-success"
										style="margin: 0px 15px 15px 15px;">
										<button type="button" class="btn btn-success btn-lg">Ease</button>
										<button type="button"
											class="btn dropdown-toggle btn-lg btn-success" id="easeRate"
											data-toggle="dropdown" aria-expanded="false">
											<span class="ui-button-text"></span> <span class="caret"></span>
											<!-- caret for arrow. ui-button-text for button text -->
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">5 : Easy</a></li>
											<li><a href="#">4</a></li>
											<li><a href="#">3</a></li>
											<li><a href="#">2</a></li>
											<li><a href="#">1 : Difficult</a></li>
										</ul>
									</div>
								</div>
								<!----------------------- End of Rate Buttons ----------------------------->
								<div class="row ">
									<div class="col-sm-6">
										<div class="col-sm-9">
											<br>
											<div id="rate"
												style="width: 100%; margin-up: auto; margin-down: auto; overflow: hidden;">
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
										<h5 style="margin-top: 15px;">
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
												<li><a href="javascript:search('${tag.tag_name}')">
														${tag.tag_name}</a></li>
											</c:forEach>

										</h5>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<br> <br>
				<div class=row>
					<div class="col-sm-8">
						<div id="info-wrap">
							<button type="button" class="btn btn-primary"
								value="Share Recipe" id="Share"
								style="float: right; margin-right: 15 px; margin-left: 18px;">
								<span id="textShare" class="ui-button-text">Share &nbsp </span>
								<span id="shareCheck" class="glyphicon glyphicon-check"
									style="visibility: hidden;"></span>
								<!-- caret for arrow. ui-button-text for button text visible; -->
							</button>

							<div id=recipeDetail>
								<h3 style="margin-top: 0px">Portion:${recipe.portion}
									&nbsp&nbsp Total Calories:${recipe.total_calorie}</h3>
								<h3>Ingredients</h3>
								<c:forEach var="ingredientAmount" items="${ingredientAmounts}">
									<p>${ingredientAmount.amount}&nbsp${ingredientAmount.meas_type}&nbsp
										${ingredientAmount.ing_name}</p>

								</c:forEach>
							</div>

							<h3>Description</h3>
							<p>${recipe.description}</p>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="panel panel-default"
							style="margin-right: 10px; max-height: 300px; overflow: scroll;">

							<div class="panel-heading clearfix">
								<h4 class="panel-title pull-left" style="padding-top: 7.5px;">Versions</h4>
								<a href="../derivedRecipe/${recipe.recipe_id}"
									id="derivedRecipeButton" class="btn btn-primary active"
									role="button" style="float: right;">Derive New Recipe</a>
							</div>

							<div class="panel-body">
								<p>
									Original: <a href="../recipe/${parent.recipe_id}"
										class="navbar-link">${parent.name}</a>
								</p>
								<p>Derived Recipes:</p>

								<ul class="list-group" style="overflow: hidden;">
									<c:forEach var="child" items="${children}">
										<a href="../recipe/${child.recipe_id}" class="list-group-item">
											<h5 class="list-group-item-heading">${child.name}</h5>
										</a>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>

				</div>
				<!-- end of row -->
				<br> <br> <br>
				<div class=row>
					<div class="panel panel-default"
						style="margin-right: 10px; margin-left: 10px; max-height: 400px; overflow: scroll;">
						<div class="panel-heading clearfix">
							<form id="addComment" action="../commentRecipe" method="post">
								<input type="text" hidden="hidden" name="user_id"
									value='${user.id}'> <input type="text" hidden="hidden"
									name="recipe_id" value='${recipe.recipe_id}'>
								<textarea placeholder="add a comment.." class="form-control"
									name="comment" rows="3" style="resize: none"></textarea>
								<br>
								<button type="submit" class="btn btn-primary" id="comment"
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
	var commentNumber = 0;
	var user = '${user.id}';
	var owner = '${ownerId}';


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
						
						if (user != owner) {
							$("#textLikeDelete").text("Like ");
							$("#textEatEdit").text("Eat ");
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
						}else{
							$("#textLikeDelete").text("Delete");
							$("#textEatEdit").text("Edit");
						}
					});

	// change & save values of rate buttons
	$(".dropdown-menu li a").click(
			function(e) {
				if (isLogged == 'true') {
					var selText = $(this).parents('.btn-group').find(
							'.dropdown-toggle').attr("id");
					var rate = $(this).text().split(" : ");
					$(this).parents('.btn-group').find('.dropdown-toggle')
							.html(rate[0]);
					if (selText == "healthRate") {
						healthRate = rate[0];
						updateRate("health_rate", healthRate);
					} else if (selText == "costRate") {
						costRate = rate[0];
						updateRate("cost_rate", costRate);
					} else if (selText == "tasteRate") {
						tasteRate = rate[0];
						updateRate("taste_rate", tasteRate);
					} else if (selText == "easeRate") {
						easeRate = rate[0];
						updateRate("ease_rate", easeRate);
					}
				}
			});

	// Like/Delete button functions 
	$("#LikeDelete").click(function(e) {
		if (isLogged == 'true') {
			if (user != owner) {
				if (liked == 0) {
					$("#likeCheck").css('visibility', 'visible');
					liked = 1;
				} else {
					$("#likeCheck").css('visibility', 'hidden');
					liked = 0;
				}
				updateRate("likes", liked);
			} else {
				alert("delete");
			}
		}
	});

	// Eat/Edit button change "check" visibilty 
	$("#EatEdit").click(function(e) {
		if (isLogged == 'true') {
			if (user != owner) {
				if (eaten == 0) {
					$("#eatCheck").css('visibility', 'visible');
					eaten = 1;
				} else {
					$("#eatCheck").css('visibility', 'hidden');
					eaten = 0;
				}
				updateRate("eats", eaten);
			} else {
				alert("edit");
			}
		}
	});

	// share recipe
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

	var comments = [];
	var commentsILike = [];

	// getting comments
	$(document)
			.ready(
					function() {
						if (isLogged == 'true') {
							$('#commentButton').attr("disabled", false);
							$
									.ajax({
										type : "POST",
										url : "../recipeComments",
										data : {
											recipeId : '${recipe.recipe_id}',
											user_id : '${user.id}'
										}
									})
									.done(
											function(answer) {
												$("#comments").html("");
												if (answer == "") {
													$("#comments")
															.append(
																	"<h4 style='text-align: center; margin-bottom: 10px; margin-top: 10px '>No comments :(<h4>");
												} else {
													comments = JSON
															.parse(answer);

													for (i = 0; i < comments.length; i++) {

														var likers = comments[i].likers;
														var likersCount = ((likers == null) ? 0
																: likers.length);

														var likerIds = [];
														for (var j = 0; j < likersCount; j++) {
															likerIds[j] = likers[j].liker_id;
														}

														$("#comments")
																.append(
																		"<li class='list-group-item'><b>"
																				+ comments[i].comment_text
																				+ "</b><p>by <a href='../user/profile/1'>"
																				+ comments[i].commenter_name
																				+ "</a></p>"
																				+ "<input type='hidden' id='commentId" + i + "' value='" + comments[i].comment_id + "' style='visibility: collapse; width: 0px;'/>"
																				+ "<button type='button' class='btn btn-primary btn-xs'"
																				+ "onclick='commentLike("
																				+ i
																				+ ")' "
																				+ " value='commentLike' id='commentLike"
																				+ i
																				+ "'style='float: left;'>"
																				+ "<span id='textCommentLike" + i +"' class='ui-button-text'>Like &nbsp</span>"
																				+ "<span id='commentLikeCheck"+ i +"' class='glyphicon glyphicon-check'"
																				+ "style='visibility: hidden;'></span></button>"
																				+ "<a id='showLikers' title='Click to see them' onclick='showLikers("
																				+ i
																				+ ")'>"
																				+ "&nbsp"
																				+ likersCount
																				+ " likes </a></li>");
														//check this comment likes number
														if ($.inArray(
																'${user.id}',
																likerIds) > -1) {
															commentsILike[i] = 1;
															$(
																	"#commentLikeCheck"
																			+ i)
																	.css(
																			'visibility',
																			'visible');
														} else {
															commentsILike[i] = 0;
															$(
																	"#commentLikeCheck"
																			+ i)
																	.css(
																			'visibility',
																			'hidden');
														}

													}
												}
											});
						} else {
							$("#comments")
									.append(
											"<h4 style='text-align: center; margin-bottom: 10px; margin-top: 10px '><em>please <a href='../login'>login</a> to add and see comments</em><h4>");
							$('#commentButton').attr("disabled", true);
						}
					});

	//											

	//Comment Like
	function commentLike(index) {
		if (isLogged == 'true') {
			if (commentsILike[index] == 0) {
				commentsILike[index] = 1;
				$("#commentLikeCheck" + index).css('visibility', 'visible');
			} else {
				commentsILike[index] = 0;
				$("#commentLikeCheck" + index).css('visibility', 'hidden');
			}
			$.ajax({
				type : "POST",
				url : "../likeComment",
				data : {
					user_id : '${user.id}',
					comment_id : $("#commentId" + index).val(),
					value : commentsILike[index]
				}
			})
		}
	};

	// for saving rate values
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

	function showLikers(index) {
		var likers = comments[index].likers;
		var content = "";
		if (likers == null || likers.length == 0) {
			content += "<p>Sorry! No likes here :(</p>";
		} else {
			for (var i = 0; i < likers.length; i++) {
				content += "<li class='list-group-item'><a href='/nutty/user/profile/" + likers[i].liker_id + "'>"
						+ likers[i].liker_name + "</p></li>";
			}
		}
		bootbox.dialog({
			title : "Likers",
			message : content,
			onEscape : function() {
			},
		});

		return false;
	};

	$(document).ready(function() {
		if (isLogged != 'true') {
			$('#rateButtons').css('display', 'none');
			$("#Eat").css('display', 'none');
			$("#Like").css('display', 'none');
			$("#Share").css('display', 'none');
			$("#derivedRecipeButton").css('display', 'none');
			$("#addComment").css('display', 'none');
		}
	});

	function search(searchKey) {
		window.location.href = "../advancedSearch/" + searchKey;
	};
</script>
</html>
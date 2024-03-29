<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style type="text/css">
@import url(http://fonts.googleapis.com/css?family=Varela+Round);

.processing #processingScreen {
	display: block;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 100;
	width: 100vw;
	height: 100vh;
	background-color: rgba(192, 192, 192, 0.5);
	background-image: url("../resources/img/processing.gif");
	background-repeat: no-repeat;
	background-position: center;
}

.processed #processingScreen {
	display: none;
}

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
	line-height: 280px;
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
	<div id=processingScreen></div>

	<!--------------------------- for facebook share ------------------------------->
	<div id="fb-root"></div>
	<script>
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/tr_TR/sdk.js#xfbml=1&appId=595039627289255&version=v2.0";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>

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
						<!----------------------- Photo ----------------------------->
						<ul class="slides">
							<c:forEach var="photo" items="${photoUrl}" varStatus="counter">
								<input type="radio" name="radio-btn" id="img-${counter.index}"
									${counter.index=='0'?'checked':''} />
								<li class="slide-container">
									<div class="slide">
										<img src="${photoUrl[counter.index]}" />
									</div>
									<div class="nav">
										<label
											for="img-${counter.first ? (fn:length(photoUrl)-1) : counter.index-1}"
											class="prev">&#x2039;</label> <label
											for="img-${counter.last ? 0 : counter.index+1}" class="next">&#x203a;</label>
									</div>
								</li>
							</c:forEach>
							<li class="nav-dots"><c:forEach var="photo"
									items="${photoUrl}" varStatus="counter">
									<label for="img-${counter.index}" class="nav-dot"
										id="img-dot-${counter.index}"></label>
								</c:forEach></li>
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
								<!----------------------- Recipe Info (Rates) ----------------------------->
								<div class="row ">
									<div class="col-sm-7">
										<div class="col-sm-5">

											<h5 style="margin-top: 12px; text-align: right;">
												Taste Rate: <br> ${numOfTasteRate} users <br>
											</h5>
											<h5 style="margin-top: 12px; text-align: right;">
												Health Rate: <br> ${numOfHealthRate} users<br>
											</h5>
											<h5 style="margin-top: 12px; text-align: right;">
												Cost Rate: <br> ${numOfCostRate} users<br>
											</h5>
											<h5 style="margin-top: 12px; text-align: right;">
												Ease Rate: <br> ${numOfEaseRate} users<br>
											</h5>
										</div>
										<div class="col-sm-7">
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
									</div>
									<!----------------------- Recipe Info (Number of likes/eats ,tags) ----------------------------->
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
					<!----------------------- Share/Report Button ----------------------------->
					<div class="col-sm-8">
						<div id="info-wrap" style="margin-left: 30px">
							<div class="row">
								<div class="col-sm-8">
									<p style="margin-top: 0px">
										<b>Portion:</b> &nbsp ${recipe.portion} <br> <b>Total
											Calories:</b> &nbsp ${recipe.nutrition_info.total_calorie} kcal
										<br> <b>Total Fat:</b> &nbsp
										${recipe.nutrition_info.total_fat} g <br> <b>Total
											Protein:</b> &nbsp ${recipe.nutrition_info.total_protein} g <br>
										<b>Total Carbohydrate:</b> &nbsp
										${recipe.nutrition_info.total_carbohydrate} g
									</p>
								</div>
								<div class="col-sm-4">
									<button type="button" class="btn btn-primary"
										value="Share Recipe" id="Share"
										style="float: right; margin-right: 15 px; margin-left: 15px;">
										<span id="textShare" class="ui-button-text">Share &nbsp
										</span> <span id="shareCheck" class="glyphicon glyphicon-check"
											style="visibility: hidden;"></span>
										<!-- caret for arrow. ui-button-text for button text visible; -->
									</button>
									<button type="button" class="btn btn-primary" id="reportRecipe"
										style="float: right; margin-right: 15 px; margin-left: 15px;">
										<span id="textReport" class="ui-button-text">Report</span>
									</button>
									<br> <br>
									<div
										style="float: right; margin-right: 15 px; margin-left: 15px;"
										id="fbShare" class="fb-share-button"
										data-href="http://titan.cmpe.boun.edu.tr:8080/nutty/recipe/${recipe.recipe_id}"
										data-layout="button"></div>

								</div>
							</div>
							<div class="row" style="margin-left: 5px">
								<!----------------------- Recipe Description ----------------------------->
								<div id=recipeDetail>
									<h3>Ingredients</h3>
									<c:forEach var="ingredientAmount" items="${ingredientAmounts}">
										<p>${ingredientAmount.amount}&nbsp${ingredientAmount.meas_type}&nbsp;
											${ingredientAmount.ing_name}</p>

									</c:forEach>
								</div>
								<h3>Description</h3>
								<p>${recipe.description}</p>
							</div>
						</div>
					</div>
					<!----------------------- Derived Recipe ----------------------------->
					<div class="col-sm-4">
						<div id="version" class="panel panel-default"
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
					<!----------------------- Comments ----------------------------->
					<div class="panel panel-default"
						style="margin-right: 10px; margin-left: 10px; max-height: 2000px; overflow: scroll;">
						<div class="panel-heading clearfix">
							<form id="addComment" action="../commentRecipe" method="post">
								<input type="text" hidden="hidden" name="user_id"
									value='${user.id}'> <input type="text" hidden="hidden"
									name="recipe_id" value='${recipe.recipe_id}'>
								<textarea placeholder="add a comment.." class="form-control"
									name="comment" rows="3" style="resize: none"></textarea>
								<br>
								<button type="submit" class="btn btn-primary" id="comment"
									style="">Comment</button>

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
	var report = '${reportOfUser}';
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
							if (report == 1) {
								$("#reportRecipe").attr("disabled", true);
							} else {
								$("#reportRecipe").attr("disabled", false);
							}
						} else {
							$("#textLikeDelete").text("Delete");
							$("#textEatEdit").text("Edit");
						}
					});

	/*
	 * change & save values of rate buttons
	 */
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

	/*
	 * Like/Delete button functions
	 */
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
				bootbox.confirm("Are you sure?", function(result) {
					if (result) {
						document.body.className = "processing";
						$.ajax({
							type : "POST",
							url : "../deleteRecipe",
							data : {
								recipe_id : '${recipe.recipe_id}',
							}
						}).done(function(response) {
							document.body.className = "processed";
							window.location.href = "../index";
						});

					} else {

					}

				});

			}
		}
	});

	/*
	 * Eat/Edit button change "check" visibilty 
	 */
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
				$(location).attr('href', "../editRecipe/${recipe.recipe_id}");
			}
		}
	});

	/*
	 * Share recipe
	 */
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

	/*
	 * Report Comment
	 */
	function reportComment(commentID, index) {
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "../reportComment",
					data : {
						comment_id : commentID,
						user_id : '${user.id}'
					}
				});

			} else {

			}
		});
	}

	/*
	 * Report Recipe
	 */
	$("#reportRecipe").click(function(e) {
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "../reportRecipe",
					data : {
						recipe_id : '${recipe.recipe_id}',
						user_id : '${user.id}'
					}
				});
				$("#reportRecipe").attr("disabled", true);
			} else {

			}
		});

	});

	var comments = [];
	var commentsILike = [];
	$(document)
			.ready(
					function() {
						if (isLogged == 'true') {
							$('#commentButton').attr("disabled", false);
							getComment();
						} else {
							$("#comments")
									.append(
											"<h4 style='text-align: center; margin-bottom: 10px; margin-top: 10px '><em>please <a href='../login'>login</a> to add and see comments</em><h4>");
							$('#commentButton').attr("disabled", true);
						}

					});

	/*
	 * Getting comments
	 */
	function getComment() {
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
								comments = JSON.parse(answer);

								for (i = 0; i < comments.length; i++) {

									var likers = comments[i].likers;
									var likersCount = ((likers == null) ? 0
											: likers.length);

									var likerIds = [];
									for (var j = 0; j < likersCount; j++) {
										likerIds[j] = likers[j].liker_id;
									}
									var content = "<div id='commentDiv"
											+ i
											+ "' <li class='list-group-item'><b>"
											+ comments[i].comment_text
											+ "</b><p>by <a href='../user/profile/"+comments[i].commenter_id+"'>"
											+ comments[i].commenter_name
											+ "</a></p>"
											+ "<input type='hidden' id='commentId" + i + "' value='" + comments[i].comment_id + "' style='visibility: collapse; width: 0px;'/>";

									if (comments[i].commenter_id != '${user.id}') {
										if (comments[i].commentedByMe == 0) {
											content += "<button type='button' class='btn btn-primary btn-xs'"
													+ "onclick='reportComment("
													+ comments[i].comment_id
													+ ","
													+ i
													+ ")' "
													+ " value='report' id='reportComment"
													+ i
													+ "'style='float: left; margin-right:5px' disabled='disabled'><span class='ui-button-text'>Report </span></button>"
										} else {
											content += "<button type='button' class='btn btn-primary btn-xs'"
													+ "onclick='reportComment("
													+ comments[i].comment_id
													+ ","
													+ i
													+ ")' "
													+ " value='report' id='reportComment"
													+ i
													+ "'style='float: left; margin-right:5px'><span class='ui-button-text'>Report </span></button>"
										}
										content += "<button type='button' class='btn btn-primary btn-xs'"
												+ "onclick='commentLike("
												+ i
												+ ")' "
												+ " value='commentLike' id='commentLike"
												+ i
												+ "'style='float: left;'>"
												+ "<span id='textCommentLike" + i +"' class='ui-button-text'>Like &nbsp</span>"
												+ "<span id='commentLikeCheck"+ i +"' class='glyphicon glyphicon-check'"
								+ "style='visibility: hidden;'></span></button>"

									} else {
										content += "<button type='button' class='btn btn-primary btn-xs'"
												+ "onclick='editComment("
												+ i
												+ ")' "
												+ " value='edit' id='editComment"
												+ i
												+ "'style='float: left;  margin-right:5px'><span class='ui-button-text'>&nbsp&nbspEdit&nbsp &nbsp</span></button>"

										content += "<button type='button' class='btn btn-primary btn-xs'"
												+ "onclick='deleteComment("
												+ comments[i].comment_id
												+ ","
												+ i
												+ ")' "
												+ " id='deleteComment"
												+ i
												+ "'style='float: left;'><span class='ui-button-text'>Delete</span></button>"
									}
									content += "&nbsp<a id='showLikers"
											+ i
											+ "' title='Click to see them' onclick='showLikers("
											+ i + ")'>" + likersCount
											+ " likes </a></li></div>"

									$("#comments").append(content);
									//check this comment likes number
									if ($.inArray('${user.id}', likerIds) > -1) {
										commentsILike[i] = 1;
										$("#commentLikeCheck" + i).css(
												'visibility', 'visible');
									} else {
										commentsILike[i] = 0;
										$("#commentLikeCheck" + i).css(
												'visibility', 'hidden');
									}

								}
							}
						});
	}

	/*
	 * Comment Like
	 */
	function commentLike(index) {
		var likersCount = $("#showLikers" + index).text().split(" ");
		var num = likersCount[0];
		if (isLogged == 'true') {
			if (commentsILike[index] == 0) {
				commentsILike[index] = 1;
				$("#commentLikeCheck" + index).css('visibility', 'visible');
				num++;
			} else {
				commentsILike[index] = 0;
				$("#commentLikeCheck" + index).css('visibility', 'hidden');
				num--;
			}
			$.ajax({
				type : "POST",
				url : "../likeComment",
				data : {
					user_id : '${user.id}',
					comment_id : $("#commentId" + index).val(),
					value : commentsILike[index]
				}
			}).done(function(response) {
				$("#showLikers" + index).text(num + " likes");
			});
		}
	};

	/*
	 * Comment Edit
	 */
	function editComment(index) {
		$('#commentDiv' + index).empty();

		var cont = '<textarea class="form-control" id="myTextArea" rows="auto" style="resize: none">'
				+ comments[index].comment_text
				+ '</textarea> <br><button class="btn btn-primary" id="changeComment" style="float: right">Done</button><br><br>';
		$('#commentDiv' + index).append(cont);
		//var comment = document.getElementById("myTextarea").value;

		$("#changeComment").on('click', function() {
			var newComment = document.getElementById("myTextArea").value;
			$.ajax({
				type : "POST",
				url : "../editComment",
				data : {
					comment_id : comments[index].comment_id,
					recipe_id : '${recipe.recipe_id}',
					text : newComment
				}
			}).done(function(response) {
				getComment();
			});
		});
	}

	/*
	 * Comment Delete
	 */
	function deleteComment(commentID, index) {
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "../deleteComment",
					data : {
						comment_id : commentID
					}
				});
				$('#commentDiv' + index).remove();

			} else {

			}

		});
	}

	/*
	 * Comment Report
	 */
	function reportComment(commentID, index) {
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "../reportComment",
					data : {
						comment_id : commentID,
						user_id : '${user.id}'
					}
				});
				$("#reportComment" + index).attr("disabled", true);
			} else {

			}
		});

	}

	/*
	 * for changing and saving rate values
	 */
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

	/*
	 * show likes number and likers of comment
	 */
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

	/*
	 * change visibility according to user/guest
	 */
	$(document).ready(function() {
		if (isLogged != 'true') {
			$('#rateButtons').css('display', 'none');
			$("#Eat").css('display', 'none');
			$("#Like").css('display', 'none');
			$("#Share").css('display', 'none');
			$("#derivedRecipeButton").css('display', 'none');
			$("#addComment").css('display', 'none');
			$("#reportRecipe").css('display', 'none');
			$("#fbShare").css('display', 'none');
			$("#EatEdit").css('display', 'none');
			$("#LikeDelete").css('display', 'none');
			$("#version").css('display', 'none');
		} else {
			if (user == owner) {
				$("#reportRecipe").css('display', 'none');
			}
		}
	});

	function search(searchKey) {
		window.location.href = "../advancedSearch/" + searchKey;
	};
</script>
</html>
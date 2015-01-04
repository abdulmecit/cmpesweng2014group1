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
		<div class="col-sm-6 col-centered">
			<ul class="slides">
				<input type="radio" name="radio-btn" id="img-1" checked />
				<li class="slide-container">
					<div class="slide">
						<h3 align="center"><a href="javascript:search('Delicious')">Delicious</a></h3>
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
						<h3 align="center"><a href="javascript:search('Healthy')">Healthy</a></h3>
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
						<h3 align="center"><a href="javascript:search('Easy')">Easy</a></h3>
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
						<h3 align="center"><a href="javascript:search('Economic')">Economic</a></h3>
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
		<div class="col-sm-6 col-centered" style="margin-top: 50px;">
			<p><a href="javascript:search('beef')">Beef</a></p>
			<p><a href="javascript:search('breakfast')">Breakfast</a></p>
			<p><a href="javascript:search('celebration')">Celebration</a></p>
			<p><a href="javascript:search('chocolate')">Chocolate</a></p>
			<p><a href="javascript:search('dessert')">Dessert</a></p>
			<p><a href="javascript:search('diet')">Diet</a></p>
			<p><a href="javascript:search('quick')">Quick</a></p>
			<p><a href="javascript:search('vegetarian')">Vegetarian</a></p>
			<p><a href="javascript:search('Student')">Student</a></p>
		</div>
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
							<li role="presentation" class="filter active" id="recommendation"><a
								class="btn btn-link">Recommendations</a></li>
							<li role="presentation" class="filter" id="newsfeed"><a
								class="btn btn-link">News Feed</a></li>
		<% /**				<li role="presentation" class="filter" id="myRecipe"><a
								class="btn btn-link">My Recipe</a></li>
							<li role="presentation" class="filter" id="myFriendsRecipe"><a
								class="btn btn-link">Recipe From My Friends</a></li>
							<li role="presentation" class="filter" id="sharedRecipe"><a
								class="btn btn-link">Shared Recipes</a></li> 
		**/ %>
						</ul>
						<ul id="recommContent" class="list-group"
							style="overflow: scroll;display:block;">
							<li><br>
								<p>Loading, please wait..</p> <br></li>
						</ul>
						<ul id="newsfeedContent" class="list-group"
							style="overflow: scroll;display:none;">
							<li><br>
								<p>Loading, please wait..</p> <br></li>
						</ul>
					</div>

				</div>
	<% /**			<div class="col-sm-3">
					<div class="panel panel-default">
						<div class="panel-body"></div>
					</div>
				</div> **/ %>
			</div>
		</div>
	</div>
	<%
		}
	%>
</body>
<script type="text/javascript">
	var searchFilter;
	var user_id = '${user.id}';
	var isLogged = '${isLogged}';
	var result="";
	
	
	$(".filter").click(function() {
		$(this).addClass("active").siblings().removeClass("active");
		searchFilter = this.id;
				
		if (searchFilter == "recommendation") {
			$("#newsfeedContent").css('display', 'none');
			$("#recommContent").css('display', 'block');
			
			$('#recommContent').empty(); 
			result='<c:forEach var="recRecipes" items="${recommendedRecipes}"> <a href="recipe/${recRecipes.recipe_id}"'
				+'class="list-group-item"><h5 class="list-group-item-heading">${recRecipes.name}</h5></a></c:forEach>';
			$('#recommContent').append(result);
/*		} else if (searchFilter == "myRecipe") {
			$('#results').empty();	
		} else if (searchFilter == "myFriendsRecipe") {
			$('#results').empty();
		} else if (searchFilter == "sharedRecipe") {
			$('#results').empty(); 
*/
		} else {
			$("#newsfeedContent").css('display', 'block');
			$("#recommContent").css('display', 'none');
		}
	});
	
	
	$(document)
	.ready(
			function() {
				if(isLogged == 'true'){
					$
						.ajax({
							type : "POST",
							url : "getRecentEvents",
							data : {
								user_id : user_id,
							}
						})
						.done(
								function(response) {
									$('#newsfeedContent').empty();
									if (response == null
											|| response.length == 0) {
										$('#newsfeedContent')
												.append(
														"<li><br>"
																+ "<p>No recent activity :(</p><br></li>");
									} else {
										for (var i = 0; i < response.length; i++) {
											var event = response[i];

											var content = "<br><li>"
													+ timestampToString(event.timestamp)
													+ " - "

											content += "<a href='/nutty/user/profile/" + event.user.id + "'>"
													+ event.user.name
													+ " "
													+ event.user.surname
													+ "</a>";

											var action = event.action;
											
											if(action == "earn_badge"){
												content += " has earned a new badge: <br>"
													+ "<a href='javascript:;' class='list-group-item'>";
													
												for(var j=0; j<event.target.badge_id; j++){
													content += "<img alt='' src='"
														+ event.target_photo_url
														+ "' width='50px' height='auto' hspace='10px'>"
												}
												
												content += "<span style='font-size: 1.2em;'>&nbsp;&nbsp;"
												+ event.target.name + "</span></a>";		
											}
											else if(action == "follow_user" || action == "get_followed"){
												if (action == "follow_user")
													content += " is now following: <br>";
												else 
													content += " is now being followed by: <br>";
												
													content += "<a href='/nutty/user/profile/" + event.target.id
														+ "' class='list-group-item'><img src='"
														+ event.target_photo_url
														+ "' title='"
														+ event.target.name + " " + event.target.surname
														+ "' onError='this.onerror=null;this.src=\"http://img2.wikia.nocookie.net/__cb20130511180903/legendmarielu/images/b/b4/No_image_available.jpg\";' width='30%' height='auto' hspace='50px'>"
														+ "<span style='font-size: 1.2em; width:250px; height:auto; display:inline-block'>"
														+ event.target.name + " " + event.target.surname
														+ "</span></a>";
											}
											else{
												if (action == "add_comment") {
													content += " has commented on a recipe: <br>";
												} else if (action == "edit_recipe") {
													content += " has edited a recipe: <br>";
												} else if (action == "add_recipe") {
													content += " has created a recipe: <br>";
												} else if (action == "derive_recipe") {
													content += " has derived a recipe: <br>";
												} else if (action == "share_recipe") {
													content += " has shared a recipe: <br>";
												} else if (action == "eat_recipe") {
													content += " has eaten a recipe: <br>";
												} else if (action == "like_recipe") {
													content += " has liked a recipe: <br>";
												} else if (action == "rate_recipe") {
													content += " has rated a recipe: <br>";
												}
												
													content += "<a href='/nutty/recipe/" + event.target.recipe_id
														+ "' class='list-group-item'><img src='"
														+ event.target_photo_url
														+ "' title='"
														+ event.target.name
														+ "' onError='this.onerror=null;this.src=\"http://img2.wikia.nocookie.net/__cb20130511180903/legendmarielu/images/b/b4/No_image_available.jpg\";' width='30%' height='auto' hspace='50px'>"
														+ "<span style='font-size: 1.2em; width:250px; height:auto; display:inline-block'>"
														+ event.target.name
														+ "</span></a>";			
											}
								
											content += "</li>";
											$('#newsfeedContent')
													.append(content);	
										}
									}
								});
				}
		});
			

	function timestampToString(timestamp) {
		var ts = new Date(timestamp);
		var date = [ ts.getFullYear(), ts.getMonth() + 1, ts.getDate() ];
		var time = [ ts.getHours(), ts.getMinutes(), ts.getSeconds() ];
		
		for (var i = 0; i < 3; i++) {
			if (time[i] < 10)
				time[i] = "0" + time[i];
			if (date[i] < 10)
				date[i] = "0" + date[i];
		}
		return date.join("/") + " " + time.join(":");
	}
	
	function search(searchKey) {
		window.location.href = "advancedSearch/" + searchKey;
	};
</script>
</html>

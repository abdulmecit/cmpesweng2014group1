<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style>
body {
	margin-top: 50px;
}

#main-wrap>div {
	min-height: 450px;
}

.info {
	min-height: 80px;
}

/* layout */
#main-wrap {
	/* overflow to handle inner floating block */
	overflow: hidden;
}

#sidebar {
	float: left;
	width: 40%;
	height: 100%;
	background: #eeeeee;
}

#foto {
	float: center;
	width: 40%;
	height: auto;
	background: #eeeeee;
	margin-top: 50px;
}

#content-wrap {
	float: right;
	width: 60%;
}

#info-wrap {
	/* overflow to handle inner floating block */
	overflow: hidden;
}

.info {
	width: 50%;
	float: left;
}

#Follow {
	display: none;
	visibility: hidden;
}

#followCheck {
	display: none;
	visibility: hidden;
}
</style>

</head>
<body>
	<div id="main-wrap">
		<div id="sidebar" align="center">
			<div id="foto">
				<!-- 	    		<img id="profilePic" alt="Photo of the User" src="http://cdn.sett.com/images/user/20140502/chef57b22ab552661a6852fe44c0e7e59e63.jpg" width="100%" height="auto"/> -->
			</div>
			<br> <br> <br>
			<h2>Best Of</h2>
			<br> <br>
			<div id="bestOf">
				<p>Loading Recipes, please wait..</p>
			</div>
		</div>
		<div id="content-wrap">
			<div id="info-wrap">
				<div class="info" align="center">
					<p>&nbsp;</p>
					<h1 id="name">${visitedUser.name} ${visitedUser.surname}</h1>
					<h3>Age:</h3>
					<p id="age">Not specified</p>
					<h3>Gender:</h3>
					<p id="gender">Not specified</p>
					<h3>Food Intolerances:</h3>
					<p id="food_int">None</p>
					<h3>Health Conditions:</h3>
					<p id="health_cond">None</p>
					<h3>Not Preferred:</h3>
					<p id="not_pref">None</p>
				</div>
				<div class="info" align="center">
					<button type="button" class="btn btn-primary" value="Follow"
						id="Follow"
						style="float: center; margin-right: 15 px; margin-top: 18px;">
						<span id="textFollow" class="ui-button-text">Follow &nbsp </span>
						<span id="followCheck" class="glyphicon glyphicon-check"></span>
						<!-- caret for arrow. ui-button-text for button text visible; -->
					</button>
				</div>
				<div class="info" align="center">
					<p>&nbsp;</p>
					<img alt=""
						src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg"
						width="50px" height="auto"> <img alt=""
						src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg"
						width="50px" height="auto"> <img alt=""
						src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg"
						width="50px" height="auto"> <img alt=""
						src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg"
						width="50px" height="auto"> <img alt=""
						src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg"
						width="50px" height="auto">
					<p>&nbsp;</p>
					<h2>${badge}</h2>
					<h3>&nbsp;</h3>
					<h3>
						<a id="showFollowers" title="Click to see them">
							${numberOfFollowers} followers </a>
					</h3>
					<h3>
						<a id="showFollowings" title="Click to see them">
							${numberOfFollowing} following </a>
					</h3>
				</div>
			</div>

			<div class="panel panel-default"
				style="margin-right: 80px; margin-left: 80px">
				<div class="panel-body">
					<div>
						<ul class="nav nav-tabs nav-justified">
							<li role="presentation" class="filter active" id="overall"><a
								class="btn btn-link">News Feed</a></li>

							<li role="presentation" class="filter" id="recommendation"><a
								class="btn btn-link">Shared Recipe</a></li>
						</ul>
						<ul id="sharedRecipes" class="list-group"
							style="overflow: scroll;">
							<p>Loading Recipes, please wait..</p>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var searchFilter;
		$(".filter").click(function() {
			$(this).addClass("active").siblings().removeClass("active");
			searchFilter = this.id;
		});

		$(document).ready(function() {
			if ('${visitedUser.id}' == '${user.id}') {
				$("#Follow").css('display', 'none');
				$("#Follow").css('visibility', 'hidden');
			} else {
				$("#Follow").css('display', 'block');
				$("#Follow").css('visibility', 'visible');
			}
		});

		var photo = '${visitedUser.photo}';
		$(document)
				.ready(
						function() {
							if(photo){
								document.getElementById('foto').innerHTML = '<img id="profilePic" alt="Photo of the User" src='
									+ photo + ' width="100%" height="auto"/>';
							}
							else{
								if ('${visitedUser.gender}' == 1) {
									document.getElementById('foto').innerHTML = '<img id="profilePic" alt="Photo of the User" src="http://cdn.sett.com/images/user/20140502/chef57b22ab552661a6852fe44c0e7e59e63.jpg" width="100%" height="auto"/>';
								} else {
									document.getElementById('foto').innerHTML = '<img id="profilePic" alt="Photo of the User" src="http://thumbs.dreamstime.com/x/funny-chef-cracking-egg-6967190.jpg" width="100%" height="auto"/>';
								}
							}
						});

		$(document).ready(function() {
			if ('${visitedUser.gender}') {
				if ('${visitedUser.gender}' == 0) {
					document.getElementById('gender').innerHTML = "Male";
				} else {
					document.getElementById('gender').innerHTML = "Female";
				}
			}
		});

		$(document).ready(
				function() {
					if ('${visitedUser.birthday}') {
						var birthday = +new Date('${visitedUser.birthday}');
						document.getElementById('age').innerHTML = ~~((Date
								.now() - birthday) / (31557600000));
					}
				});

		$(document)
				.ready(
						function() {
							$
									.ajax({
										type : "POST",
										url : "../getUsersRecipes",
										data : {
											userId : '${visitedUser.id}',
										}
									})
									.done(
											function(response) {
												if (response != null) {
													$('#bestOf').empty();
													for (var i = 0; i < response.length; i++) {
														var href = "../../recipe/"
																+ response[i][0];
														var name = response[i][1];
														var src = response[i][2];
														$('#bestOf')
																.append(
																		"<a href='"+href+"'><img src='"
																				+ src
																				+ "' title='"
																				+ name
																				+ "' onError='this.onerror=null;this.src=\"http://img2.wikia.nocookie.net/__cb20130511180903/legendmarielu/images/b/b4/No_image_available.jpg\";' width='50%' height='auto'><p>"
																				+ name
																				+ "</p></a>");
													}
												} else {
													$('#bestOf').empty();
													$('#bestOf')
															.append(
																	"User doesn't own any recipe :(");
												}
											});
						});

		$(document)
				.ready(
						function() {
							$
									.ajax({
										type : "POST",
										url : "../getSharedRecipes",
										data : {
											userId : '${visitedUser.id}',
										}
									})
									.done(
											function(response) {
												if (response != null) {
													$('#sharedRecipes').empty();
													for (var i = 0; i < response.length; i++) {
														var href = "../../recipe/"
																+ response[i][0];
														var name = response[i][1];
														var src = response[i][2];
														//	$('#sharedRecipes').append("<div class=row><div class='panel panel-default' align='center'>");
														$('#sharedRecipes')
																.append(
																		"<a href='"+href+"'class='list-group-item'><img src='"
																				+ src
																				+ "' title='"
																				+ name
																				+ "' onError='this.onerror=null;this.src=\"http://img2.wikia.nocookie.net/__cb20130511180903/legendmarielu/images/b/b4/No_image_available.jpg\";' width='30%' height='auto' hspace='50px'><span style='font-size: 1.2em'>"
																				+ name
																				+ "</span></a>");
													}
													//	$('#sharedRecipes').append("</div></div>");
												} else {
													$('#sharedRecipes').empty();
													$('#sharedRecipes')
															.append(
																	"User hasn't shared any recipe :(");
												}
											});
						});

		var isFollower = '${isFollower}';

		$(document).ready(function() {
			if (isFollower == 'true') {
				$("#followCheck").css('display', 'inline');
				$("#followCheck").css('visibility', 'visible');
			} else {
				$("#followCheck").css('display', 'none');
				$("#followCheck").css('visibility', 'hidden');
			}
		});

		$("#Follow").click(function() {
			if (isFollower == 'true') {
				$("#followCheck").css('display', 'none');
				$("#followCheck").css('visibility', 'hidden');
				isFollower = 'false';
				followUser(0);
			} else {
				$("#followCheck").css('display', 'inline');
				$("#followCheck").css('visibility', 'visible');
				isFollower = 'true';
				followUser(1);
			}
		});

		function followUser(value) {
			$.ajax({
				type : "POST",
				url : "../followUser",
				data : {
					follower_id : '${user.id}',
					followed_id : '${visitedUser.id}',
					value : value
				}
			})
		};

		$(document).ready(function() {
			$.ajax({
				type : "POST",
				url : "../foodIntoleranceREST",
				data : {
					user_id : '${visitedUser.id}'
				}
			}).done(function(res) {
				var len = res.length;
				if (len != 0) {
					var prefs = "";
					for (var i = 0; i < len - 1; i++) {
						prefs += res[i].fs_name + ", ";
					}
					prefs += res[len - 1].fs_name;
					document.getElementById('food_int').innerHTML = prefs;
				}
			});
		});

		$(document).ready(function() {
			$.ajax({
				type : "POST",
				url : "../healthConditionREST",
				data : {
					user_id : '${visitedUser.id}'
				}
			}).done(function(res) {
				var len = res.length;
				if (len != 0) {
					var prefs = "";
					for (var i = 0; i < len - 1; i++) {
						prefs += res[i].fs_name + ", ";
					}
					prefs += res[len - 1].fs_name;
					document.getElementById('health_cond').innerHTML = prefs;
				}
			});
		});

		$(document).ready(function() {
			$.ajax({
				type : "POST",
				url : "../unpreferREST",
				data : {
					user_id : '${visitedUser.id}'
				}
			}).done(function(res) {
				var len = res.length;
				if (len != 0) {
					var prefs = "";
					for (var i = 0; i < len - 1; i++) {
						prefs += res[i].ing_name + "<br>";
					}
					prefs += res[len - 1].ing_name;
					document.getElementById('not_pref').innerHTML = prefs;
				}
			});
		});

		$("#showFollowers")
				.click(
						function() {
							//var followerWindow = window.open("", "_blank", "left=500, top=350, width=200, height=150, resizable=1, scrollbars=1");

							var followers = [];
							<c:forEach var="follower" items="${followers}">
							followers.push({
								user_id : "${follower.id}",
								name : "${follower.name}",
								surname : "${follower.surname}"
							});
							</c:forEach>

							var content = "";
							if (followers == null || followers.length == 0) {
								content += "<p>Sorry! No follower is found :(</p>";
							} else {
								for (var i = 0; i < followers.length; i++) {
									content += "<li class='list-group-item'><a href='" + followers[i].user_id + "'>"
											+ followers[i].name
											+ " "
											+ followers[i].surname
											+ "</p></li>";
								}
							}
							//content += "<button type='button'> Close </button>";
							//followerWindow.document.write(content);	
							bootbox.dialog({
								title : "Followers",
								message : content,
								onEscape : function() {
								},
							});
							return false;
						});

		$("#showFollowings")
				.click(
						function() {
							var followings = [];
							<c:forEach var="following" items="${followings}">
							followings.push({
								user_id : "${following.id}",
								name : "${following.name}",
								surname : "${following.surname}"
							});
							</c:forEach>

							var content = "<ul class='list-group' style='overflow:hidden;'>";
							if (followings == null || followings.length == 0) {
								content += "<p>Sorry! You are not following anyone :(</p>";
							} else {
								for (var i = 0; i < followings.length; i++) {
									content += "<li class='list-group-item'><a href='"
											+ followings[i].user_id
											+ "'>"
											+ followings[i].name
											+ " "
											+ followings[i].surname
											+ "</p></li>";
								}
							}
							content += "</ul>"
							bootbox.dialog({
								title : "Following",
								message : content,
								onEscape : function() {
								},
							});
							return false;
						});
	</script>
</body>
</html>
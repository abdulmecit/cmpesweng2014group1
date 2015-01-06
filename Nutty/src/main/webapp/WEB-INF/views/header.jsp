<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<meta property="og:title" content="${recipe.name}" />
<meta property="og:image" content="${photoUrl[0]}" />
<title>Nutty</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

<!-- Bootbox CSS -->
<script
	src="/nutty/resources/bootbox.js"></script>

<!-- Autocomplete CSS -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<style type="text/css">
@import url(http://fonts.googleapis.com/css?family=Varela+Round);
</style>

<!-- Add custom CSS here -->
<style>
body {
	margin-top: 80px;
}

/****************************************
	CSS 3 SEARCH FORM BY CAMERON BANEY
    Design Blog: http://blog.cameronbaney.com 
	Twitter: @cameronbaney
****************************************/
#search-form {
	margin: 0 auto;
	width: 515px;
	/* Rounded Corners */
	border-radius: 17px;
	-webkit-border-radius: 17px;
	-moz-border-radius: 17px;
	/* Shadows */
	box-shadow: 1px 1px 2px rgba(0, 0, 0, .3), 0 0 2px rgba(0, 0, 0, .3);
	-webkit-box-shadow: 1px 1px 2px rgba(0, 0, 0, .3), 0 0 2px
		rgba(0, 0, 0, .3);
	-moz-box-shadow: 1px 1px 2px rgba(0, 0, 0, .3), 0 0 2px
		rgba(0, 0, 0, .3);
}

/*** TEXT BOX ***/
#searchText {
	background: #fafafa; /* Fallback color for non-css3 browsers */
	/* Gradients */
	background: -webkit-gradient(linear, left bottom, left top, color-stop(0, rgb(250,
		250, 250)), color-stop(1, rgb(230, 230, 230)));
	background: -moz-linear-gradient(center top, rgb(250, 250, 250) 0%,
		rgb(230, 230, 230) 100%);
	border: 0;
	border-bottom: 1px solid #fff;
	border-right: 1px solid rgba(255, 255, 255, .8);
	font-size: 16px;
	margin: 4px;
	padding: 5px;
	width: 400px;
	/* Rounded Corners */
	border-radius: 17px;
	-webkit-border-radius: 17px;
	-moz-border-radius: 17px;
	/* Shadows */
	box-shadow: -1px -1px 2px rgba(0, 0, 0, .3), 0 0 1px rgba(0, 0, 0, .2);
	-webkit-box-shadow: -1px -1px 2px rgba(0, 0, 0, .3), 0 0 1px
		rgba(0, 0, 0, .2);
	-moz-box-shadow: -1px -1px 2px rgba(0, 0, 0, .3), 0 0 1px
		rgba(0, 0, 0, .2);
}
</style>
</head>

<% 	session.setAttribute("currentUrl", request.getAttribute("javax.servlet.forward.request_uri"));	%>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header navbar-left">
				<a class="navbar-brand" href=/nutty>Nutty</a>
			</div>

			<div style="margin-top: 7px; margin-left: 100px; margin-right: 100px">
				<form id="search-form" class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input id="searchText" type="text" class="form-control"
							placeholder="Search">
					</div>
					<input type="submit" class="btn btn-default" value="Search">

					<%
						if (session.getAttribute("isLogged") == null
								|| ((Boolean) (session.getAttribute("isLogged")) == false)) {
						} else {
					%>
					<div class="form-group">
						<label for="searchOptions" class="col-sm-3 control-label"
							style="color: gray;">For:</label>
						<div class="col-sm-9">
							<input id="searchOption" type="radio" name="searchOption"
								value="recipe"><label style="color: gray;">&nbsp;
								Recipe &nbsp; </label> <input id="searchOption" type="radio"
								name="searchOption" value="user"><label
								style="color: gray;">&nbsp;User</label>
						</div>
					</div>
					<%
						}
					%>
				</form>

			</div>

			<ul class="nav navbar-nav navbar-right">
				<%
					if (session.getAttribute("isLogged") == null
							|| ((Boolean) (session.getAttribute("isLogged")) == false)) {				
				%>
				<li><a href="/nutty/login">Login</a></li>
				<li><a href="/nutty/signup">Sign Up</a></li>
				<%
					} else {
				%>
				<li><a id="requests" class="glyphicon glyphicon-user" title="Follow Requests" style="cursor:pointer"></a></li> <!-- eger request varsa style="color: red;" -->
				<li><a href="/nutty/advancedSearch">Advanced<br />&nbsp;&nbsp;Search
				</a></li>
				<li><a href="/nutty/user/reportPage">Reports</a></li>
				<li><a href="/nutty/addRecipe">Add Recipe</a></li>
				<li><a href="/nutty/user/profile/${user.id}">My Profile</a></li>
				<li><a id="logout" href="/nutty/logout">Logout</a></li>
				<li id="settings" class="dropdown"><a href="#"
					data-toggle="dropdown" class="dropdown-toggle"><span
						class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a>
					<ul role="menu" class="dropdown-menu">
						<li id="popular"><a href="/nutty/user/homesettings">Profile
								Settings</a></li>
						<li id="app"><a href="/nutty/user/preferences">Food
								Preferences</a></li>
						<li id="app"><a href="/nutty/user/privacy">Privacy
								Settings</a></li>
					</ul></li>

				<%
					}
				%>

				<!-- 		  <ul class="nav navbar-nav navbar-search navbar-right"> -->
				<!--             <input type="text" class="search-query span3" placeholder="Search"><ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins> -->
				<!--           </ul> -->
		</div>
		<!-- /.container -->
	</nav>

	<script type="text/javascript">
		var requests;
	
		$(document).ready(function(){
			if('${isLogged}')
				checkResults();
		});
		
		function checkResults(){
			$.ajax({
				type: "POST",
				url: "/nutty/user/getFollowRequests",
				data: {
					user_id: '${user.id}'
				}
			}).done(function(answer) {
				requests = answer;
				if(answer != ""){
					length = (answer.split("|").length - 1);
					$('#requests').css('color','red').attr("title",length);
				}else{
					$('#requests').css('color','gray').attr("title","No waiting request");
				}
			})
		}
		
		$('#requests').on("click", function(event){
			event.preventDefault();
			showResult("Follow Requests", requests, true);
		});
	
		$('#search-form')
				.submit(
						function(event) {
							event.preventDefault();
							$
									.ajax(
											{
												type : "POST",
												url : "/nutty/basicSearch",
												data : {
													search : $("#searchText")
															.val(),
													searchOption : $(
															'input:radio[name=searchOption]:checked')
															.val()
												}
											}).done(function(answer) {
										$("#searchResults").html("");
										showResult("Search Results", answer,false);
									})
						});
		
		function showResult(title, answer, isRequest) {
			var content = "";
			if (answer == "") {
				content +="<p>Sorry, Nothing to show :(</p>";
			} else {
				var results = answer.split('|');
				var path = results[0];
				for (i = 1; i < results.length; i++) {
					dummy = results[i].split('>');
					//content += '<li class="list-group-item"><a href="' + path + '/' + dummy[1] +'>' + dummy[0] + '</a></li>';
					content += '<a href="/nutty/' + path + '/' + dummy[1] +'">'
							+ dummy[0] + '</a>';
					if(isRequest){
						content += '<a href="javascript:answerRequest('+dummy[1]+',1);"> <img src="http://png-1.findicons.com/files/icons/2226/matte_basic/16/accept.png"/> </a><a href="javascript:answerRequest('+dummy[1]+',0);"> <img src="http://circabc.eu:8080/circabc/faces/images/extension/help/Newsgroups%20-%20Icon%20-%20Post%20rejected.PNG"/> </a>';					
					}
					content += '</p>';
				}}
				bootbox.dialog({
					title : title,
					message : content,
					onEscape: function() {},
				});
		}
		
		function answerRequest(id, answer){
			$.ajax({
				type:"POST",
				url:"/nutty/user/answerFollowRequest",
				data : {
					follower_id : id,
					followed_id : '${user.id}',
					value : answer
				}
			}).done(function(answer){
				if(answer == 1){
					$('.close').trigger('click');
					checkResults();
				}else{
					alert("An error occured!");
				}
			})
		}
		
		/*		
		function showResult(answer) {
		var resultsWindow  = window.open("", "_blank", "left=500, top=350, width=200, height=150, resizable=1, scrollbars=1");
		var results = answer.split('|');
		var path = results[0];
		var content = "<h3>Results:</h3>";
		for (i = 1; i < results.length; i++) {
		dummy = results[i].split('>');
		content += "<li class='list-group-item'><a href='javascript:;' onclick='window.opener.location.href = \" " + path + "/" + dummy[1] +"  \"; window.close();'>" + dummy[0] + "</p></li>";
		}
		content += "<button type='button' onclick='window.close()'> Close </button>";
		
		resultsWindow.document.write(content);
		return false;
		}
		
		$("#searchResults").html("");
						if (answer == "") {
							alert("Sorry, Nothing to show :(");
						} else {
							window.location = "../basicSearchResult";
							var results = answer
									.split('|');
							var path = results[0];
							for (i = 1; i < results.length; i++) {
								dummy = results[i]
										.split('>');
								$("#searchResults")
										.append(
												"<a href='" + path + "/" + dummy[1] + "'>"
														+ dummy[0]
														+ "</p>");*/
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*" %>
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
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	

    <!-- Add custom CSS here -->
    <style>
        body {margin-top: 50px;}
			#main-wrap > div { min-height: 450px; }
		.info { min-height: 80px; }
		
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
		
/* 		search */
#search-form {

    margin: 0 auto;
    
	background: #e1e1e1; /* Fallback color for non-css3 browsers */
	width: 515px;
	
	/* Gradients */
	background: -webkit-gradient( linear,left top, left bottom, color-stop(0, rgb(243,243,243)), color-stop(1, rgb(225,225,225)));
	background: -moz-linear-gradient( center top, rgb(243,243,243) 0%, rgb(225,225,225) 100%);
	
	/* Rounded Corners */
	border-radius: 17px; 
	-webkit-border-radius: 17px;
	-moz-border-radius: 17px;
	
	/* Shadows */
	box-shadow: 1px 1px 2px rgba(0,0,0,.3), 0 0 2px rgba(0,0,0,.3); 
	-webkit-box-shadow: 1px 1px 2px rgba(0,0,0,.3), 0 0 2px rgba(0,0,0,.3);
	-moz-box-shadow: 1px 1px 2px rgba(0,0,0,.3), 0 0 2px rgba(0,0,0,.3);
}

/*** TEXT BOX ***/
input[type="text"]{
	background: #fafafa; /* Fallback color for non-css3 browsers */
	
	/* Gradients */
	background: -webkit-gradient( linear, left bottom, left top, color-stop(0, rgb(250,250,250)), color-stop(1, rgb(230,230,230)));
	background: -moz-linear-gradient( center top, rgb(250,250,250) 0%, rgb(230,230,230) 100%);
	
	border: 0;
	border-bottom: 1px solid #fff;
	border-right: 1px solid rgba(255,255,255,.8);
	font-size: 16px;
	margin: 10px;
	padding: 5px;
	width: 400px;
	
	/* Rounded Corners */
	border-radius: 17px; 
	-webkit-border-radius: 17px;
	-moz-border-radius: 17px;
	
	/* Shadows */
	box-shadow: -1px -1px 2px rgba(0,0,0,.3), 0 0 1px rgba(0,0,0,.2);
	-webkit-box-shadow: -1px -1px 2px rgba(0,0,0,.3), 0 0 1px rgba(0,0,0,.2);
	-moz-box-shadow: -1px -1px 2px rgba(0,0,0,.3), 0 0 1px rgba(0,0,0,.2);
}

/*** USER IS FOCUSED ON TEXT BOX ***/
input[type="text"]:focus{
	outline: none;
	background: #fff; /* Fallback color for non-css3 browsers */
	
	/* Gradients */
	background: -webkit-gradient( linear, left bottom, left top, color-stop(0, rgb(255,255,255)), color-stop(1, rgb(235,235,235)));
	background: -moz-linear-gradient( center top, rgb(255,255,255) 0%, rgb(235,235,235) 100%);
}

/*** SEARCH BUTTON ***/
input[type="submit"]{
	background: #44921f;/* Fallback color for non-css3 browsers */
	
	/* Gradients */
	background: -webkit-gradient( linear, left top, left bottom, color-stop(0, rgb(79,188,32)), color-stop(0.15, rgb(73,157,34)), color-stop(0.88, rgb(62,135,28)), color-stop(1, rgb(49,114,21)));
	background: -moz-linear-gradient( center top, rgb(79,188,32) 0%, rgb(73,157,34) 15%, rgb(62,135,28) 88%, rgb(49,114,21) 100%);
	
	border: 0;
	color: #eee;
	cursor: pointer;
	float: right;
	font: 16px Arial, Helvetica, sans-serif;
	font-weight: bold;
	height: 30px;
	margin: 4px 4px 0;
	text-shadow: 0 -1px 0 rgba(0,0,0,.3);
	width: 84px;
	outline: none;
	
	/* Rounded Corners */
	border-radius: 30px; 
	-webkit-border-radius: 30px;
	-moz-border-radius: 30px;
	
	/* Shadows */
	box-shadow: -1px -1px 1px rgba(255,255,255,.5), 1px 1px 0 rgba(0,0,0,.4);
	-moz-box-shadow: -1px -1px 1px rgba(255,255,255,.5), 1px 1px 0 rgba(0,0,0,.2);
	-webkit-box-shadow: -1px -1px 1px rgba(255,255,255,.5), 1px 1px 0 rgba(0,0,0,.4);
}
/*** SEARCH BUTTON HOVER ***/
input[type="submit"]:hover {
	background: #4ea923; /* Fallback color for non-css3 browsers */
	
	/* Gradients */
	background: -webkit-gradient( linear, left top, left bottom, color-stop(0, rgb(89,222,27)), color-stop(0.15, rgb(83,179,38)), color-stop(0.8, rgb(66,143,27)), color-stop(1, rgb(54,120,22)));
	background: -moz-linear-gradient( center top, rgb(89,222,27) 0%, rgb(83,179,38) 15%, rgb(66,143,27) 80%, rgb(54,120,22) 100%);
}
input[type="submit"]:active {
	background: #4ea923; /* Fallback color for non-css3 browsers */
	
	/* Gradients */
	background: -webkit-gradient( linear, left bottom, left top, color-stop(0, rgb(89,222,27)), color-stop(0.15, rgb(83,179,38)), color-stop(0.8, rgb(66,143,27)), color-stop(1, rgb(54,120,22)));
	background: -moz-linear-gradient( center bottom, rgb(89,222,27) 0%, rgb(83,179,38) 15%, rgb(66,143,27) 80%, rgb(54,120,22) 100%);
}
 
    </style>

  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="../../">Nutty</a>
        </div>
<!-- 		  <ul class="nav navbar-nav navbar-search"> -->
<!--             <input type="text" class="search-query span3" placeholder="Search..."> -->
<!--           </ul> -->
		  <ul class="nav navbar-nav navbar-right">
		    <li><a href="${user.id}">My Profile</a></li>
		    <li><a href="../../logout">Logout</a></li>
		    <li id="settings" class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a><ul role="menu" class="dropdown-menu"><li id="popular"><a href="../homesettings">Profile Settings</a></li><li id="app"><a href="../preferences">Food Preferences</a></li></li>
		  </ul>
        

      </div><!-- /.container -->
    </nav>

    
	<div id="main-wrap">
	    <div id="sidebar" align="center">
	    	<div id="foto">
<!-- 	    		<img id="profilePic" alt="Photo of the User" src="http://cdn.sett.com/images/user/20140502/chef57b22ab552661a6852fe44c0e7e59e63.jpg" width="100%" height="auto"/> -->
	    	</div>
	    	<br><br><br>
	    	<h2>Best Of</h2>
	    	<br><br>
	    	<div id="bestOf"><p>Loading Recipes, please wait..</p></div>
	    </div>
	    <div id="content-wrap">
	        <div id="info-wrap">
	            <div class="info" align="center">
	            	<p>&nbsp;</p>
	            	<h1 id="name">${visitedUser.name} ${visitedUser.surname}</h1>
	            	<h3>Age:</h3> <p id="age"></p>
	            	<h3>Gender:</h3> <p id="gender">Not specified</p>
	            	<h3>Food Intolerances:</h3> 
	            	<p id="food_int"> None </p>
	            	<h3>Health Conditions:</h3>
	            	<p id="health_cond"> None </p>
	            	<h3>Not Preferred:</h3>
	            	<p id="not_pref"> None </p>
	        	</div>
	        	<div class="info" align="center">
	        	    <button type="button" class="btn btn-primary" value="Follow" id="Follow"
						style="float: center; margin-right: 15 px; margin-top: 18px;">
						<span id="textFollow" class="ui-button-text">Follow &nbsp </span>
						<span id="followCheck" class="glyphicon glyphicon-check" style="visibility: hidden;"></span>
						<!-- caret for arrow. ui-button-text for button text visible; -->
					</button>
	        	</div>
            	<div class="info" align="center">
	            	<p>&nbsp;</p>
	            	<img alt="" src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg" width="50px" height="auto">
	            	<img alt="" src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg" width="50px" height="auto">
	            	<img alt="" src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg" width="50px" height="auto">
	            	<img alt="" src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg" width="50px" height="auto">
	            	<img alt="" src="http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg" width="50px" height="auto">
	            	<p>&nbsp;</p>
	            	<h2>Master Chef</h2>
	            	<h3>&nbsp;</h3>
	            	<h3>${numberOfFollowers} followers</h3>
	            	<h3>${numberOfFollowing} following</h3>
				</div>
		    </div>
        	<h1 align="center">News Feed</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
        	<h1 align="center">.</h1>
		</div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function () {
		if('${visitedUser.gender}' == 1){
			document.getElementById('foto').innerHTML = '<img id="profilePic" alt="Photo of the User" src="http://cdn.sett.com/images/user/20140502/chef57b22ab552661a6852fe44c0e7e59e63.jpg" width="100%" height="auto"/>';	
		}else{
			document.getElementById('foto').innerHTML = '<img id="profilePic" alt="Photo of the User" src="http://thumbs.dreamstime.com/x/funny-chef-cracking-egg-6967190.jpg" width="100%" height="auto"/>';
		}
	});	
	
	$(document).ready(function () {
		if('${visitedUser.gender}' == 0){
			document.getElementById('gender').innerHTML = "Male";
		}else{
			document.getElementById('gender').innerHTML = "Female";
		}  	
  	});	
	
	$(document).ready(function () {
		  var birthday = +new Date('${visitedUser.birthday}');
		  document.getElementById('age').innerHTML = ~~((Date.now() - birthday) / (31557600000));
	});
	
	$(document).ready(function () {
    	$.ajax({
			  type: "POST",
			  url: "../getUsersRecipes",
			  data: {  
  				  userId: '${visitedUser.id}',
  				  }
			})
			.done(function(response) {
				if(response != null){
					$('#bestOf').empty();
					for(var i=0; i<response.length; i++){
						var href = "../../recipe/" + response[i][0];
						var name = response[i][1];
						var src = response[i][2];
						$('#bestOf').append("<a href='"+href+"'><img src='"+src+"' title='"+name+"' onError='this.onerror=null;this.src=\"http://img2.wikia.nocookie.net/__cb20130511180903/legendmarielu/images/b/b4/No_image_available.jpg\";' width='50%' height='auto'><p>"+name+"</p></a>");					}
				}else{
					$('#bestOf').empty();
					$('#bestOf').append("User doesn't own any recipe :(");
				}
			});   	
  	});	
	
   	var isFollower = '${isFollower}';
	
	$(document).ready(function () {
		if (isFollower == 'true'){
			$("#followCheck").css('visibility','visible');  
		}
		else{
			$("#followCheck").css('visibility','hidden');  
		}
	});	
    	
	$("#Follow").click(function() {
		if (isFollower == 'true') {
			$("#followCheck").css('visibility','hidden'); 
			isFollower = 'false';
			followUser(0);
		} else {
			$("#followCheck").css('visibility','visible');
			isFollower == 'true'
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
				value: value
			}
		})	
	};

// 	$(document).ready(function () {
//     	$.ajax({
// 			  type: "POST",
// 			  url: "CheckFoodIntoleranceServlet",
// 			})
// 			.done(function(msg) {
// 				if(msg.length != 0 && msg.indexOf("%") > -1){
// 					var prefs = (msg.slice(0, -1)).split("%")
// 					document.getElementById('food_int').innerHTML = prefs;
// 				}
// 			});   	
//   	});	
	
// 	$(document).ready(function () {
//     	$.ajax({
// 			  type: "POST",
// 			  url: "CheckHealthConditionServlet",
// 			})
// 			.done(function(msg) {
// 				if(msg.length != 0 && msg.indexOf("%") > -1){
// 					var prefs = (msg.slice(0, -1)).split("%")
// 					document.getElementById('health_cond').innerHTML = prefs;
// 				}
// 			});   	
//   	});	
	
// 	$(document).ready(function () {
//     	$.ajax({
// 			  type: "POST",
// 			  url: "CheckNonPreferredServlet",
// 			})
// 			.done(function(msg) {
// 				if(msg.length != 0 && msg.indexOf("%") > -1){
// 					var prefs = (msg.slice(0, -1)).split("%")
// 					document.getElementById('not_pref').innerHTML = prefs;
// 				}
// 			});   	
//   	});	
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*" %>
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
		    height: 350px;
		    background: #2EFE2E;
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
    </style>

  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href=".">Nutty</a>
        </div>
		  <ul class="nav navbar-nav navbar-right">
		    <li><a>Settings</a></li>
		    <li><a href="profilinfo.jsp">Info</a></li>
		    <li><a href="logout.jsp">Logout</a></li>
		  </ul>
        

      </div><!-- /.container -->
    </nav>

    
	<div id="main-wrap">
	    <div id="sidebar" align="center">
	    	<div id="foto">
	    		<h2>Photo of the User</h2>
	    	</div>
	    	<h2>Best Of</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    	<h2>.</h2>
	    </div>
	    
	    <div id="content-wrap">
	        <div id="info-wrap">
	            <div class="info" align="center">
	            	<p>&nbsp;</p>
	            	<h1>Name Surname:</h1> <h3 id="full_name"></h3>
	            	<h3>Age:</h3> <p id="age">Not specified</p>
	            	<h3>Gender:</h3> <p id="gender">Not specified</p>
	            	<h3>Food Intolerances:</h3> 
	            	<p id="food_int"> None </p>
	            	<h3>Health Conditions:</h3>
	            	<p id="health_cond"> None </p>
	            	<h3>Not Preferred:</h3>
	            	<p id="not_pref"> None </p>
	            <div class="info" align="center">
	            	<p>&nbsp;</p>
	            	<h1>*****</h1>
	            	<h2>Master Chef</h2>
	            	<h3>&nbsp;</h3>
	            	<h3>120 followers</h3>
	            	<h3>120 following</h3>
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
    	$.ajax({
			  type: "POST",
			  url: "CheckFoodIntoleranceServlet",
			})
			.done(function(msg) {
				if(msg.length != 0 && msg.indexOf("%") > -1){
					var prefs = (msg.slice(0, -1)).split("%")
					document.getElementById('food_int').innerHTML = prefs;
				}
			});   	
  	});	
	
	$(document).ready(function () {
    	$.ajax({
			  type: "POST",
			  url: "CheckHealthConditionServlet",
			})
			.done(function(msg) {
				if(msg.length != 0 && msg.indexOf("%") > -1){
					var prefs = (msg.slice(0, -1)).split("%")
					document.getElementById('health_cond').innerHTML = prefs;
				}
			});   	
  	});	
	
	$(document).ready(function () {
    	$.ajax({
			  type: "POST",
			  url: "CheckNonPreferredServlet",
			})
			.done(function(msg) {
				if(msg.length != 0 && msg.indexOf("%") > -1){
					var prefs = (msg.slice(0, -1)).split("%")
					document.getElementById('not_pref').innerHTML = prefs;
				}
			});   	
  	});	
	
	$(document).ready(function () {
    	$.ajax({
			  type: "POST",
			  url: "CheckFullNameServlet",
			})
			.done(function(msg) {
				if(msg.length != 0){
					document.getElementById('full_name').innerHTML = msg;
				}
			});   	
  	});	
	
	$(document).ready(function () {
    	$.ajax({
			  type: "POST",
			  url: "CheckBirthdayServlet",
			})
			.done(function(msg) {
				if(msg.length != 0){
					document.getElementById('age').innerHTML = calcAge(msg);
				}
			});   	
  	});	
	
	$(document).ready(function () {
    	$.ajax({
			  type: "POST",
			  url: "CheckGenderServlet",
			})
			.done(function(msg) {
				if(msg.length != 0){
					document.getElementById('gender').innerHTML = msg;
				}
			});   	
  	});	
	
	function calcAge(dateString) {
		  var birthday = +new Date(dateString);
		  return ~~((Date.now() - birthday) / (31557600000));
	}
	</script>
</body>
</html>
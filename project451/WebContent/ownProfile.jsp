<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>NuttyTest</title>

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
          <a class="navbar-brand" href=".">Nutty Test</a>
        </div>
		  <ul class="nav navbar-nav navbar-right">
		    <li><a href="javascript:settings();">Settings</a></li>
		    <li><a href="">Logout</a></li>
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
	            	<h1>Name Surname</h1>
	            	<h3>Age</h3>
	            	<h3>Gender</h3>
	            	<h3 id="pref">No Preferences</h3>
	            	<h3>.</h3>
	            	<h3>.</h3>
	            </div>
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
			  data: {},
			})
			.done(function(msg) {
				if(msg.length != 0){
					document.getElementById('pref').innerHTML = msg;
					alert(msg);
				}
			});
    	
  	});	
	
	function settings(){
    	window.location.href = "profilinfo.jsp";
  	} 
	</script>
</body>
</html>
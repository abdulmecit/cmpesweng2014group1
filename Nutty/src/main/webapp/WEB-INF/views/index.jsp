<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

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
        body {margin-top: 60px;}
        
        /****************************************
	CSS 3 SEARCH FORM BY CAMERON BANEY
    Design Blog: http://blog.cameronbaney.com 
	Twitter: @cameronbaney
****************************************/

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
	margin: 4px;
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
        
        @import url(http://fonts.googleapis.com/css?family=Varela+Round);

		.slides {
		    padding: 0;
		    width: 609px;
		    height: 420px;
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
		
		.slides input { display: none; }
		
		.slide-container { display: block; }
		
		.slide {
		    top: 0;
		    opacity: 0;
		    width: 609px;
		    height: 420px;
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
		    width: 200px;
		    height: 100%;
		    display: none;
		    position: absolute;
		
			opacity: 0;
		    z-index: 9;
		    cursor: pointer;
		
		    transition: opacity .2s;
		
		    color: #FFF;
		    font-size: 156pt;
		    text-align: center;
		    line-height: 380px;
		    font-family: "Varela Round", sans-serif;
		    background-color: rgba(255, 255, 255, .3);
		    text-shadow: 0px 0px 15px rgb(119, 119, 119);
		}
		
		.slide:hover + .nav label { opacity: 0.5; }
		
		.nav label:hover { opacity: 1; }
		
		.nav .next { right: 0; }
		
		input:checked + .slide-container  .slide {
		    opacity: 1;
		
		    transform: scale(1);
		
		    transition: opacity 1s ease-in-out;
		}
		
		input:checked + .slide-container .nav label { display: block; }
		
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
		
		input#img-1:checked ~ .nav-dots label#img-dot-1,
		input#img-2:checked ~ .nav-dots label#img-dot-2,
		input#img-3:checked ~ .nav-dots label#img-dot-3,
		input#img-4:checked ~ .nav-dots label#img-dot-4 {
			background: rgba(0, 0, 0, 0.8);
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
		    <% 	if (session.getAttribute("isLogged") == null || ((Boolean)(session.getAttribute("isLogged")) == false)){ %>		  
	    		<li><a href="login">Login</a></li>
	    		<li><a href="signup">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="user/profile">My Profile</a></li>	    	
	    		<li><a href="logout">Logout</a></li>
	    		<li id="settings" class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><img alt="Settings" src="http://www.foxconnbuilders.com/img/gear.png" width="20px" height="20px"/><i class="fa fa-caret-down"></i></a><ul role="menu" class="dropdown-menu"><li id="popular"><a href="/nutty/user/homesettings">Profile Settings</a></li><li id="app"><a href="/nutty/user/healthSettings">Health Settings</a></li></li>
	    	<%}%>	    				    
		  </ul>
<!-- 		  <ul class="nav navbar-nav navbar-search navbar-right"> -->
<!--             <input type="text" class="search-query span3" placeholder="Search"><ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins> -->
<!--           </ul> -->
        
      </div><!-- /.container -->
    </nav>
    <br><br><br>
	<form id="search-form">
        <input type="text">
        <input type="submit" value="Search">
	</form>
	
	<br><br>
	
	 <ul class="slides">
	    <input type="radio" name="radio-btn" id="img-1" checked />
	    <li class="slide-container">
			<div class="slide">
				<h3 align="center">Delicious</h3>
	        	<a href="/nutty"><button><img src="http://2.bp.blogspot.com/-fHFQXiIq6IQ/TegYuDrnq-I/AAAAAAAAQAQ/AaB71suJeFk/s1600/Kobe+Beef-0094.jpg" /></button></a>
	        </div>
			<div class="nav">
				<label for="img-4" class="prev">&#x2039;</label>
				<label for="img-2" class="next">&#x203a;</label>
			</div>
	    </li>
	
	    <input type="radio" name="radio-btn" id="img-2" />
	    <li class="slide-container">
	        <div class="slide">
	        	<h3 align="center">Healthy</h3>
	        	<a href="/nutty"><button><img src="http://sandiegobargainmama.com/wp-content/uploads/2011/09/balanced-meal.png" /></button></a>
	        </div>
			<div class="nav">
				<label for="img-1" class="prev">&#x2039;</label>
				<label for="img-3" class="next">&#x203a;</label>
			</div>
	    </li>
	
	    <input type="radio" name="radio-btn" id="img-3" />
	    <li class="slide-container">
	        <div class="slide">
	        	<h3 align="center">Easy</h3>
	        	<a href="/nutty"><button><img src="http://www.damakdiyari.com.tr/content/images/thumbs/0000588_sahanda-menemen.jpeg" /></button></a>
	        </div>
			<div class="nav">
				<label for="img-2" class="prev">&#x2039;</label>
				<label for="img-4" class="next">&#x203a;</label>
			</div>
	    </li>
	
	    <input type="radio" name="radio-btn" id="img-4" />
	    <li class="slide-container">
	        <div class="slide">
				<h3 align="center">Cheap</h3>
				<a href="/nutty"><button><img src="http://kumsaldakiizler.com/wp-content/uploads/makarna_zerrinin_mutfa%C4%9F%C4%B1_domates_biber_soslu_makarna.jpg" /></button></a>
	        </div>
			<div class="nav">
				<label for="img-3" class="prev">&#x2039;</label>
				<label for="img-1" class="next">&#x203a;</label>
			</div>
	    </li>
		
	    <li class="nav-dots">
	      <label for="img-1" class="nav-dot" id="img-dot-1"></label>
	      <label for="img-2" class="nav-dot" id="img-dot-2"></label>
	      <label for="img-3" class="nav-dot" id="img-dot-3"></label>
	      <label for="img-4" class="nav-dot" id="img-dot-4"></label>
	    </li>
	</ul>
	

  </body>
</html>
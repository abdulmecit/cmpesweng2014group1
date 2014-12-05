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

<style>
body {
	margin-top: 80px;
}

#section {
	width: 280px;
	height: 150px;
	float: left;
	padding: 10px;
	margin-right: 10px;
	margin-left: 4px;
}

#sec2 {
	width: 1000px;
	height: 250px;
	float: none;
	padding: 10px;
	margin-right: 10px;
	margin-left: 4px;
}
</style>
</head>


<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="../">Nutty</a>
		</div>
		  <ul class="nav navbar-nav navbar-right">
		    <% 	if (session.getAttribute("isLogged") == null || ((Boolean)(session.getAttribute("isLogged")) == false)){ %>		  
	    		<li><a href="login">Login</a></li>
	    		<li><a href="signup">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="profile/${user.id}">My Profile</a></li>
	    		<li><a href="../logout">Logout</a></li>
	    		<li id="settings" class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a><ul role="menu" class="dropdown-menu"><li id="popular"><a href="homesettings">Profile Settings</a></li><li id="app"><a href="preferences">Food Preferences</a></li></li>
	    	<%}%>	    				    
		  </ul>
	</div>
	</nav>

<div class="container">
	<div id="sec2">
		<form name="foodForm" method="post" action="FoodIntoleranceServlet">
			<h4>Select Your Food Intolerance and Allergies</h4>

			<div id="section">
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="lactose intolerance" /> Lactose Intolerance
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="gluten intolerance" /> Gluten Intolerance
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="fruit allergy" /> Fruit Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="garlic allergy" /> Garlic Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="oat allergy" /> Oat Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="egg allergy" /> Egg Allergy
				</p>
			</div>

			<div id="section">
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="meat allergy" /> Meat Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="milk allergy" /> Milk Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="peanut allergy" /> Peanut Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="fish allergy" /> Fish Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="soy allergy" /> Soy Allergy
				</p>
			</div>

			<div id="section">

				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="tree nut allergy" /> Tree Nut Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="wheat allergy" /> Wheat Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="hot peppers allergy" /> Hot Peppers Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="sulfites allergy" /> Sulfites Allergy
				</p>
				<p>
					<input type="checkbox" name="Food Intolerance and Allergies"
						value="tartrazine allergy" /> Tartrazine Allergy
				</p>
			</div>

			<input type="submit" value="Submit" />
			<br><br>
			<font color="blue">${res}</font>
		</form>
	</div>


	<div id="sec2">
		<form name="DiseaseForm" method="post" action="HealthConditionServlet">
			<h4>Select Your Diseases</h4>
			<div id="section">
				<p>
					<input type="checkbox" name="disease" value="reflux" /> Reflux
				</p>
				<p>
					<input type="checkbox" name="disease" value="ulcer" /> Ulcer
				</p>
				<p>
					<input type="checkbox" name="disease" value="gastritis" />
					Gastritis
				</p>
			</div>

			<div id="section">
				<p>
					<input type="checkbox" name="disease" value="diabetes" /> Diabetes
				</p>
				<p>
					<input type="checkbox" name="disease" value="cholesterol" />
					Cholesterol
				</p>
			</div>

			<div id="section">
				<p>
					<input type="checkbox" name="disease" value="heart condition" />
					Heart Condition
				</p>
				<p>
					<input type="checkbox" name="disease" value="high blood pressure" />
					High Blood Pressure
				</p>
			</div>
			<input type="submit" value="Submit" />
			<br><br>
			<font color="blue">${res2}</font>
		</form>
		
		
		<div id="section">
		<form name="Others" method="post" action="NonPreferredServlet">
			<h4>Other Non-Preferred Ingredients</h4>
			<input type="text" id="OtherPreferences" name="OtherPreferences" /> <input
				type="submit" value="Add" />
			<br><br>
			<font color="blue">${res3}</font>
		</form>
		</div>
	</div>
</div>
</body>
</html>
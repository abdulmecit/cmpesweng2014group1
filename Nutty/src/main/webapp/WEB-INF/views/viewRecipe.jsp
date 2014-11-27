<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cmpesweng2014.group1.nutty.model.Message"%>
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
	    		<li><a href="../login">Login</a></li>
	    		<li><a href="../signup">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="../user/profile">My Profile</a></li>
	    		<li><a href="../logout">Logout</a></li>
	    		<li id="settings" class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a><ul role="menu" class="dropdown-menu"><li id="popular"><a href="/nutty/user/homesettings">Profile Settings</a></li><li id="app"><a href="/nutty/user/preferences">Food Preferences</a></li></li>
	    	<%}%>	    				    
		  </ul>  

      </div><!-- /.container -->
    </nav>
       
   
      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
        	<img src= "${photoUrl}"/>
        	        	
        	<h3>Recipe Information</h3>
			<p>Name: ${recipe.name}</p>
			<p>Description: ${recipe.description}</p>
			<p>Portion: ${recipe.portion}</p>
			<p>Created On: ${recipe.createdDate}</p>
			<p>Last Updated On: ${recipe.updatedDate}</p>
			<p>Total Calories: ${recipe.total_calorie}</p>
			
			<p>OwnerId: ${ownerId}</p>
			
			<h3>Tags</h3>			
			<c:forEach var="tag" items="${tags}">
   				Id: <c:out value="${tag.tag_id}"/>
   				Name: <c:out value="${tag.tag_name}"/>
   				<br>
			</c:forEach>
			
			<h3>Ingredients</h3>			
			<c:forEach var="ingredientAmount" items="${ingredientAmounts}">
   				Id: <c:out value="${ingredientAmount.ing_id}"/>
   				Name: <c:out value="${ingredientAmount.ing_name}"/>
   				Amount: <c:out value="${ingredientAmount.amount}"/>	
   				<br>
			</c:forEach>
			
			<h3>Comments</h3>			
			<c:forEach var="commentAndLike" items="${commentsAndLikes}">
   				Id: <c:out value="${commentAndLike.key.comment_id}"/>
   				Text: <c:out value="${commentAndLike.key.text}"/>
   				UserId: <c:out value="${commentAndLike.key.user_id}"/>	
   				Likes: <c:out value="${commentAndLike.value}"/>	
   				<br>
			</c:forEach>
			
			<h3>Recipe Ratings</h3>
			<p>Likes: ${noOfLikes}</p>
			<p>Eaten: ${noOfEats}</p>
			<p>Avg Health Rate: ${avgHealthRate}</p>
			<p>Avg Cost Rate: ${avgCostRate}</p>
			<p>Avg Taste Rate: ${avgTasteRate}</p>
			<p>Avg Ease Rate: ${avgEaseRate}</p>
			
			<h4>Recipe Tree</h4>
			<p>ParentId: ${parent.recipe_id}</p>
			<p>ParentName: ${parent.name}</p>		
			<p>Children:</p>
			<c:forEach var="child" items="${children}">
				Child:
   				Id: <c:out value="${child.recipe_id}"/>
   				Name: <c:out value="${child.name}"/>
   				<br>
			</c:forEach>
		</div>
		<br>
		<div class="col-md-offset-3 col-md-6 well">
		<h4>Comments:</h4>
			<div id="comments">
			</div>
		</div>
	 </div>
   
</body>

<script type="text/javascript">

$(document).ready(function (){
	  $.ajax({
		  type: "POST",
		  url: "../recipeComments",
		  data: {  
			  recipeId:'${recipe.recipe_id}',
			  }
		}) 
		.done(function(answer) {
			$( "#comments" ).html("");
			if(answer == ""){
				$( "#comments" ).append( "<p>No comments :(</p>" );
			}else{
				var recipes = answer.split('|');
				for (i = 1; i < recipes.length; i++) {
					dummy = recipes[i].split('>');
					$( "#comments" ).append( "<p><b>" + dummy[1] + "</b></p><p>" + dummy[0] + "</p>" );
				}
			}
		});
});

</script>

</html>
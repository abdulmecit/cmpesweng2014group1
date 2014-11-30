<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.sql.*"%>
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
#dropArea {text-align: center; line-height: 50px; line-width: 50px; margin: auto; font-size: 15px; display: inline-block;}
#progress {display: none}
.uploading #dropArea {display: none}
.uploaded #dropArea {display: none}
.uploading #progress {display: inline}
.uploaded #progress {display: inline}
</style>

</head>

<body>

	<!------------------------ navigation bar --------------------------->


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
	    		<li id="settings" class="dropdown"><a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-cog"></span><i class="fa fa-caret-down"></i></a><ul role="menu" class="dropdown-menu"><li id="popular"><a href="/nutty/user/homesettings">Profile Settings</a></li><li id="app"><a href="/nutty/user/preferences">Food Preferences</a></li></li>
	    	<%}%>	    				    
		  </ul>  

      </div><!-- /.container -->
    </nav>
    
	<div class="container">
		<form method="POST">
			<div class="panel panel-default">
				<div class="panel-heading clearfix">
					<h2 class="panel-title pull-left" style="padding-top: 7.5px;">Create
						New Recipes</h2>
					<button type="submit" value="submit" id="submit"
						class="btn btn-primary" style="float: right;" disabled>Create
						Recipe</button>
				</div>

				<div class="panel-body">
					<div class="col-sm-12">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h2 class="panel-title pull-left" style="padding-top: 7.5px;">Name, Portion, Tags & Photo</h2>
							</div>
							<div class="panel-body">
								<div class="row">
							    	<div class="col-xs-9">
										<input type="text" class="form-control" id="recipeName"
											name="recipeName" placeholder="Name of Recipe...">
							      	</div>
							      	<div class="col-xs-3">
										<input type="text" class="form-control" id="portion"
											name="portion" placeholder="portion">
								 	</div>
								</div>
								<br>
								<div id="dynamicInput3" class="col-sm-3" align="left">
									<input type="text" class="form-control" id="tag" name="tag[]"
										placeholder=" Write Tags of Recipe"> <br> <input
										type="text" class="form-control" id="tag" name="tag[]"
										placeholder="italian cuisine..."> <br> <input
										type="text" class="form-control" id="tag" name="tag[]"
										placeholder="chicken..."> <br> <input type="text"
										class="form-control" id="tag" name="tag[]"
										placeholder="soup...">
								</div>
								<div id="dynamicInput3" class="col-sm-3" align="left">
									<input type="text" class="form-control" id="tag" name="tag[]"
										placeholder="soy sauce..."> <br> <input
										type="text" class="form-control" id="tag" name="tag[]"
										placeholder=" chilli sauce..."> <br> <input
										type="text" class="form-control" id="tag" name="tag[]"
										placeholder="easy..."> <br> <input type="text"
										class="form-control" name="tags[]" id="tag"
										placeholder="cheap...">
								</div>
								<div id="dynamicInput3" class="col-sm-6" align="left">
									<div class="panel panel-default">

										<!------------------------  Photo  --------------------------->
										<div class="panel-body" style="height: 200px">
										
										<div id="dropArea">Drag and drop your recipe picture here! OR 
											<button onclick="document.querySelector('#elma').click()">Choose from your computer</button>
										</div>
				
										<input id="elma" style="visibility: collapse; width: 0px;" type="file" onchange="upload(this.files[0])">
										
										<input type="hidden" class="form-control" id="link" name="link"></input>
										
										<p id="progress">Uploading...</p>
										
										</div>
										<!----------------------- Ends of photo --------------------------->
									</div>
								</div>
							</div>
						</div>
					</div>

					<!------------------------  Get Ingredients  --------------------------->
					<div class="col-sm-6">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h4 class="panel-title pull-left" style="padding-top: 7.5px;">Amount
									/ Ingredients</h4>
								<input type="button" value="Another ingredient"
									class="btn btn-primary"
									onClick="addInput('dynamicInput','dynamicInput2');"
									style="float: right;">
							</div>

							<div class="panel-body">
								<div id="dynamicInput" class="col-sm-4" align="left">
									<p>
										<input type="text" class="form-control" id="amount"
											name="amount[]" placeholder="2pounds,etc">
									</p>
								</div>
								<div id="dynamicInput2" class="col-sm-8" align="left">
									<p>
										<input type="text" class="form-control" id="ingredient"
											name="ingredient[]" placeholder="sugar,etc.">
									</p>
								</div>
							</div>
						</div>
					</div>

					<!------------------------  Get Directions  --------------------------->

					<div class="col-sm-6">
						<div class="panel panel-default">
							<div class="panel-heading clearfix">
								<h4 class="panel-title " style="padding-top: 7.5px;">Directions</h4>
							</div>
							<div class="panel-body">
								<textarea class="form-control" id="description"
									name="description" rows="13"></textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!---------------------------  Functions  ------------------------------>
	<script type="text/javascript">
			var counter = 1;
			function addInput(divName, div2) {
				var newdiv1 = document.createElement('div');
				var newdiv2 = document.createElement('div');
				newdiv1.innerHTML = "<p> <input type='text' class='form-control' id='amount' name='amount[]' placeholder='2pounds,etc'> </p>"
				newdiv2.innerHTML = "<p> <input type='text' class='form-control' id='ingredient' name='ingredient[]' placeholder='sugar,etc'> </p>";
				document.getElementById(divName).appendChild(newdiv1);
				document.getElementById(div2).appendChild(newdiv2);
				counter++;
			}

			$("#recipeName").keyup(
					function(event) {
						if ($("#recipeName").val().length != 0
								&& $("#portion").val().length != 0
								&& $("#ingredient").val().length != 0
								&& $("#amount").val().length != 0
								&& $("#description").val().length != 0) {
							$('#submit').attr("disabled", false);
						} else {
							$('#submit').attr("disabled", true);
						}
					});
			$("#portion").keyup(
					function(event) {
						if ($("#recipeName").val().length != 0
								&& $("#portion").val().length != 0
								&& $("#ingredient").val().length != 0
								&& $("#amount").val().length != 0
								&& $("#description").val().length != 0) {
							$('#submit').attr("disabled", false);
						} else {
							$('#submit').attr("disabled", true);
						}
					});
			$("#ingredient").keyup(
					function(event) {
						if ($("#recipeName").val().length != 0
								&& $("#portion").val().length != 0
								&& $("#ingredient").val().length != 0
								&& $("#amount").val().length != 0
								&& $("#description").val().length != 0) {
							$('#submit').attr("disabled", false);
						} else {
							$('#submit').attr("disabled", true);
						}
					});
			$("#amount").keyup(
					function(event) {
						if ($("#recipeName").val().length != 0
								&& $("#portion").val().length != 0
								&& $("#ingredient").val().length != 0
								&& $("#amount").val().length != 0
								&& $("#description").val().length != 0) {
							$('#submit').attr("disabled", false);
						} else {
							$('#submit').attr("disabled", true);
						}
					});
			$("#description").keyup(
					function(event) {
						if ($("#recipeName").val().length != 0
								&& $("#portion").val().length != 0
								&& $("#ingredient").val().length != 0
								&& $("#amount").val().length != 0
								&& $("#description").val().length != 0) {
							$('#submit').attr("disabled", false);
						} else {
							$('#submit').attr("disabled", true);
						}
					});
			
			window.ondragover = function(e) {e.preventDefault()}
		    window.ondrop = function(e) {e.preventDefault(); upload(e.dataTransfer.files[0]); }
		    
		    function upload(file) {
		        if (!file || !file.type.match(/image.*/)) return;
		        document.body.className = "uploading";
		        var fd = new FormData(); 
		        fd.append("image", file); 
		        var xhr = new XMLHttpRequest(); 
		        xhr.open("POST", "https://api.imgur.com/3/image.json"); 
		        xhr.onload = function() {
		            document.querySelector("#link").value = JSON.parse(xhr.responseText).data.link;
		            document.querySelector("#progress").innerHTML = "Done!";
		            document.body.className = "uploaded";
		        }
		        
		        xhr.setRequestHeader('Authorization', 'Client-ID 4aaaa88af99c596'); 		        
		        xhr.send(fd);
		    }
		</script>
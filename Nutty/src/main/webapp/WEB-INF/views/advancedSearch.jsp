<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style type="text/css">
	.loading #loadingScreen {
	    display: block;
	    position: absolute;
	    top: 0;
	    left: 0;
	    z-index: 100;
	    width: 100vw;
	    height: 100vh;	
	    background-color: rgba(192, 192, 192, 0.5);
	    background-image: url("http://i.stack.imgur.com/MnyxU.gif");
	    background-repeat: no-repeat;
	    background-position: center;
	}
	.loaded #loadingScreen {
		display: none;
	}
	@import url(http://fonts.googleapis.com/css?family=Varela+Round);
</style>

<body>
	<div id=loadingScreen></div>
	<div class="container">
		<div class="panel panel-default">

			<div class="panel-heading clearfix">
				<h2 class="panel-title pull-left" style="padding-top: 7.5px;">Advanced
					search</h2>
			</div>

			<div class="panel-body">
				<form id="AdvSearch" method="POST">

					<!-- /input-group -->
					<div class="input-group">
						<input type="text" class="form-control" id="searchKey" name="search"
							placeholder="tags"> <span class="input-group-btn">
							<button class="btn btn-primary" type="submit" id="search">Search!</button>
						</span>
					</div>

					<br> <br>

					<div class="row">
						<div class="col-sm-4">
							<b style="vertical-align: middle;">Calories between:</b>
						</div>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="caloriesMin" name="calorieIntervalLow"
								placeholder="min">
						</div>
						<div class="col-xs-1" style="text-align: center;">-</div>
						<div class="col-sm-2">
							<input type="text" class="form-control" id="caloriesMax" name="calorieIntervalHigh"
								placeholder="max">
						</div>
					</div>
					<br> <br>
					<div class="row">
						<div class="col-sm-4">
							<b>Must have ingredients:</b> <br> <br> <input
								type="button" value="Another ingredient" class="btn btn-default"
								onClick="addInput('dynamicInput');" style="float: left;">
						</div>
						<div class="col-sm-7">
							<div id="dynamicInput" align="left">
								<p>
									<input type="text" class="form-control" id="ingredient"
										name="mustHaveIngredients[]" placeholder="sugar,etc.">
								</p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4"></div>
						<div class="col-sm-7">
							<div class="checkbox">
								<label> <input type="checkbox" id="foodPreferences" name="enableFoodSelection"> 
									<b>Consider my food preferences</b>
								</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4"></div>
						<div class="col-sm-7">
							<div class="checkbox">
								<label> <input type="checkbox" id="eatenRecipes" name="enableEaten"> 
								<b>Don't show eaten recipes</b>
								</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4"></div>
						<div class="col-sm-7">
							<div class="checkbox">
								<label> <input type="checkbox" id="JustMyTags" name="disableSemantic"> 
								<b>Just use my tags</b>
								</label>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="panel panel-default">
				<div class="panel-body">
					<h5 style="text-align: center;">Results According to</h5>
					<ul class="nav nav-tabs nav-justified">
						<li role="presentation" class="filter active" id="overall"><a
							class="btn btn-link">Overall</a></li>
						<li role="presentation" class="filter" id="likes"><a
							class="btn btn-link">Like</a></li>
						<li role="presentation" class="filter" id="taste"><a
							class="btn btn-link">Taste Rate</a></li>
						<li role="presentation" class="filter" id="health"><a
							class="btn btn-link">Health Rate </a></li>
						<li role="presentation" class="filter" id="cost"><a
							class="btn btn-link">Cost Rate</a></li>
						<li role="presentation" class="filter" id="ease"><a
							class="btn btn-link">Ease Rate</a></li>
					</ul>
					<ul id="overallContent" class="list-group"
						style="overflow: scroll; display: block;">
						<li><br>
							<p>Loading, please wait..</p> <br></li>
					</ul>
					<ul id="likeContent" class="list-group"
						style="overflow: scroll; display:none;">
						<li><br>
							<p>Loading, please wait..</p> <br></li>
					</ul>
					<ul id="tasteContent" class="list-group"
						style="overflow: scroll; display:none;">
						<li><br>
							<p>Loading, please wait..</p> <br></li>
					</ul>
					<ul id="healthContent" class="list-group"
						style="overflow: scroll; display:none;">
						<li><br>
							<p>Loading, please wait..</p> <br></li>
					</ul>
					<ul id="costContent" class="list-group"
						style="overflow: scroll; display:none;">
						<li><br>
							<p>Loading, please wait..</p> <br></li>
					</ul>
					<ul id="easeContent" class="list-group"
						style="overflow: scroll; display:none;">
						<li><br>
							<p>Loading, please wait..</p> <br></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		
		$(document).ready(function(){
			var searchKey = '${searchKey}';
			if(searchKey != null && searchKey.trim().length > 0){
				$("#searchKey").val(searchKey);
				$("#results").empty();
				document.body.className = "loading";
				$.ajax({
					type : "POST",
					url : "../advancedSearchResults",
					data : {
						search: searchKey,
						user_id : '${user.id}'
					}}).done(function(answer) {
						if ((answer == "") || (answer == "[]")) {
							$("#results").append("<p>Nothing to show :(</p>");
							document.body.className = "loaded";
						} else {
							var resultsRec = JSON.parse(answer);
							for (i = 0; i < resultsRec.length; i++) {
								$("#results").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + resultsRec[i].id +"'>" + resultsRec[i].name + "</p></li>");
								document.body.className = "loaded";
							}	
						}
					}).fail(function (){
						alert("Ajax call was unsuccessfull :(");
						document.body.className = "loaded";
					});
			}
		});

	
	   // to add new input text
		var counter = 1;
		function addInput(divName) {
			var newdiv = document.createElement('div');
			newdiv.innerHTML = "<p> <input type='text' class='form-control' id='ingredient' name='mustHaveIngredients[]' placeholder='sugar,etc'> </p>";
			document.getElementById(divName).appendChild(newdiv);
			counter++;
		}

		/* to enable search button
		$("#searchKey").keyup(function(event) {
			if ($("#searchKey").val().length != 0) {
				$('#search').attr("disabled", false);
			} else {
				$('#search').attr("disabled", true);
			}
		});
		*/
		
		// 
		var searchFilter;
		$(".filter").click(function() {
			$(this).addClass("active").siblings().removeClass("active");
			searchFilter = this.id;
			$("#results").append(searchFilter);
		});

		var advSearchResult;
		$('#AdvSearch').submit(
			function(event) {
				event.preventDefault();
				$("#results").empty();
				document.body.className = "loading";
				var nodeList = document.getElementsByName("mustHaveIngredients[]");
				var nodeArray = [];
				for (var i = 0; i < nodeList.length; i++){
				    nodeArray[i] = nodeList[i].value;
				}
				$.ajax({
					type : "POST",
					url : "advancedSearchResults",
					data : {
						search: $("#searchKey").val(),
						calorieIntervalLow : $("#caloriesMin").val(),
						calorieIntervalHigh : $("#caloriesMax").val(),
						mustHaveIngredientz : JSON.stringify(nodeArray),
						enableFoodSelection : $("#foodPreferences").prop('checked') ? 'true' : 'false',
						enableEaten : $("#eatenRecipes").prop('checked') ? 'true' : 'false',
						disableSemantic : $("#JustMyTags").prop('checked') ? 'true' : 'false',
						user_id : '${user.id}'
					}}).done(function(answer) {
						if ((answer == "") || (answer == "[]") || (answer == null)) {
							$("#tasteContent").append("<p>Nothing to show :(</p>");
							$("#healthContent").append("<p>Nothing to show :(</p>");
							$("#costContent").append("<p>Nothing to show :(</p>");
							$("#easeContent").append("<p>Nothing to show :(</p>");
							$("#overallContent").append("<p>Nothing to show :(</p>");
							$("#likeContent").append("<p>Nothing to show :(</p>");
							document.body.className = "loaded";
						} else {
							advSearchResult = answer;
							
							$("#likeContent").css('display', 'none');
							$("#tasteContent").css('display', 'none');
							$("#healthContent").css('display', 'none');
							$("#costContent").css('display', 'none');
							$("#easeContent").css('display', 'none');
							$("#overallContent").css('display', 'block');
							
							$.ajax({
								type:"POST",
								url:"sortBy",
								data:{
									results:advSearchResult,
									type:"overall"
								}
							}).done(function(answer){
								var results = JSON.parse(answer);
								$("#overallContent").empty();
								for (i = 0; i < results.length; i++) {
									$("#overallContent").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + results[i].recipe_id +"'>" + results[i].name + "</p></li>");
									document.body.className = "loaded";
								}			
							}).fail(function(){
								alert("Ajax call was unsuccessfull :(");	
								document.body.className = "loaded";
								
							});
// 							var resultsRec = JSON.parse(answer);
// 							for (i = 0; i < resultsRec.length; i++) {
// 								$("#results").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + resultsRec[i].id +"'>" + resultsRec[i].name + "</p></li>");
// 								document.body.className = "loaded";
// 							}			
						}
					}).fail(function (){
						alert("Ajax call was unsuccessfull :(");	
						document.body.className = "loaded";
					});
			});
							
			/*
			* switch filter between sort types
			*/
			$(".filter")
					.click(
							function() {
								$(this).addClass("active").siblings().removeClass(
										"active");
								searchFilter = this.id;
							
							if (searchFilter == "overall") {
								$("#likeContent").css('display', 'none');
								$("#tasteContent").css('display', 'none');
								$("#healthContent").css('display', 'none');
								$("#costContent").css('display', 'none');
								$("#easeContent").css('display', 'none');
								$("#overallContent").css('display', 'block');
								
								$.ajax({
									type:"POST",
									url:"sortBy",
									data:{
										results:advSearchResult,
										type:searchFilter
									}
								}).done(function(answer){
									var results = JSON.parse(answer);
									$("#overallContent").empty();
									for (i = 0; i < results.length; i++) {
										$("#overallContent").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + results[i].recipe_id +"'>" + results[i].name + "</p></li>");
										document.body.className = "loaded";
									}			
								}).fail(function(){
									alert("Ajax call was unsuccessfull :(");	
									document.body.className = "loaded";
									
								});
							}
							else if(searchFilter == "likes") {
								$("#tasteContent").css('display', 'none');
								$("#healthContent").css('display', 'none');
								$("#costContent").css('display', 'none');
								$("#easeContent").css('display', 'none');
								$("#overallContent").css('display', 'none');
								$("#likeContent").css('display', 'block');
								
								$.ajax({
									type:"POST",
									url:"sortBy",
									data:{
										results:advSearchResult,
										type:searchFilter
									}
								}).done(function(answer){
									var results = JSON.parse(answer);
									$("#likeContent").empty();
									for (i = 0; i < results.length; i++) {
										$("#likeContent").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + results[i].recipe_id +"'>" + results[i].name + "</p></li>");
										document.body.className = "loaded";
									}			
								}).fail(function(){
									alert("Ajax call was unsuccessfull :(");	
									document.body.className = "loaded";
									
								});
							}
							else if(searchFilter == "taste") {
								$("#healthContent").css('display', 'none');
								$("#costContent").css('display', 'none');
								$("#easeContent").css('display', 'none');
								$("#overallContent").css('display', 'none');
								$("#likeContent").css('display', 'none');
								$("#tasteContent").css('display', 'block');
								
								$.ajax({
									type:"POST",
									url:"sortBy",
									data:{
										results:advSearchResult,
										type:searchFilter
									}
								}).done(function(answer){
									var results = JSON.parse(answer);
									$("#tasteContent").empty();
									for (i = 0; i < results.length; i++) {
										$("#tasteContent").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + results[i].recipe_id +"'>" + results[i].name + "</p></li>");
										document.body.className = "loaded";
									}			
								}).fail(function(){
									alert("Ajax call was unsuccessfull :(");	
									document.body.className = "loaded";
									
								});
								
							}
							else if(searchFilter == "health") {
								$("#costContent").css('display', 'none');
								$("#easeContent").css('display', 'none');
								$("#overallContent").css('display', 'none');
								$("#likeContent").css('display', 'none');
								$("#tasteContent").css('display', 'none');
								$("#healthContent").css('display', 'block');
								
								$.ajax({
									type:"POST",
									url:"sortBy",
									data:{
										results:advSearchResult,
										type:searchFilter
									}
								}).done(function(answer){
									var results = JSON.parse(answer);
									$("#healthContent").empty();
									for (i = 0; i < results.length; i++) {
										$("#healthContent").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + results[i].recipe_id +"'>" + results[i].name + "</p></li>");
										document.body.className = "loaded";
									}			
								}).fail(function(){
									alert("Ajax call was unsuccessfull :(");	
									document.body.className = "loaded";
									
								});
								
							}
							else if(searchFilter == "cost") {
								$("#easeContent").css('display', 'none');
								$("#overallContent").css('display', 'none');
								$("#likeContent").css('display', 'none');
								$("#tasteContent").css('display', 'none');
								$("#healthContent").css('display', 'none');
								$("#costContent").css('display', 'block');
								
								$.ajax({
									type:"POST",
									url:"sortBy",
									data:{
										results:advSearchResult,
										type:searchFilter
									}
								}).done(function(answer){
									var results = JSON.parse(answer);
									$("#costContent").empty();
									for (i = 0; i < results.length; i++) {
										$("#costContent").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + results[i].recipe_id +"'>" + results[i].name + "</p></li>");
										document.body.className = "loaded";
									}			
								}).fail(function(){
									alert("Ajax call was unsuccessfull :(");	
									document.body.className = "loaded";
									
								});
	
							}
							else if(searchFilter == "ease") {
								$("#overallContent").css('display', 'none');
								$("#likeContent").css('display', 'none');
								$("#tasteContent").css('display', 'none');
								$("#healthContent").css('display', 'none');
								$("#costContent").css('display', 'none');
								$("#easeContent").css('display', 'block');
								
								$.ajax({
									type:"POST",
									url:"sortBy",
									data:{
										results:advSearchResult,
										type:searchFilter
									}
								}).done(function(answer){
									var results = JSON.parse(answer);
									$("#easeContent").empty();
									for (i = 0; i < results.length; i++) {
										$("#easeContent").append( "<li class='list-group-item'><a href= '/nutty/recipe/"  + results[i].recipe_id +"'>" + results[i].name + "</p></li>");
										document.body.className = "loaded";
									}			
								}).fail(function(){
									alert("Ajax call was unsuccessfull :(");	
									document.body.className = "loaded";
									
								});

							}	
						});
	</script>
</body>
</html>
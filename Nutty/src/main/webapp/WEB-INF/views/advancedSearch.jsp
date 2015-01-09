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

<script type="text/javascript">

$(function(){
	$("#likeContent").css('display', 'none');
	$("#tasteContent").css('display', 'none');
	$("#healthContent").css('display', 'none');
	$("#costContent").css('display', 'none');
	$("#easeContent").css('display', 'none');
	$("#overallContent").css('display', 'none');
	$("#similarityContent").css('display', 'none');
});


</script>

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
						<li role="presentation" class="filter active" id="similarity"><a
							class="btn btn-link">Similarity</a></li>
						<li role="presentation" class="filter" id="overall"><a
							class="btn btn-link">Overall</a></li>
						<li role="presentation" class="filter" id="like"><a
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
					<ul id="similarityContent" class="list-group"
						style="overflow: scroll; display: block;">
						<li><br>
							<p>Loading, please wait..</p> <br></li>
					</ul>
					<ul id="overallContent" class="list-group"
						style="overflow: scroll; display: none;">
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
		
	var advSearchResults = null;		// will hold advanced search results according to similarity rating
	var advSearchResultIds = [];		// will hold advanced search recipe ids
	var searchFilter = "similarity";	// will hold the name of the pressed filter
	var searchKey = '${searchKey}';		// will hold a value if this page has opened after clicking a tag
	
	$(document).ready(function(){
		if(searchKey != null && searchKey.trim().length > 0){
			$("#searchKey").val(searchKey);
			document.body.className = "loading";
			$.ajax({
				type : "POST",
				url : "/nutty/advancedSearchResults",
				data : {
					search: searchKey,
					user_id : '${user.id}'
				}}).done(function(answer) {
					if ((answer == "") || (answer == "[]") || (answer == null)) {
						advSearchResults = "";
						$("#similarityContent").empty().append("<p>Nothing to show :(</p>");
						$("#tasteContent").empty().append("<p>Nothing to show :(</p>");
						$("#healthContent").empty().append("<p>Nothing to show :(</p>");
						$("#costContent").empty().append("<p>Nothing to show :(</p>");
						$("#easeContent").empty().append("<p>Nothing to show :(</p>");
						$("#overallContent").empty().append("<p>Nothing to show :(</p>");
						$("#likeContent").empty().append("<p>Nothing to show :(</p>");
					} else {
						advSearchResults = answer;
						var results = JSON.parse(answer);
						$("#similarityContent").empty();
						for (i = 0; i < results.length; i++) {
							advSearchResultIds[i] = results[i].id;
							$("#similarityContent").append( "<a href= '/nutty/recipe/"  + results[i].id +"' class='list-group-item'><img src='"+results[i].photoUrl+"' title='"+results[i].name+"' width='30%' height='auto' hspace='50px'><span style='font-size: 1.2em; width:250px; height:auto; display:inline-block'>" + results[i].name + "</span></a></p>");
						}
					}
					hideContent();
					$("#similarityContent").css('display', 'block');
					document.body.className = "loaded";
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
	
	$('#AdvSearch').submit(
		function(event) {
			event.preventDefault();
			document.body.className = "loading";
			
			// Reset to initial state if a search was done before
			if(advSearchResults != null){
				advSearchResults = null;
				advSearchResultIds = [];
				resetContent();
				$("#similarity").addClass("active").siblings().removeClass("active");
				searchFilter = "similarity";
			}
			
			var nodeList = document.getElementsByName("mustHaveIngredients[]");
			var nodeArray = [];
			for (var i = 0; i < nodeList.length; i++){
			    nodeArray[i] = nodeList[i].value;
			}
			$.ajax({
				type : "POST",
				url : "/nutty/advancedSearchResults",
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
						advSearchResults = "";
						$("#similarityContent").empty().append("<p>Nothing to show :(</p>");
						$("#tasteContent").empty().append("<p>Nothing to show :(</p>");
						$("#healthContent").empty().append("<p>Nothing to show :(</p>");
						$("#costContent").empty().append("<p>Nothing to show :(</p>");
						$("#easeContent").empty().append("<p>Nothing to show :(</p>");
						$("#overallContent").empty().append("<p>Nothing to show :(</p>");
						$("#likeContent").empty().append("<p>Nothing to show :(</p>");
					} else {
						advSearchResults = answer;
						var results = JSON.parse(answer);
						$("#similarityContent").empty();
						for (i = 0; i < results.length; i++) {
							advSearchResultIds[i] = results[i].id;
							$("#similarityContent").append( "<a href= '/nutty/recipe/"  + results[i].id +"' class='list-group-item'><img src='"+results[i].photoUrl+"' title='"+results[i].name+"' width='30%' height='auto' hspace='50px'><span style='font-size: 1.2em; width:250px; height:auto; display:inline-block'>" + results[i].name + "</span></a></p>");
						}
					}
					hideContent();
					$("#similarityContent").css('display', 'block');
					document.body.className = "loaded";
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
						document.body.className = "loading";

						$(this).addClass("active").siblings().removeClass(
								"active");
						searchFilter = this.id;
					
						if(advSearchResults != null){
							if(searchFilter == "similarity"){
								hideContent();
								$("#similarityContent").css('display', 'block');
							}					
							else {
								var contentHolder = "#" + searchFilter + "Content";
								// Only make the ajax call if a new search was done 
								if($(contentHolder)[0].innerText.trim() == "Loading, please wait.."){
									$.ajax({
										type:"POST",
										url:"/nutty/sortBy",
										data:{
											results:JSON.stringify(advSearchResultIds),
											type:searchFilter
										}
									}).done(function(answer){
										var results = JSON.parse(answer);
										$(contentHolder).empty();
										for (i = 0; i < results.length; i++) {
											$(contentHolder).append( "<a href= '/nutty/recipe/"  + results[i].id +"' class='list-group-item'><img src='"+results[i].photoUrl+"' title='"+results[i].name+"' width='30%' height='auto' hspace='50px'><span style='font-size: 1.2em; width:250px; height:auto; display:inline-block'>" + results[i].name + "</span></a></p>");
										}			
									}).fail(function(){
										alert("Ajax call was unsuccessfull :(");				
									});	
								}	
								hideContent();
								$(contentHolder).css('display', 'block');
							}
						}
						document.body.className = "loaded";
				});
							
	function hideContent(){
		$("#likeContent").css('display', 'none');
		$("#tasteContent").css('display', 'none');
		$("#healthContent").css('display', 'none');
		$("#costContent").css('display', 'none');
		$("#easeContent").css('display', 'none');
		$("#overallContent").css('display', 'none');
		$("#similarityContent").css('display', 'none');
	}
	
	function resetContent(){
		$("#likeContent").empty().append("<li><br><p>Loading, please wait..</p> <br></li>");
		$("#tasteContent").empty().append("<li><br><p>Loading, please wait..</p> <br></li>");
		$("#healthContent").empty().append("<li><br><p>Loading, please wait..</p> <br></li>");
		$("#costContent").empty().append("<li><br><p>Loading, please wait..</p> <br></li>");
		$("#easeContent").empty().append("<li><br><p>Loading, please wait..</p> <br></li>");
		$("#overallContent").empty().append("<li><br><p>Loading, please wait..</p> <br></li>");
		$("#similarityContent").empty().append("<li><br><p>Loading, please wait..</p> <br></li>");
	}
	</script>
</body>
</html>
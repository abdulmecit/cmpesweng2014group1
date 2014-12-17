<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style type="text/css">
@import url(http://fonts.googleapis.com/css?family=Varela+Round);
</style>
<body>

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
								<label> <input type="checkbox" id="foodPreferences"
									name="enableFoodSelection" value="true"> <b>Consider my food preferences</b>
								</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4"></div>
						<div class="col-sm-7">
							<div class="checkbox">
								<label> <input type="checkbox" id="eatenRecipes" name="enableEaten"
									value="true"> <b>Don't show eaten recipes</b>
								</label>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4"></div>
						<div class="col-sm-7">
							<div class="checkbox">
								<label> <input type="checkbox" id="JustMyTags" name="disableSemantic"
									value="true"> <b>Just use my tags</b>
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
						<li role="presentation" class="filter" id="like"><a
							class="btn btn-link">Like</a></li>
						<li role="presentation" class="filter" id="taste"><a
							class="btn btn-link">Taste Rate</a></li>
						<li role="presentation" class="filter" id="healht"><a
							class="btn btn-link">Health Rate </a></li>
						<li role="presentation" class="filter" id="cost"><a
							class="btn btn-link">Cost Rate</a></li>
						<li role="presentation" class="filter" id="ease"><a
							class="btn btn-link">Ease Rate</a></li>
					</ul>
				
					<ul id="results" class="list-group" style="overflow:scroll;">
							
			
				    </ul>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
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
			})
			$("#results").append(searchFilter);
		});

		$('#AdvSearch')
		.submit(
				function(event) {
					event.preventDefault();
					$.ajax({
						type : "POST",
						url : "../advancedSearchResults",
						data : {
							search: search,
							calorieIntervalLow : calorieIntervalLow,
							calorieIntervalHigh : calorieIntervalHigh,
							mustHaveIngredients : mustHaveIngredients,
							enableFoodSelection : enableFoodSelection,
							enableEaten : enableEaten,
							disableSemantic : disableSemantic,
							user_id : '${user.id}',
						}}).done(function(answer) {
									//$("#searchResults").html("");
									if (answer == "") {
										$("#results")
												.append(
														"<p>Nothing to show :(</p>");
									} else {
										var results = answer
												.split('|');
										var path = results[0];
										for (i = 1; i < results.length; i++) {
											dummy = results[i]
													.split('>');
											$("#results")
													.append("<a href='../recipe/" + ${recipe.recipe_id} + "' class='list-group-item'>"
															 +" <h5 class='list-group-item-heading'> RecipeName </h5></a>");
										}
									}
								});
			});
		
	</script>
</body>
</html>
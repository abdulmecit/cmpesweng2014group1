<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

</head>
<body>

	<!-- ------------------- Recipe Report Table ------------------ -->
	<div class="panel panel-default" style="margin: 120px 75px 75px 75px;">
		<div class="panel-heading">Recipe Reports</div>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th class="col-sm-1" style="text-align: center">Number
						OfReports</th>
					<th class="col-sm-7" style="text-align: center">Contents</th>
					<th class="col-sm-1" style="text-align: center">Delete Content</th>
					<th class="col-sm-1" style="text-align: center">Delete Reports</th>
					<th class="col-sm-1" style="text-align: center">Delete User</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="repRecipe" items="${reportedRecipes}"
					varStatus="counter">
					<tr>
						<td style="text-align: center">${recipeReportNumbers[counter.index]}</td>

						<td style="text-align: center"><a
							href="/nutty/recipe/${repRecipe.recipe_id}">
								${repRecipe.name}</a></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='deleteRecipe(${repRecipe.recipe_id})'>
								<span class="ui-button-text">Recipe</span>
							</button></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='cancelReport(${repRecipe.recipe_id})'>
								<span class="ui-button-text">Reports</span>
							</button></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary" onclick='deleteAccount(${ownerId})'>
								<span class="ui-button-text">User</span>
							</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- ------------------- Comment Report Table ------------------ -->
	<div class="panel panel-default" style="margin: 75px 75px 75px 75px;">
		<div class="panel-heading">Comment Reports</div>
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th class="col-sm-1" style="text-align: center">Number Of
						Reports</th>
					<th class="col-sm-8" style="text-align: center">Contents</th>
					<th class="col-sm-1" style="text-align: center">Delete Content</th>
					<th class="col-sm-1" style="text-align: center">Delete Reports</th>
					<th class="col-sm-1" style="text-align: center">Delete User</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="repComments" items="${reportedComments}"
					varStatus="counter">
					<tr>

						<td style="text-align: center">${commentReportNumbers[counter.index]}</td>

						<td style="text-align: center"><a
							href="/nutty/recipe/${repComments.recipe_id}">
								${repComments.text}</a></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='deleteComment(${repComments.comment_id})'>
								<span class="ui-button-text">Comment</span>
							</button></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='cancelReport(${repComments.recipe_id})'>
								<span class="ui-button-text">Reports</span>
							</button></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary" onclick='cancelReport(${ownerId})'>
								<span class="ui-button-text">User</span>
							</button></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script type="text/javascript">
	
	
	/*
	* Delete Recipe
	*/
	function deleteRecipe(RecipeID){
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "/nutty/deleteRecipe",
					data : {
						recipe_id : RecipeID,
					}
				}).done(function(e) {
				})
			} else {
			}
		});
		
		location.reload();
	}
	
	/*
	* Delete Comment
	*/
	function deleteComment(commentID){
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "/nutty/deleteComment",
					data : {
						comment_id : commentID,
					}
				}).done(function(e) {
				})
			} else {
			}
		});
		
		location.reload();
		
	}
	
	/*
	* Delete Reports
	*/
	function cancelReport(RecipeID){
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "/nutty/deleteReports",
					data : {
						recipe_id : RecipeID,
					}
				}).done(function(e) {
				})
			} else {
			}
		});
	
		location.reload();
	
	
	}

	/*
	* Delete Account (missing userId)
	*/
		
	function deleteAccount(userID){
		/* bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "/nutty/deleteAccount",
					data : {
						user_id : '${user.id}',
					}
				}).done(function(e) {
				})
			} else {
			}
		})	*/
		location.reload();
	}

	</script>
</body>
</html>
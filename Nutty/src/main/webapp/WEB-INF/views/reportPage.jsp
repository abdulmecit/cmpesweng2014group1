<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style type="text/css">

.processing #processingScreen {
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 100;
    width: 100vw;
    height: 100vh;	
    background-color: rgba(192, 192, 192, 0.5);
    background-image: url("../resources/img/processing.gif");
    background-repeat: no-repeat;
    background-position: center;
}
.processed #processingScreen {
	display: none;
}
</style>

</head>
<body>
	<div id=processingScreen></div>

	<!--------------------- Recipe Report Table -------------------->
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
				</tr>
			</thead>
			<tbody>
				<c:forEach var="reportRecipe" items="${reportedRecipes}"
					varStatus="counter">
					<tr>
						<td style="text-align: center">${recipeReportNumbers[counter.index]}</td>

						<td style="text-align: center"><a
							href="/nutty/recipe/${reportRecipe.recipe_id}">
								${reportRecipe.name}</a></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='deleteRecipe(${reportRecipe.recipe_id})'>
								<span class="ui-button-text">Recipe</span>
							</button></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='cancelRecipeReport(${reportRecipe.recipe_id})'>
								<span class="ui-button-text">Reports</span>
							</button></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!--------------------- Comment Report Table -------------------->
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
				</tr>
			</thead>
			<tbody>
				<c:forEach var="reportComments" items="${reportedComments}"
					varStatus="counter">
					<tr>

						<td style="text-align: center">${commentReportNumbers[counter.index]}</td>

						<td style="text-align: center"><a
							href="/nutty/recipe/${reportComments.recipe_id}">
								${reportComments.text}</a></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='deleteComment(${reportComments.comment_id})'>
								<span class="ui-button-text">Comment</span>
							</button></td>

						<td style="text-align: center"><button type="button"
								class="btn btn-primary"
								onclick='cancelCommentReport(${reportComments.comment_id})'>
								<span class="ui-button-text">Reports</span>
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
	function deleteRecipe(recipeID){
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				document.body.className = "processing";
				$.ajax({
					type : "POST",
					url : "/nutty/deleteRecipe",
					data : {
						recipe_id : recipeID,
					}
				}).done(function(e) {
					document.body.className = "processed";
					location.reload();
				})
			} else {
			}
		});
	}
	
	/*
	* Delete Comment
	*/
	function deleteComment(commentID){
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				document.body.className = "processing";
				$.ajax({
					type : "POST",
					url : "/nutty/deleteComment",
					data : {
						comment_id : commentID,
					}
				}).done(function(e) {
					document.body.className = "processed";
					location.reload();
				})
			} else {
			}
		});
	}
	
	/*
	* Delete Recipe Reports
	*/
	function cancelRecipeReport(recipeID){
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				document.body.className = "processing";
				$.ajax({
					type : "POST",
					url : "/nutty/cancelRecipeReports",
					data : {
						recipe_id : recipeID,
					}
				}).done(function(e) {
					document.body.className = "processed";
					location.reload();
				})
			} else {
			}
		});
	}
	
	/*
	* Delete Comment Reports
	*/
	function cancelCommentReport(commentID){
		bootbox.confirm("Are you sure?", function(result) {
			if (result) {
				document.body.className = "processing";
				$.ajax({
					type : "POST",
					url : "/nutty/cancelCommentReports",
					data : {
						comment_id : commentID,
					}
				}).done(function(e) {
					document.body.className = "processed";
					location.reload();
				})
			} else {
			}
		});
	}
	</script>
</body>
</html>
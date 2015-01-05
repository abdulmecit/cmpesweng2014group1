<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

</head>
<body>
	<div class="container">

		<div class="row">
			<div class="col-md-offset-3 col-md-6 well" align="center">
				<h1>Privacy Settings</h1>
				<form name="privacyForm" method="post">
					<div>		
						<br>
						<h3>Being Followable </h3>
						<div class="form-group" align="center">
							<input type="radio" id="follow0" name="followable" value="0" 
							${privOptions.followable=='0'?'checked':''}>With Request 
							&nbsp;
							<input type="radio" id="follow1" name="followable" value="1" 
							${privOptions.followable=='1'?'checked':''}>Without Request
						</div>
						<br>	
						<h4>Health Conditions</h4>
						<div class="form-group" align="center">
							<input type="radio" id="health0" name="visible_hc" value="0" 
							${privOptions.visible_health_condition=='0'?'checked':''}>Hidden 
							&nbsp;
							<input	type="radio" id="health1" name="visible_hc" value="1" 
							${privOptions.visible_health_condition=='1'?'checked':''}>Visible
						</div>
						<br>
						<h4>Food Intolerances</h4>
						<div class="form-group" align="center">
							<input type="radio" id="intolerance0" name="visible_fi" value="0" 
							${privOptions.visible_food_intolerance=='0'?'checked':''}>Hidden
							&nbsp;
							<input type="radio" id="intolerance1" name="visible_fi" value="1" 
							${privOptions.visible_food_intolerance=='1'?'checked':''}>Visible
						</div>
						<br>
						<h4>Not Preferred Ingredients</h4>
						<div class="form-group" align="center">
							<input type="radio" id="notPreferred0" name="visible_np" value="0" 
							${privOptions.visible_not_pref=='0'?'checked':''}>Hidden
							&nbsp;
							<input type="radio" id="notPreferred1" name="visible_np" value="1" 
							${privOptions.visible_not_pref=='1'?'checked':''}>Visible
						</div>
						<input type="hidden" id="from_page" name="from_page" value="${from_page}">	  			
						<button type="submit" class="btn btn-primary" style="float: right">Apply</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
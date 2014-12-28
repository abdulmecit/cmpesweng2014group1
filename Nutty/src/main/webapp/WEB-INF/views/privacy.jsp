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
							<input type="radio" id="follow0" value="0">With Request <input
								type="radio" id="follow1" value="1">Without Request
						</div>
						<br>	
						<h4>Health Conditions</h4>
						<div class="form-group" align="center">
							<input type="radio" id="health0" value="0">Hidden <input
								type="radio" id="health1" value="1">Visible
						</div>
						<br>
						<h4>Food Intolerances</h4>
						<div class="form-group" align="center">
							<input type="radio" id="intolerance0" value="0">Hidden <input
								type="radio" id="intolerance1" value="1">Visible
						</div>
						<br>
						<h4>Not Prefered Ingredients</h4>
						<div class="form-group" align="center">
							<input type="radio" id="notPrefered0" value="0">Hidden <input
								type="radio" id="notPrefered1" value="1">Visible
						</div>
						<button type="submit" class="btn btn-primary" style="float: right">Apply</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
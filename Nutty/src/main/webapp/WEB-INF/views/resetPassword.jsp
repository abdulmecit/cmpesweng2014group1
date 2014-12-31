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
				<h1>Change Password</h1>
				<div>
					<form:form class="form-horizontal" action="resetPass"
						method="POST" modelAttribute="user">
						<div class="formChangePassword">
							<div class="form-group" align="center">
								<label for="inputPassword" class="col-sm-4 control-label">New Password:</label>
								<input type="password" class="form-control" id="inputPassword"
									name="inputPassword" style="width: 50%" maxlength="30">
								<label for="inputPassword" class="col-sm-4 control-label">Confirm Password:</label>
								<input type="password" class="form-control" id="inputPassword2"
									name="inputPassword2" style="width: 50%" maxlength="30"> 
								<input type="hidden" name="user_id" id="user_id" value="${user.id}">
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-primary">Apply</button>
								</div>
							</div>
						</div>
					</form:form>
					<br> 
					<font color="blue">${message.message}</font>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	
	</script>
	
</body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true"/>
    <title>Nutty</title>
    
  </head>
       
   <div class="container">
      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
			<p>${message.message}</p>
			<br>
			<p>Click <a href=".">here</a> to return to the main page.<p>
			<p>Click <a href="addRecipe">here</a> to add recipe.<p>
		</div>
	 </div>
   </div>
</body>
</html>
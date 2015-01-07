<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true"/>
    <title>Nutty</title>
    
</head>
<body>    
   <div class="container">
      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
         	<h1>Ooops!</h1>
         	<br>
    		<p>Application has encountered an error. Sorry for the inconvenience.</p>
    		<br>
    		<p>Reason: ${errorMessage}</p>
		    <!--
		    Failed URL: ${url}
		    Exception:  ${exception.message}
	        <c:forEach items="${exception.stackTrace}" var="ste">   
	   			 ${ste} 
	    	</c:forEach>
		    -->
		</div>
	 </div>
   </div>
</body>
</html>
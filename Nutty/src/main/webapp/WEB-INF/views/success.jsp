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
			<p>${message.message}</p>
			<br>
			<p>Click <a href=".">here</a> to return to the main page.<p>
			<p id="backLink" style="display:none">Click <a href="${message.data}">here</a> to return where you've left off.<p>
		</div>
	 </div>
   </div>
</body>
<script type="text/javascript">
	var message_data = "${message.data}";
	if(message_data && message_data.indexOf("success") == -1)
		$("#backLink").css('display', 'block');
	 
	$(document).ready(function() {
		$.ajax({
			type: "POST",
			url: "removeSessionAttr",
			data: { 
				attrName : "message"
			}
		});
	});
</script>
</html>



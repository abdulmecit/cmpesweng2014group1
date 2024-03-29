<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Nutty</title>

    <!-- Bootstrap core CSS -->
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	

    <!-- Add custom CSS here -->
    <style>
        body {margin-top: 60px;}
    </style>

  </head>

  <body>
  
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href=".">Nutty</a>
        </div>
		  <ul class="nav navbar-nav navbar-right">
		    <% 	if (session.getAttribute("user_id") == null || Integer.valueOf(session.getAttribute("user_id").toString()) == 0){ %>		  
	    		<li><a href="login.jsp">Login</a></li>
	    		<li><a href="signup.jsp">Sign Up</a></li>
	    	<%} else {%>
	    		<li><a href="ownProfile.jsp">My Profile</a></li>	
	    		<li><a href="logout.jsp">Logout</a></li>
	    	<%}%>	    				    
		  </ul>
        

      </div><!-- /.container -->
    </nav>

    <div class="container">

      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
        
          <form class="form-horizontal" action="" method="POST">
			  <div class="form-group">
			    <label for="inputName" class="col-sm-2 control-label">Enter a name</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="inputName" name="inputName" style="width:80%" maxlength="30">
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-default" id="addButton">Add</button>
			    </div>
			  </div>
			  <br>
		      <font color="blue" id="errorMsg"></font>
			</form>
        </div>
      </div>
      
      <div class="container" id="tableContainer" align="left"> 
      
	      <% Class.forName("com.mysql.jdbc.Driver");
	         Connection con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
	         Statement stmt = con.createStatement(); 
	         String query = "SELECT * FROM database1.deneme";
	         ResultSet rs = stmt.executeQuery(query);
	      %>
		<table class="table table-hover" id="names-table" border=1>
		<% ResultSetMetaData rsmd = rs.getMetaData();
		 int columnCount = rsmd.getColumnCount();
		 int rowCount = 0;
		%>
		<TR class="active">
		<% for (int i = 0; i < columnCount; i++) { %>
		<TH> <% out.println(rsmd.getColumnLabel(i + 1)); %> </TH>
		<% } %>
		</TR>
		<% while (rs.next()) {
			rowCount++; %>
			<TR>
			<% for (int i = 0; i < columnCount; i++) { %>
			 <TD> <% out.println(rs.getString(i + 1)); %> </TD>
			<% } %>
			</TR>
		<% } %>
		</table>
      </div>

    </div><!-- /.container -->

	<script>
	
	$("#addButton").on('click', function (event) {
		event.preventDefault();
		$.ajax({
			  type: "POST",
			  url: "InputNameServlet",
			  data: { name: $( "#inputName" ).val()}
			})
			  .done(function(msg) {
				  document.getElementById('errorMsg').innerHTML = msg;
				  if(msg === "Success!") 
					  refreshTable();
			});
	});
	
	function refreshTable(){
	    $(document).ready(function()
    	    {
    	        $.ajaxSetup(
    	        {
    	            cache: false,
    	            beforeSend: function() {
    	                $('#tableContainer').hide();
    	            },
    	            complete: function() {
    	                $('#tableContainer').show();
    	            },
    	            success: function() {
    	                $('#tableContainer').show();
    	            }
    	        });
    	        $("#tableContainer").html('');
    	        $("#tableContainer").load("index.jsp #tableContainer");
    	    });
	}
	
	</script>

  </body>
</html>
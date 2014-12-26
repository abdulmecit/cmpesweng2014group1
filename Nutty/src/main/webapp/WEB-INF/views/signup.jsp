<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true"/>
    <title>Nutty</title>
    
    
    <style>
#dropArea {
	text-align: center;
	line-height: 50px;
	line-width: 50px;
	margin: auto;
	font-size: 15px;
	display: inline-block;
}

#progress {
	display: none
}

.uploading #dropArea {
	display: none
}

.uploaded #dropArea {
	display: none
}

.uploading #progress {
	display: inline
}

.uploaded #progress {
	display: inline
}
</style>
    
    
</head>
<body>
    <div class="container">

      <div class="row">
        <div class="col-md-offset-3 col-md-6 well">
          <h2>Sign Up</h2>
          <form:form class="form-horizontal" method="POST" modelAttribute="user">
			  <div class="form-group">
			    <label for="inputName" class="col-sm-4 control-label">Name:</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="inputName" name="inputName" style="width:80%" maxlength="30" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputSurname" class="col-sm-4 control-label">Surname:</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" id="inputSurname" name="inputSurname" style="width:80%" maxlength="30" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputEmail" class="col-sm-4 control-label">E-mail:</label>
			    <div class="col-sm-6">
			      <input type="email" class="form-control" id="inputEmail" name="inputEmail" style="width:80%" maxlength="30" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputPassword" class="col-sm-4 control-label">Password:</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="inputPassword1" name="inputPassword1" style="width:80%" maxlength="30" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputPassword" class="col-sm-4 control-label">Confirm Password:</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="inputPassword2" name="inputPassword2" style="width:80%" maxlength="30" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputBirthday" class="col-sm-4 control-label">Birthday:</label>
			    <span><select name="birthday_day" id="day" class="_5dba"><option value="0" selected>Day</option><option value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option><option value="19">19</option><option value="20">20</option><option value="21">21</option><option value="22">22</option><option value="23">23</option><option value="24">24</option><option value="25">25</option><option value="26">26</option><option value="27">27</option><option value="28">28</option><option value="29">29</option><option value="30">30</option><option value="31">31</option></select><select name="birthday_month" id="month" class="_5dba"><option value="0" selected>Month</option><option value="1">Jan</option><option value="2">Feb</option><option value="3">Mar</option><option value="4">Apr</option><option value="5">May</option><option value="6">Jun</option><option value="7">Jul</option><option value="8">Aug</option><option value="9">Sep</option><option value="10">Oct</option><option value="11">Nov</option><option value="12">Dec</option></select><select name="birthday_year" id="year" class="_5dba"><option value="0" selected>Year</option><option value="2014">2014</option><option value="2013">2013</option><option value="2012">2012</option><option value="2011">2011</option><option value="2010">2010</option><option value="2009">2009</option><option value="2008">2008</option><option value="2007">2007</option><option value="2006">2006</option><option value="2005">2005</option><option value="2004">2004</option><option value="2003">2003</option><option value="2002">2002</option><option value="2001">2001</option><option value="2000">2000</option><option value="1999">1999</option><option value="1998">1998</option><option value="1997">1997</option><option value="1996">1996</option><option value="1995">1995</option><option value="1994">1994</option><option value="1993">1993</option><option value="1992">1992</option><option value="1991">1991</option><option value="1990">1990</option><option value="1989">1989</option><option value="1988">1988</option><option value="1987">1987</option><option value="1986">1986</option><option value="1985">1985</option><option value="1984">1984</option><option value="1983">1983</option><option value="1982">1982</option><option value="1981">1981</option><option value="1980">1980</option><option value="1979">1979</option><option value="1978">1978</option><option value="1977">1977</option><option value="1976">1976</option><option value="1975">1975</option><option value="1974">1974</option><option value="1973">1973</option><option value="1972">1972</option><option value="1971">1971</option><option value="1970">1970</option><option value="1969">1969</option><option value="1968">1968</option><option value="1967">1967</option><option value="1966">1966</option><option value="1965">1965</option><option value="1964">1964</option><option value="1963">1963</option><option value="1962">1962</option><option value="1961">1961</option><option value="1960">1960</option><option value="1959">1959</option><option value="1958">1958</option><option value="1957">1957</option><option value="1956">1956</option><option value="1955">1955</option><option value="1954">1954</option><option value="1953">1953</option><option value="1952">1952</option><option value="1951">1951</option><option value="1950">1950</option><option value="1949">1949</option><option value="1948">1948</option><option value="1947">1947</option><option value="1946">1946</option><option value="1945">1945</option><option value="1944">1944</option><option value="1943">1943</option><option value="1942">1942</option><option value="1941">1941</option><option value="1940">1940</option><option value="1939">1939</option><option value="1938">1938</option><option value="1937">1937</option><option value="1936">1936</option><option value="1935">1935</option><option value="1934">1934</option><option value="1933">1933</option><option value="1932">1932</option><option value="1931">1931</option><option value="1930">1930</option><option value="1929">1929</option><option value="1928">1928</option><option value="1927">1927</option><option value="1926">1926</option><option value="1925">1925</option><option value="1924">1924</option><option value="1923">1923</option><option value="1922">1922</option><option value="1921">1921</option><option value="1920">1920</option><option value="1919">1919</option><option value="1918">1918</option><option value="1917">1917</option><option value="1916">1916</option><option value="1915">1915</option><option value="1914">1914</option><option value="1913">1913</option><option value="1912">1912</option><option value="1911">1911</option><option value="1910">1910</option><option value="1909">1909</option><option value="1908">1908</option><option value="1907">1907</option><option value="1906">1906</option><option value="1905">1905</option></select></span>
			  </div>
			  <div class="form-group">
			    <label for="inputGender" class="col-sm-4 control-label">Gender:</label>
			    <div class="col-sm-6">
			      <input type="radio" name="gender" value="0">Male
                  <input type="radio" name="gender" value="1">Female
			    </div>
			  </div>
			  
			  
			  
			  <div class="form-group">
			    <label for="inputPhoto" class="col-sm-4 control-label">Photo:</label>
			    <div  class="panel panel-default">
									<div class="panel-body form-group" align="center"
										style="height: 158px">
										<div id="dropArea">
											Drag and drop your profile picture here! OR
											<br>
											<button type="button"
												onclick="document.querySelector('#elma').click()">Choose
												from your computer</button>
										</div>
										<input id="elma" style="visibility: collapse; width: 0px;"
										type="file" onchange="upload(this.files[0])">
										 
										<input type="hidden" class="form-control" id="link" name="link"></input>
										<p id="progress">Uploading...</p>
									</div>
								</div>
			  </div>
			  
			  
			  
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-primary" id="registerButton" disabled>Sign Up</button>
			    </div>
			  </div>
			  <br>
		      <font color="blue">${message.message}</font>
			</form:form>
        </div>
      </div>
	</div>
</body>
 <script>
      var passwordSame;
      $("#inputPassword2").keyup(function (event) {
    	  if($( "#inputPassword1" ).val() != $( "#inputPassword2" ).val()){
    	  	$("#inputPassword2").css("background-color","pink");
    	  	$('#registerButton').attr("disabled", true);
    	  	passwordSame = false;
    	  }else{
    		  $("#inputPassword2").css("background-color","lightgreen");
    		  passwordSame = true;
    		  if($("#inputName").val().length != 0 && $("#inputSurname").val().length != 0 && $("#inputEmail").val().length != 0 && $("#inputPassword1").val().length != 0){
    		  	$('#registerButton').attr("disabled", false);
    		  }	
    	  }
      });
      
      $("#inputPassword1").keyup(function (event) {
    	  if($( "#inputPassword1" ).val() != $( "#inputPassword2" ).val()){
      	  	$("#inputPassword2").css("background-color","pink");
      	  	$('#registerButton').attr("disabled", true);
      	  	passwordSame = false;
      	  }else{
      		  $("#inputPassword2").css("background-color","lightgreen");
      		  passwordSame = true;
      		  if($("#inputName").val().length != 0 && $("#inputSurname").val().length != 0 && $("#inputEmail").val().length != 0 && $("#inputPassword1").val().length != 0){
      		  	$('#registerButton').attr("disabled", false);
      		  }	
      	  }
      });
      
      $("#inputName").keyup(function (event) {
    	  if(passwordSame && $("#inputName").val().length != 0 && $("#inputSurname").val().length != 0 && $("#inputEmail").val().length != 0 && $("#inputPassword1").val().length != 0){
  		  	$('#registerButton').attr("disabled", false);
  		  }else{
  			$('#registerButton').attr("disabled", true);
  		  }
      });
      
      $("#inputSurname").keyup(function (event) {
    	  if(passwordSame && $("#inputName").val().length != 0 && $("#inputSurname").val().length != 0 && $("#inputEmail").val().length != 0 && $("#inputPassword1").val().length != 0){
  		  	$('#registerButton').attr("disabled", false);
  		  }else{
    			$('#registerButton').attr("disabled", true);
  		  }
      });
      
      $("#inputEmail").keyup(function (event) {
    	  if(passwordSame && $("#inputName").val().length != 0 && $("#inputSurname").val().length != 0 && $("#inputEmail").val().length != 0 && $("#inputPassword1").val().length != 0){
  		  	$('#registerButton').attr("disabled", false);
  		  }else{
    			$('#registerButton').attr("disabled", true);
  		  }
      });
      
      $("#registerButton").on('click', function (event) {
    	if(!passwordSame){
    		alert("Passwords are not the same!");
    	}else{
    		/*
	      	event.preventDefault(); 
	      	$.ajax({
	  			  type: "POST",
	  			  url: "signup",
	  			  data: { 
	  				  name:$( "#inputName" ).val(),
	  				  surname:$( "#inputSurname" ).val(),
	  				  email: $( "#inputEmail" ).val(), 
	  				  password: $( "#inputPassword1" ).val(),
	  				  birthday: $( "#year" ).val() + "-" + $( "#month" ).val() + "-" + $( "#day" ).val(),
	  				  gender: $( 'input:radio[name=sex]:checked' ).val()
	  				  }
	  			})
	  			.done(function(msg) {
	  				document.getElementById('errorMsg').innerHTML = msg;
					if(msg.lastIndexOf("Succ", 0) === 0)	//signup success		
						window.location.assign("signupsuccess.jsp");
	  			});*/
    		}
    	});		
      
      
      
      window.ondragover = function(e) {
			e.preventDefault()
		}
		window.ondrop = function(e) {
			e.preventDefault();
			upload(e.dataTransfer.files[0]);
		}

		function upload(file) {
			if (!file || !file.type.match(/image.*/))
				return;
			document.body.className = "uploading";
			var fd = new FormData();
			fd.append("image", file);
			var xhr = new XMLHttpRequest();
			xhr.open("POST", "https://api.imgur.com/3/image.json");
			xhr.onload = function() {
				document.querySelector("#link").value = JSON
						.parse(xhr.responseText).data.link;
				document.querySelector("#progress").innerHTML = "Done!";
				document.body.className = "uploaded";
			}

			xhr.setRequestHeader('Authorization', 'Client-ID 4aaaa88af99c596');
			xhr.send(fd);
		}
	</script>
</html>
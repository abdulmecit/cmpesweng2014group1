<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.sql.*"%>

<html lang="en">
<jsp:include page="header.jsp" flush="true" />
<title>Nutty</title>

<!-- Add custom CSS here -->
<style>
body {
	margin-top: 80px;
}

.ui-autocomplete-loading {
	background: white url("../resources/img/ui-anim_basic_16x16.gif") right
		center no-repeat;
}

.autocomplete-suggestions {
	border: 1px solid #999;
	background: #FFF;
	overflow: auto;
}

.autocomplete-suggestion {
	padding: 5px 5px;
	white-space: nowrap;
	overflow: hidden;
	font-size: 22px
}

.autocomplete-selected {
	background: #F0F0F0;
}

.autocomplete-suggestions strong {
	font-weight: bold;
	color: #3399FF;
}

.ui-autocomplete {
	max-height: 200px;
	overflow-y: auto;
	overflow-x: hidden;
}

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
<%
	if (session.getAttribute("isLogged") == null
			|| ((Boolean) (session.getAttribute("isLogged")) == false)) {
%>
	<h4 style='text-align: center; margin-bottom: 10px; margin-top: 10px'>
	<em>please login to see this page</em>
	</h4>
		<%
			} else {
		%>

		<div class="container">
			<form method="POST">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<h2 class="panel-title pull-left" style="padding-top: 7.5px;">
							Create New Version of <em><b> ${recipe.name}</b> </em>
						</h2>
						<button type="submit" value="submit" id="submit"
							class="btn btn-primary" style="float: right;" disabled>Create
							New Version</button>
					</div>

					<div class="panel-body">
						<div class="col-sm-12">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h2 class="panel-title pull-left" style="padding-top: 7.5px;">Name,
										Portion, Tags & Photo</h2>
								</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-9">
											<input type="text" class="form-control" id="recipeName"
												name="recipeName" placeholder="New Name of Recipe...">
										</div>
										<div class="col-xs-3">
											<input type="text" class="form-control"
												value=${recipe.portion
										} id="portion"
												name="portion" placeholder="portion">
										</div>
									</div>
									<br>
									<div id="dynamicInput3" class="col-sm-3" align="left">
										<input type="text" class="form-control" id="tag" name="tag[]" value="${tags[0].tag_name}"
											placeholder=" Write Tags of Recipe"> <br> <input
											type="text" class="form-control" id="tag" name="tag[]" value="${tags[1].tag_name}"
											placeholder="italian cuisine..."> <br> <input
											type="text" class="form-control" id="tag" name="tag[]" value="${tags[2].tag_name}"
											placeholder="chicken..."> <br> <input
											type="text" class="form-control" id="tag" name="tag[]" value="${tags[3].tag_name}"
											placeholder="soup...">
									</div>
									<div id="dynamicInput3" class="col-sm-3" align="left">
										<input type="text" class="form-control" id="tag" name="tag[]" value="${tags[4].tag_name}"
											placeholder="soy sauce..."> <br> <input
											type="text" class="form-control" id="tag" name="tag[]" value="${tags[5].tag_name}"
											placeholder=" chilli sauce..."> <br> <input
											type="text" class="form-control" id="tag" name="tag[]" value="${tags[6].tag_name}"
											placeholder="easy..."> <br> <input type="text"
											class="form-control" name="tag[]" id="tag" value="${tags[7].tag_name}"
											placeholder="cheap...">
									</div>
									<div id="dynamicInput3" class="col-sm-6" align="left">
										<div class="panel panel-default">

											<!------------------------  Photo  --------------------------->
											<div class="panel-body" style="height: 200px">

												<div id="dropArea">
													Drag and drop your recipe picture here! OR
													<button type="button"
														onclick="document.querySelector('#elma').click()">Choose
														from your computer</button>
												</div>

												<input id="elma" style="visibility: collapse; width: 0px;"
													type="file" onchange="upload(this.files[0])"> <input
													type="hidden" class="form-control" id="link" name="link" value="${photoUrl}"></input>

												<p id="progress">Uploading...</p>

											</div>
											<!----------------------- Ends of photo --------------------------->
										</div>
									</div>
								</div>
							</div>
						</div>

						<!------------------------  Get Ingredients  --------------------------->
						<div class="col-sm-6">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h4 class="panel-title pull-left"
										style="padding-top: 7.5px; padding-bottom: 7.5px">Search
										Ingredient:</h4>
									<input type="text" class="form-control" id="addIngredient">
								</div>

								<div class="panel-body">
									<div class="panel-heading clearfix">
										<h4 class="panel-title pull-left" style="padding-top: 7.5px;">Amount
											/ Ingredients</h4>
									</div>
									<div id="dynamicInput"></div>
									<div>
										<c:forEach var="ingredientAmount" items="${ingredientAmounts}"
											varStatus="loop">
											<div id="textBoxDiv0${loop.index}">
												<p>
												<div class="col-sm-4" align="left">
													<input type="text" class="form-control" id="amount"
														name="amount[]" value="${ingredientAmount.amount}">
												</div>
												<div class='col-sm-4' align='left'> 
													<select name='measType[]'> 
														<option value='gr' <c:if test ="${ingredientAmount.meas_type == 'gr'}">selected</c:if>>gr</option>
														<c:forEach var="item" items="${measTypesMap[loop.index]}">
															<option value="${item}" <c:if test ="${ingredientAmount.meas_type == item}">selected</c:if>>${item}</option>
														</c:forEach>
													</select>
												</div>
												<br><br>
												<div class="col-sm-8">
													<div class="input-group">
														<input type="text" class="form-control" id="ingredient"
															name="ingredient[]" value="${ingredientAmount.ing_name}"
															readonly> <span class="input-group-btn">
															<button type="button" class="btn btn-default"
																onclick="deleteText('0${loop.index}')"
																id="delingredient">
																<span>&times;</span>
															</button>
														</span>
													</div>
												</div>
												<br> <br>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>

						<!------------------------  Get Directions  --------------------------->

						<div class="col-sm-6">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h4 class="panel-title " style="padding-top: 7.5px;">Directions</h4>
								</div>
								<div class="panel-body">
									<textarea class="form-control" id="description"
										name="description" rows="13">${recipe.description}</textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<%
			}
		%>
		<!---------------------------  Functions  ------------------------------>
		<script type="text/javascript">
			var counter = 1;
			function addInput(div, ing_name, meas_types) {
				var newDiv = document.createElement('div');
				newDiv.id = 'textBoxDiv' + counter;
				var content = "<p><div class='col-sm-4' align='left'> <input type='text' class='form-control' id='amount' name='amount[]' placeholder='only number'> </div>"
				+ "<div class='col-sm-4' align='left'> <select name='measType[]'> <option value='gr' selected>gr</option>";
				for(var i=0; i<meas_types.length; i++){
					content += "<option value='" + meas_types[i] + "'>" + meas_types[i] + "</option>";
				}			
				content += "</select> </div> <br><br>"
				+ "<div class='col-sm-8'> <div class='input-group'> <input type='text' class='form-control' id='ingredient' name='ingredient[]' value='" + ing_name + "'  readonly>"
				+ "<span class='input-group-btn'> <button type='button' class='btn btn-default' onclick='deleteText("+counter+")'"
				+ "id='delingredient"
				+ counter
				+ "'><span id='den'>&times;</span></button> </span></div></div></p><br><br>";
				newDiv.innerHTML = content;
				document.getElementById(div).appendChild(newDiv);
				counter++;
		        formCheck();
			}

			function deleteText(i) {
				$("#textBoxDiv" + i).remove();
		        formCheck();
			}

		    $(document).on("keyup change", "#recipeName, #portion, #description, #amount", function() {
		        formCheck();
		    });
		
			function formCheck(){
				var ingredientz = document.getElementsByName("ingredient[]");
				var amountz = document.getElementsByName("amount[]");
				var ingredients = [];
				var amounts = [];
				if ((ingredientz != null) && (amountz != null)){
					transferValues(ingredientz, ingredients);
					transferValues(amountz, amounts);
				}
				if( ($("#recipeName").val().length != 0)
					&& ($("#portion").val().length != 0)
					&& ($("#description").val().length != 0)
					&& (ingredients.length != 0)
					&& (amounts.length != 0)
					&& (ingredients.length == amounts.length) )
				{
					$('#submit').attr("disabled", false);
				} else {
					$('#submit').attr("disabled", true);
				}
			}	

			function transferValues(src, dest) {
				for (var i = 0; i < src.length; i++){
					var item = src[i].value;
					if(!isBlank(item))
						dest.push(item);
				}
			}
			
			function isBlank(str) {
			    return (!str || /^\s*$/.test(str));
			}

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

				xhr.setRequestHeader('Authorization',
						'Client-ID 4aaaa88af99c596');
				xhr.send(fd);
			}

			$("#addIngredient").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : "../someIngredients2",
						dataType : "json",
						data : {
							filter : request.term
						},
						success : function(data) {
							response($.map(data, function(item) {
								return {
									label : item.ing_name,
									id : item.id,
								};
							}));
						}
					});
				},
				minLength : 3,
				select : function(event, ui) {
					$.ajax({
						type : "POST",
						url : "../measTypesOfIngr",
						data : {
							ing_id : ui.item.id
						}
					}).done(function(meas_types) {
						addInput('dynamicInput', ui.item.label, meas_types);
					});
					//clear text box
					$(this).val("");
					return false;
				}
			});
		</script>
	</body>		
</html>
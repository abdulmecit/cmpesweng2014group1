<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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

</style>
</head>
<body>
    <!--------------- Food Intolerance and Allergies Checkbox ------------------->
	<form name="foodForm" method="post">
		<div class="container">
			<div class="panel panel-default">

				<div class="panel-heading clearfix">
					<h2 class="panel-title pull-left" style="padding-top: 7.5px;">Select
						Your Food Intolerance and Allergies</h2>
				</div>

				<div class="panel-body">
					<div class="row">
						<div class="col-sm-4">
							<p>
								<input type="checkbox" id="lactose intolerance"
									name="FoodSelection[]" value="lactose intolerance" /> Lactose
								Intolerance
							</p>
							<p>
								<input type="checkbox" id="gluten intolerance"
									name="FoodSelection[]" value="gluten intolerance" /> Gluten
								Intolerance
							</p>
							<p>
								<input type="checkbox" id="fruit allergy" name="FoodSelection[]"
									value="fruit allergy" /> Fruit Allergy
							</p>
							<p>
								<input type="checkbox" id="garlic allergy"
									name="FoodSelection[]" value="garlic allergy" /> Garlic
								Allergy
							</p>
							<p>
								<input type="checkbox" id="oat allergy" name="FoodSelection[]"
									value="oat allergy" /> Oat Allergy
							</p>
							<p>
								<input type="checkbox" id="egg allergy" name="FoodSelection[]"
									value="egg allergy" /> Egg Allergy
							</p>
						</div>
						<div class="col-sm-4">
							<p>
								<input type="checkbox" id="meat allergy" name="FoodSelection[]"
									value="meat allergy" /> Meat Allergy
							</p>
							<p>
								<input type="checkbox" id="milk allergy" name="FoodSelection[]"
									value="milk allergy" /> Milk Allergy
							</p>
							<p>
								<input type="checkbox" id="peanut allergy"
									name="FoodSelection[]" value="peanut allergy" /> Peanut
								Allergy
							</p>
							<p>
								<input type="checkbox" id="fish allergy" name="FoodSelection[]"
									value="fish allergy" /> Fish Allergy
							</p>
							<p>
								<input type="checkbox" id="soy allergy" name="FoodSelection[]"
									value="soy allergy" /> Soy Allergy
							</p>
						</div>
						<div class="col-sm-4">
							<p>
								<input type="checkbox" id="tree nut allergy"
									name="FoodSelection[]" value="tree nut allergy" /> Tree Nut
								Allergy
							</p>
							<p>
								<input type="checkbox" id="wheat allergy" name="FoodSelection[]"
									value="wheat allergy" /> Wheat Allergy
							</p>
							<p>
								<input type="checkbox" id="hot peppers allergy"
									name="FoodSelection[]" value="hot peppers allergy" /> Hot
								Peppers Allergy
							</p>
							<p>
								<input type="checkbox" id="sulfites allergy"
									name="FoodSelection[]" value="sulfites allergy" /> Sulfites
								Allergy
							</p>
							<p>
								<input type="checkbox" id="tartrazine allergy"
									name="FoodSelection[]" value="tartrazine allergy" />
								Tartrazine Allergy
							</p>
						</div>
					</div>
				</div>
			</div>

			<!--------------- Diseases Checkbox ------------------->
			<div class="panel panel-default">

				<div class="panel-heading clearfix">
					<h2 class="panel-title pull-left" style="padding-top: 7.5px;">Select
						Your Diseases</h2>
				</div>

				<div class="panel-body">

					<div class="row">
						<div class="col-sm-4">
							<p>
								<input type="checkbox" id="reflux" name="FoodSelection[]"
									value="reflux" /> Reflux
							</p>
							<p>
								<input type="checkbox" id="ulcer" name="FoodSelection[]"
									value="ulcer" /> Ulcer
							</p>
							<p>
								<input type="checkbox" id="gastritis" name="FoodSelection[]"
									value="gastritis" /> Gastritis
							</p>
						</div>
						<div class="col-sm-4">
							<p>
								<input type="checkbox" id="diabetes" name="FoodSelection[]"
									value="diabetes" /> Diabetes
							</p>
							<p>
								<input type="checkbox" id="cholesterol" name="FoodSelection[]"
									value="cholesterol" /> Cholesterol
							</p>
						</div>
						<div class="col-sm-4">
							<p>
								<input type="checkbox" id="heart condition"
									name="FoodSelection[]" value="heart condition" /> Heart
								Condition
							</p>
							<p>
								<input type="checkbox" id="high blood pressure"
									name="FoodSelection[]" value="high blood pressure" /> High
								Blood Pressure
							</p>
						</div>
					</div>
				</div>
			</div>

			<!--------------- Textbox For Other Foods ------------------->
			<div class="panel panel-default">

				<div class="panel-heading clearfix">
					<h2 class="panel-title pull-left" style="padding-top: 7.5px;">
						Other Non-Preferred Ingredients: &nbsp &nbsp &nbsp
						<div style="float: right; width:400px">
							<input type="text" id="addPreference" />
						</div>
					</h2>
				</div>

				<div class="panel-body">

					<div id="unpreferreds"
						style="width: 600px; float: left; overflow: auto;"></div>
					<div></div>
				</div>
				
				<input type="hidden" id="from_page" name="from_page" value="${from_page}">	  			
			</div>
			<input type="submit" value="Submit" style="float: right" />
		</div>
	</form>

	<script>
		var table = document.getElementById("myTable");
		var index = 0;

		$(document).ready(function() {
			$.ajax({
				type : "POST",
				url : "foodSelectionREST",
				data : {
					user_id : '${user.id}'
				}
			}).done(function(res) {
				var len = res.length;
				for (var i = 0; i < len; i++)
					document.getElementById(res[i].fs_name).checked = true;
			});
		});

		$(document).ready(function() {
			$.ajax({
				type : "POST",
				url : "unpreferREST",
				data : {
					user_id : '${user.id}'
				}
			}).done(function(res) {
				var len = res.length;
				for (var i = 0; i < len; i++)
					addRow(res[i]);
			});
		});

		$("#addPreference").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "../someIngredients3",
					dataType : "json",
					data : {
						filter : request.term
					},
					success : function(data) {				
						if(!data.length){
							response([{
								label: 'Sorry! No matches found.', 
								value: "false"
							}]);
						}
						else{
							response($.map(data, function(item) {
								return {
									label : item,
									value : "true"
								};
							}));
						}
					}
				});
			},
			minLength : 3,
			select : function(event, ui) {
				if(ui.item.value == "true")
					addRow(ui.item.label);
				//clear text box
				$(this).val("");
				return false;
			}
		});

		function addRow(item) {

			var row = document.createElement('div');
			row.id = index;
			row.style.height = "25px";
			row.style.width = "600px";
			row.innerHTML = item
					+ "&nbsp;"
					+ "<img id='delete' title='Delete' src='../resources/img/delete.png' height='20' width='20' onclick='deleteRow(this.parentElement.id)'></img>"
					+ "<input type='hidden' id='OtherPreferences' name='OtherPreferences[]' value='" + item + "' style='visibility: collapse; width: 0px;'/>";
			document.getElementById("unpreferreds").appendChild(row);
			index++;
		}

		function deleteRow(id) {
			var row = document.getElementById(id);
			row.parentElement.removeChild(row);
		}
	</script>
</body>
</html>
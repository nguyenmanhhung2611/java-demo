<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">

<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>Salary Calculation</title>

</head>
<body>
	<div class='container'>
		<div class="large-12 columns">
			<div align="center"><h1>Salary Calculation</h1></div>
			<div class="row">* required fields</div>
			<div class="row" id="success" style="color: blue">Employee A has been save to DB successfully</div>
			<div class="row" id="require" style="color: red">You have to input salary.</div>
			<div class="row">id : ${user.id}</div>
			<div class="row">First Name : ${user.firstname}</div>

			<div class="row">Last Name :${user.lastname}</div>
			
				<div class='row'> Employee Type*:
					<div class="col-sm-4">
						<select id="slemployeeType" name="myDropDown1">
	
							<c:if test="${Employeetype.getId() == 0}">
								<option selected="selected" value="0">All</option>
							</c:if>
							<c:if test="${Employeetype.getId() == 1}">
								<option selected="selected" value="1">Normal Employee</option>
							</c:if>
							<c:if test="${Employeetype.getId() == 2}">
								<option selected="selected" value="2">Hourly Employee</option>
							</c:if>
							<c:if test="${Employeetype.getId() == 3}">
								<option selected="selected" value="3">Sale Employee</option>
							</c:if>
	
						</select>
					</div>
				</div>
				
		<div class='row'>
			<form action='calculation' method='post'> 
					
					<div class="large-12 columns" id="normalemployee">
						<div class ="row"> Salary*:
							<input type='text' name='week_salary' id='week_salary' value='' maxlength='4'
							onkeypress='return event.charCode >= 48 && event.charCode <= 57' /></div>
					</div>

					<div class="large-12 columns" id="hourlyemployee" >
						<div class ="row"> Hourly Work*:
							<input type='text' name='hourlyworked' id='hourlyworked' value='' maxlength='2'
							onkeypress='return event.charCode >= 48 && event.charCode <= 57' /></div>
							
						<div class ="row"> Wage Per Hour*:
							<input type='text' name='wageperhour' id='wageperhour' value='' maxlength='2'
							onkeypress='return event.charCode >= 48 && event.charCode <= 57' /></div>
					</div>

					<div class="large-12 columns" id="saleemployee">
						<div class ="row"> Basic Salary*:
							<input type='text' name='basic_salary' id='basic_salary' value='' maxlength='2'
							onkeypress='return event.charCode >= 48 && event.charCode <= 57' /></div>
							
						<div class ="row"> Gross Saled*:
							<input type='text' name='gross_saled' id='gross_saled' value='' maxlength='2'
							onkeypress='return event.charCode >= 48 && event.charCode <= 57' /></div>
							
							
						<div class ="row">Commission Rate*:
							<input type='text' name='commission_rate' id='commission_rate' value=''
							maxlength='2' onkeypress='return event.charCode >= 48 && event.charCode <= 57' />	</div>
						
						
					</div>
					<div class="large-12 columns">
						<div id="comment" class ="row"> Comment*:
							<textarea name='comment' id='comment' col='200' rows='3'></textarea>
						</div>
						
						<div class='row'>
							<input type="submit" name='Save' value='Save'/>
							<input type='button' name='Back' value='Back' onclick="back()" />
						</div>	
					</div>				
				</form>
			</div>
			
		</div>
	</div>



</body>
<script src="myscripts.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	$(function() {

		// declare function of validate screen
		function refresh() {
			var value = $("#slemployeeType").val();
			if (value == '1') {
				$('#hourlyemployee').hide();
				$('#saleemployee').hide();
				$('#normalemployee').show();
			} else if (value == '2') {
				$('#saleemployee').hide();
				$('#normalemployee').hide();
				$('#hourlyemployee').show();

			} else if (value == '3') {

				$('#hourlyemployee').hide();
				$('#normalemployee').hide();
				$('#saleemployee').show();

			}
		}
		// register listener
		$("#slemployeeType").change(function() {
			refresh();

		});

		// hide ok and ng
		$("#success").hide();
		$("#require").hide();

		// call validate screen function when page load
		refresh();

	});

	function save() {
		window.location.href = 'calculation';
	}

	function back() {
		window.location.href = 'homeEmployee';
	}
</script>
</html>
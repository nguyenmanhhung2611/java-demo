<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<div align="center"><h1>List Employee</h1></div>
	<div class ="container">
		<div class="box-body">
			<div class="row">
				<div> User ${user.getLastname()}, <a href="login">logout</a></div>
			</div>
		<table id="bugTable" border="1"
			class="table table-bordered table-striped">
			<thead style="background: #3366FF">
				<tr>
					<th style="width: 5% !important; word-break: break-all;">ID</th>
					<th style="width: 5% !important; word-break: break-all;">EmID</th>
					<th style="width: 25% !important; word-break: break-all;">First	Name</th>
					<th style="width: 30% !important; word-break: break-all;">Last Name</th>
					<th style="width: 20% !important; word-break: break-all;">Salary Calculation</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${listUser}">
					<tr>
						<td style="width: 5% !important; word-break: break-all;">${item.id}</td>
						<td style="width: 15% !important;  word-break: break-all;">${item.getEmployeetypeid()}</td>					
						<td style="width: 30% !important; word-break: break-all;">${item.firstname}</td>	
						<td style="width: 25% !important; word-break: break-all;">${item.lastname}</td>
						<td style="width: 20% !important; word-break: break-all;">
							<a onclick="viewDetail('${item.id}', '${item.getEmployeetypeid()}')">Salary Calculate</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</div>

	<form:form action="homeSalary" method="POST" id="userForm">	  
	  	<input type="hidden" id="id" name="id"><br>
	  	<input type="hidden" id="emID" name="emID"><br>
	</form:form>

<!-- <!-- Bootstrap Js -->
<script>
	$(document).ready(function() {
		$('#bugTable').DataTable();
	});
	
	function viewDetail(id, emID) {
		$("#id").val(id);
		$("#emID").val(emID);
		
		$("#userForm").submit();
	} 

</script>
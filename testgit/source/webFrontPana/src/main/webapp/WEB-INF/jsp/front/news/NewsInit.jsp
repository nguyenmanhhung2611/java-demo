<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>thanh hien</h1>

<%-- <div>${PrefMstList}</div> --%>
<div>=================================</div>
<div>
	<c:forEach var="prefMstInfo" items="${PrefMstList}">
			${prefMstInfo.getPrefName()}"
	</c:forEach>
</div>
<div>=================================</div>

		<table id="bugTable" border="1"
			class="table table-bordered table-striped">
			<thead style="background: #3366FF">
				<tr>
					<th style="width: 35% !important; word-break: break-all;">Name</th>
					<th style="width: 30% !important; word-break: break-all;">Stock Quantity</th>
					<th style="width: 35% !important; word-break: break-all;">Sale Price</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="List" items="${ProductList}">
					<tr>
						<td style="width: 35% !important; word-break: break-all;">${List.getName()}</td>
						<td style="width: 30% !important;  word-break: break-all;">${List.getStockQuantity()}</td>					
						<td style="width: 35% !important; word-break: break-all;">${List.getSalePriceString()}</td>	
					</tr>
				</c:forEach>
			</tbody>
		</table>
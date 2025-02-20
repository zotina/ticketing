<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.java.model.*" %>
<%@ page import="java.util.List" %>
<%
	List<Reservation> reservationList = null;
	String message = null;

	try {
		reservationList = (List<Reservation>) request.getAttribute("reservationList");
			message = (String) request.getAttribute("message");
	} catch (Exception e) {
		// Log the error or handle it appropriately
		e.printStackTrace();
	}
%>
<%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">
	<section class="section profile">
		<% if(message != null) { %>
			<div class="alert alert-info">
				<%= message %>
			</div>
		<% } %>
		<div class="container">
			<div class="row">
				<div class="table-container">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Id_reservation</th>
							<th>Date_reservation</th>
							<th>Prix</th>
							<th>type_siege</th>
							<th>vol</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<% if (reservationList != null) { %>
							<% for (Reservation reservationvalue : reservationList) { %>
								<tr>
								<td><%= reservationvalue.getId_reservation() %></td>
								<td><%= reservationvalue.getDate_reservation() %></td>
								<td><%= reservationvalue.getPrix() %></td>
								<td><%= reservationvalue.getType_siege().getLibelle() %></td>
								<td><%= reservationvalue.getVol().getLibelle() %></td>
								<td> 									
									<a href="anul_reservation?id_reservation=<%= reservationvalue.getId_reservation() %>" class="btn btn-danger btn-sm"
									onclick="return confirm('Êtes-vous sûr de vouloir annuler ce reservation ?')">annuler</a></td>
								</tr>
							<% } %>
						<% } %>
						</tbody>
					</table>
			</div>
		</div>
	</div>
	</section>
</main>
<%@ include file="../../templates/footer.jsp" %>

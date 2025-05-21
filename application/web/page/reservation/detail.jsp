<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="mg.itu.java.model.*" %>
    <%@ page import="mg.itu.java.dto.*" %>
        <%@ page import="java.util.List" %>
            <%
    List<ReservationAvecClasse> panier = null;

    try {
        panier = (List<ReservationAvecClasse> ) request.getAttribute("panier");
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
    <%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">
	<section class="section profile">
		<div class="container">
			<div class="row">
				<div class="table-container">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Type de siège</th>
								<th>Classe</th>
							</tr>
						</thead>
						<tbody>
							<% if (panier != null && panier.size() > 0 ) { %>
								<% for (ReservationAvecClasse reservationvalue : panier) { %>
									<tr>
										<td>
											<%= reservationvalue.getReservation().getType_siege().getLibelle() %>
										</td>
										<td>
											<%
											for(Reservation_classe res : reservationvalue.getReservationClasse()) {
											%>
												<div>
													<%= res.getClasse().getLibelle() %> :
													<%=res.getNombre()%>
												</div>
												<% } %>
										</td>
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

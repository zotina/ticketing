<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.java.model.*" %>
<%@ page import="java.util.*" %>
<%
    List<Vol> volList = (List<Vol>) request.getAttribute("volList");
    List<Type_siege> typeSiegeList = (List<Type_siege>) request.getAttribute("typeSiegeList");
    String message = (String) request.getAttribute("message");
%>
<%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">
    <section class="section profile">
        <div class="container mt-4">
            <% if(message != null) { %>
                <div class="alert alert-info">
                    <%= message %>
                </div>
            <% } %>
            <!-- Flexbox pour afficher les trois formulaires sur une ligne -->
            <div class="d-flex justify-content-between">
                <!-- Formulaire pour Critere_reservation -->
                <form action="critere_reservation_insert" method="post" class="flex-fill me-2">
                    <h3 class="mt-4">Critère de Réservation</h3>
                    <div class="mb-3">
                        <label class="form-label">Heure</label>
                        <input type="number" class="form-control" name="critere_reservation.heur" placeholder="Heure" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Date de changement</label>
                        <input type="date" class="form-control" name="critere_reservation.date_changement" required>
                    </div>

                    <button type="submit" class="btn btn-secondary">
                        Enregistrer Critère de Réservation
                    </button>
                </form>

				<!-- Formulaire pour Promotion -->
                <form action="promotion_insert" method="post" class="flex-fill me-2">
                    <h3 class="mt-4">Promotion</h3>
					<div class="mb-3">
						<label class="form-label">Date promotion</label>
						<input type="date" class="form-control" name="promotion.date_promotion" required>
					</div>
	
					<div class="mb-3">
						<label class="form-label">Vol</label>
						<select class="form-select" name="promotion.vol.id_vol" required>
							<option value="">Sélectionner un vol</option>
							<% for(Vol vol : volList) { %>
								<option value="<%= vol.getId_vol() %>">
									<%= vol.getAvion().getModel() + " - " + vol.getVille().getNom() %>
								</option>
							<% } %>
						</select>
					</div>
	
					<h3 class="mt-4">Types de Sièges</h3>
					<div id="siegesContainer">
						<% for (int i = 0; i < typeSiegeList.size(); i++) { %>
							<% Type_siege type = typeSiegeList.get(i); %>
							<div class="mb-3">
								<label class="form-label"><%= type.getLibelle() %></label>
								<input type="hidden" name="promotion_siege[<%= i %>].type_siege.id_type_siege" 
									   value="<%= type.getId_type_siege() %>">
								<input type="number" class="form-control" name="promotion_siege[<%= i %>].nbr_siege"
									   placeholder="Nombre de sièges" required>
								<br>
								<input type="number" class="form-control" name="promotion_siege[<%= i %>].valeur"
									   placeholder="Promotion" step="0.01" min="0" required>
							</div>
						<% } %>
					</div>
	
					<button type="submit" class="btn btn-primary">
						Enregistrer
					</button>
                </form>

				
                <!-- Formulaire pour Annulation_reservation -->
                <form action="annulation_reservation_insert" method="post" class="flex-fill ms-2">
                    <h3 class="mt-4">Annulation de Réservation</h3>
                    <div class="mb-3">
                        <label class="form-label">Heure</label>
                        <input type="number" class="form-control" name="annulation_reservation.heur" placeholder="Heure" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Date de changement</label>
                        <input type="date" class="form-control" name="annulation_reservation.date_changement" required>
                    </div>

                    <button type="submit" class="btn btn-danger">
                        Enregistrer Annulation
                    </button>
                </form>
            </div>
        </div>
    </section>
</main>

<%@ include file="../../templates/footer.jsp" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.java.model.*" %>
<%@ page import="java.util.*" %>
<%
    Vol vol = (Vol) request.getAttribute("vol");
    List<Ville> villeList = (List<Ville>) request.getAttribute("villeList");
    List<Avion> avionList = (List<Avion>) request.getAttribute("avionList");
    List<Type_siege> typeSiegeList = (List<Type_siege>) request.getAttribute("typeSiegeList");
	List<Vol_siege> volSiegeList = (List<Vol_siege>) request.getAttribute("vol_siege");
    String error = (String) request.getAttribute("error");
%>
<%@ include file="../../templates/header.jsp" %>
<main id="main" class="main">
    <section class="section profile">
		<div class="container mt-4">
			<center>
				<h2><%= (vol != null && vol.getId_vol() != null) ? "Modifier Vol" : "Nouveau Vol" %></h2>
			</center>
			
			<% if(error != null) { %>
				<div class="alert alert-danger">
					<%= error %>
				</div>
			<% } %>

			<form action="<%= (vol != null && vol.getId_vol() != null) ? "vol_store" : "vol_insert" %>" method="post">
				<% if(vol != null && vol.getId_vol() != null) { %>
					<input type="hidden" name="vol.id_vol" value="<%= vol.getId_vol() %>">
				<% } %>

				<div class="mb-3">
					<label class="form-label">Date du Vol</label>
					<input type="datetime-local" class="form-control" name="vol.date_vol" value="<%= vol != null ? vol.getDate_vol() : "" %>" required>
				</div>

				<div class="mb-3">
					<label class="form-label">Ville</label>
					<select class="form-select" name="vol.ville.id_ville" required>
						<option value="">Sélectionner une ville</option>
						<% for(Ville ville : villeList) { %>
							<option value="<%= ville.getId_ville() %>" 
								<%= (vol != null && vol.getVille() != null && 
									ville.getId_ville().equals(vol.getVille().getId_ville())) ? "selected" : "" %>>
								<%= ville.getNom() %>
							</option>
						<% } %>
					</select>
				</div>

				<div class="mb-3">
					<label class="form-label">Avion</label>
					<select class="form-select" name="vol.avion.id_avion" required>
						<option value="">Sélectionner un avion</option>
						<% for(Avion avion : avionList) { %>
							<option value="<%= avion.getId_avion() %>"
								<%= (vol != null && vol.getAvion() != null && 
									avion.getId_avion().equals(vol.getAvion().getId_avion())) ? "selected" : "" %>>
								<%= avion.getModel() %>
							</option>
						<% } %>
					</select>
				</div>

				<h3 class="mt-4">Types de Sièges</h3>
				<div id="siegesContainer">
					<% if (volSiegeList == null) { %>
						<% for (int i = 0; i < typeSiegeList.size(); i++) { %>
							<% Type_siege type = typeSiegeList.get(i); %>
							<div class="mb-3">
								<label class="form-label"><%= type.getLibelle() %></label>
								<input type="hidden" name="vol_siege[<%= i %>].type_siege.id_type_siege" 
										value="<%= type.getId_type_siege() %>">
								<input type="number" class="form-control" name="vol_siege[<%= i %>].prix"
										placeholder="Prix" step="0.01" min="0" required>
							</div>
						<% } %>
					<% } else { %>
						<% for (int i = 0; i < volSiegeList.size(); i++) { %>
							<% Vol_siege siege = volSiegeList.get(i); %>
							<div class="mb-3">
								<label class="form-label"><%= siege.getType_siege().getLibelle() %></label>
								<input type="hidden" name="vol_siege[<%= i %>].type_siege.id_type_siege" 
										value="<%= siege.getType_siege().getId_type_siege() %>">
								<input type="number" class="form-control" name="vol_siege[<%= i %>].prix"
										placeholder="Prix" step="0.01" min="0" value="<%= siege.getPrix() %>">
							</div>
						<% } %>
					<% } %>
				</div>


				<button type="submit" class="btn btn-primary">
					<%= (vol != null && vol.getId_vol() != null) ? "Modifier" : "Enregistrer" %>
				</button>
			</form>
		</div>
	</section>
</main>	

<%@ include file="../../templates/footer.jsp" %>
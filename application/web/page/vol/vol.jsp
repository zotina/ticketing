<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.java.model.*" %>
<%@ page import="java.util.*" %>
<%
    List<Vol> volList = (List<Vol>) request.getAttribute("volList");
    List<Ville> villeList = (List<Ville>) request.getAttribute("villeList");
    List<Avion> avionList = (List<Avion>) request.getAttribute("avionList");
    List<Vol_siege> volSiegeList = (List<Vol_siege>) request.getAttribute("volSiegeList");
%>
<%@ include file="../../templates/header.jsp" %>

<main id="main" class="main">
    <section class="section profile">
        <div class="container">
			<div class="card">
				<div class="card-body">
					<center>
						<h5 class="card-title">Recherche de vols</h5>

						<form action="vol_search" method="get" class="mb-4">
							<div class="mb-3">
								<label class="form-label">Date début</label>
								<input type="datetime-local" class="form-control" name="dateDebut" style="font-size: 16px;">
							</div>

							<div class="mb-3">
								<label class="form-label">Date fin</label>
								<input type="datetime-local" class="form-control" name="dateFin" style="font-size: 16px;">
							</div>

							<div class="mb-3">
								<label class="form-label">Ville</label>
								<select class="form-select" name="id_ville" style="font-size: 16px;">
									<option value="">Tous</option>
									<% for(Ville ville : villeList) { %>
										<option value="<%= ville.getId_ville() %>"><%= ville.getNom() %></option>
									<% } %>
								</select>
							</div>

							<div class="mb-3">
								<label class="form-label">Avion</label>
								<select class="form-select" name="id_avion" style="font-size: 16px;">
									<option value="">Tous</option>
									<% for(Avion avion : avionList) { %>
										<option value="<%= avion.getId_avion() %>"><%= avion.getModel() %></option>
									<% } %>
								</select>
							</div>

							<button type="submit" class="btn btn-primary">
								Search
							</button>
						</form>
					</center>
				</div>
            </div>

			<div class="card">
				<div class="card-body">  
					<div class="table-responsive">
						<table class="table table-bordered table-hover">
							<thead class="table-light">
								<tr>
									<th>ID</th>
									<th>Date</th>
									<th>Ville</th>
									<th>Avion</th>
									<th>Types de Sièges</th>
									<% if ("admin".equalsIgnoreCase((String) request.getSession().getAttribute("auth"))) { %>
									<th>Actions</th>
									<% } %>
								</tr>
							</thead>
							<tbody>
								<% for(Vol vol : volList) { %>
									<tr>
										<td><%= vol.getId_vol() %></td>
										<td><%= vol.getDate_vol() %></td>
										<td><%= vol.getVille().getNom() %></td>
										<td><%= vol.getAvion().getModel() %></td>
										<td>
											<% 
												List<Vol_siege> sieges = volSiegeList.stream()
													.filter(vs -> vs.getVol().getId_vol().equals(vol.getId_vol()))
													.collect(java.util.stream.Collectors.toList());
												for(Vol_siege siege : sieges) {
											%>
												<div>
													<%= siege.getType_siege().getLibelle() %> : <%= siege.getPrix() %> Ar
												</div>
											<% } %>
										</td>
										<% if ("admin".equalsIgnoreCase((String) request.getSession().getAttribute("auth"))) { %>
										<td>
												<a href="vol_update?id_vol=<%= vol.getId_vol() %>" class="btn btn-warning btn-sm">Modifier</a>
												<a href="vol_delete?id_vol=<%= vol.getId_vol() %>" class="btn btn-danger btn-sm" 
												   onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce vol ?')">Supprimer</a>
										</td>
										<% } %>
									</tr>
								<% } %>
							</tbody>
						</table>
					</div>
				</div>
            </div>
        </div>
    </section>
</main>

<%@ include file="../../templates/footer.jsp" %>

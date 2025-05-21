<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="mg.itu.java.model.*" %>
    <%@ page import="mg.itu.java.dto.*" %>
        <%@ page import="java.util.List" %>
            <%
    List<Type_siege> type_siegeList = null;
    List<Vol> volList = null;
    List<Classe> classeList = null;
	String message = null;
    List<ReservationAvecClasse> panier = null;

    try {
        type_siegeList = (List<Type_siege>) request.getAttribute("typeSiegeList");
        volList = (List<Vol>) request.getAttribute("volList");
        classeList = (List<Classe>) request.getAttribute("classeList");
		message = (String) request.getAttribute("message");
        panier = (List<ReservationAvecClasse> ) request.getAttribute("panier");
    } catch (Exception e) {
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
                                    <div class="container d-flex align-items-center justify-content-center" style="width: 80vh;">
                                        <div class="row w-100">
                                            <form action="ajout_panier" method="post">
                                                <div class="form-group">
                                                    <label for="id_type_siege">Type de siège</label>
                                                    <select class="form-control" id="id_type_siege" name="reservation.type_siege.id_type_siege" required>
                                                    <option value="">Sélectionner un type de siège</option>
                                                    <% for (Type_siege type_siege : type_siegeList) { %>
                                                        <option value="<%= type_siege.getId_type_siege() %>"><%= type_siege.getLibelle() %></option>
                                                    <% } %>
                                                </select>
                                                </div>

                                                <h3 class="mt-4">Sièges</h3>
                                                <div id="siegesContainer">
                                                    <% for (int i = 0; i < classeList.size(); i++) { 
                                                    Classe classe = classeList.get(i); %>
                                                        <div class="mb-3">
                                                            <label class="form-label"><%= classe.getLibelle() %></label>
                                                            <input type="hidden" name="reservation_classe[<%= i %>].classe.id_classe" value="<%= classe.getId_classe() %>">
                                                            <input type="number" class="form-control" name="reservation_classe[<%= i %>].nombre" placeholder="Nombre de Place" min="0">
                                                        </div>
                                                        <% } %>
                                                </div>

                                                <button type="submit" class="btn btn-success">Ajouter</button>
                                            </form>

                                            <%-- Affichage du panier --%>
                                                <h3 class="mt-5">Reservation</h3>

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

                                                <form action="reservation_insert" method="post">
                                                    <div class="form-group">
                                                        <label for="id_vol">Vol</label>
                                                        <select class="form-control" id="id_vol" name="reservation.vol.id_vol" required>
                                                        <option value="">Sélectionner un vol</option>
                                                        <% for (Vol vol : volList) { %>
                                                            <option value="<%= vol.getId_vol() %>" %>
                                                                <%= vol.getLibelle() %>
                                                            </option>
                                                        <% } %>
                                                    </select>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="date_reservation">Date de réservation</label>
                                                        <input type="datetime-local" class="form-control" id="date_reservation" name="reservation.date_reservation" required>
                                                    </div>
                                                    <button type="submit" class="btn btn-primary">Confirmer la réservation</button>
                                                </form>
                                        </div>
                                    </div>

                        </section>

                    </main>
                    <%@ include file="../../templates/footer.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String emailError = (String) request.getAttribute("error_email");
String emailValue = (String) request.getAttribute("value_email");

String mdpError = (String) request.getAttribute("error_mdp");
String mdpValue = (String) request.getAttribute("value_mdp");

String nomError = (String) request.getAttribute("error_nom");
String nomValue = (String) request.getAttribute("value_nom");

String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>atelier</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="<%= request.getContextPath() %>/assets/img/favicon.png" rel="icon">
    <link href="<%= request.getContextPath() %>/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="<%= request.getContextPath() %>/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/vendor/simple-datatables/style.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/fonts/fontawesome-all.min.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="<%= request.getContextPath() %>/assets/css/template.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/table.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/assets/css/formulaire.css" rel="stylesheet">

    <!-- =======================================================
    * Template Name: NiceAdmin - v2.5.0
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>

<main id="main" class="main">
    <section class="section profile">
        <div class="container d-flex align-items-center justify-content-center" style="width: 80vh;">
            <div class="row w-100">
                <div class="col-md-8">
                    <h2 class="text-center">Inscription</h2>
                    <form action="process_sign_up" method="post" enctype="multipart/form-data">
                        <% if (error != null) { %>
                            <div class="alert alert-danger">
                                <%= error %>
                            </div>
                        <% } %>

                        <!-- Champ Nom -->
                        <div class="form-group">
                            <label for="nom">Nom :</label>
                            <% if (nomError != null) { %>
                                <div class="error-message">
                                    <%= nomError %>
                                </div>
                            <% } %>
                            <input type="text" class="form-control" name="utilisateur.nom" id="nom" 
                                   value="<%= (nomValue != null) ? nomValue : "" %>">
                        </div>

                        <!-- Champ Email -->
                        <div class="form-group">
                            <label for="email">Email :</label>
                            <% if (emailError != null) { %>
                                <div class="error-message">
                                    <%= emailError %>
                                </div>
                            <% } %>
                            <input type="text" class="form-control" name="utilisateur.email" id="email" 
                                   value="<%= (emailValue != null) ? emailValue : "" %>">
                        </div>

                        <!-- Champ Mot de Passe -->
                        <div class="form-group">
                            <label for="mdp">Mot de passe :</label>
                            <% if (mdpError != null) { %>
                                <div class="error-message">
                                    <%= mdpError %>
                                </div>
                            <% } %>
                            <input type="password" class="form-control" name="utilisateur.mdp" id="mdp" 
                                   value="<%= (mdpValue != null) ? mdpValue : "" %>">
                        </div>
                        <div class="form-group">
                            <label for="passport">Passeport :</label>
                            <input type="file" class="form-control" name="passport" id="passport">
                        </div>
                        <!-- Bouton de Soumission -->
                        <button type="submit" class="btn btn-primary w-100">Inscription</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Vendor JS Files -->
<script src="<%= request.getContextPath() %>/assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendor/chart.js/chart.umd.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendor/echarts/echarts.min.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendor/quill/quill.min.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendor/tinymce/tinymce.min.js"></script>
<script src="<%= request.getContextPath() %>/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="<%= request.getContextPath() %>/assets/js/main1.js"></script>

</body>
</html>

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

</head>
<body>

    <!-- ======= Header ======= -->
    <header id="header" class="header fixed-top d-flex align-items-center">
        <div class="d-flex align-items-center justify-content-between">
            <a href="index.html" class="logo d-flex align-items-center">
                <img src="<%= request.getContextPath() %>/assets/img/logo.png" alt="">
                <span class="d-none d-lg-block"></span>
            </a>
            Ticketing
            <i class="bi bi-list toggle-sidebar-btn"></i>
        </div><!-- End Logo -->

        <div class="ms-auto me-3">
            <a href="logout" class="btn btn-danger btn-sm">Logout</a>
        </div>
    </header><!-- End Header -->

    <!-- ======= Sidebar ======= -->
    <aside id="sidebar" class="sidebar">
        <ul class="sidebar-nav" id="sidebar-nav">
            <li class="nav-heading">Ticketing</li>

            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#client-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Vol</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="client-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="vol_list">
                            <i class="bi bi-circle"></i><span>liste</span>
                        </a>
                    </li>
                    <%
                        String auth = (String) request.getSession().getAttribute("auth");
                        if (auth != null && auth.equalsIgnoreCase("admin")) {
                    %>
                    <li>
                        <a href="vol_form">
                            <i class="bi bi-circle"></i><span>Ajout</span>
                        </a>
                    </li>
                    <% } %>
                </ul>
            </li>

            <%
                if (auth != null && auth.equalsIgnoreCase("admin")) {
            %>
            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#detail-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Detail vol</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="detail-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="detail_form">
                            <i class="bi bi-circle"></i><span>form</span>
                        </a>
                    </li>
                </ul>
            </li>
            <% } %>
            
            <%
                if (auth != null && auth.equalsIgnoreCase("user")) {
            %>
            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#reservation-nav" data-bs-toggle="collapse" href="#">
                    <i class="bi bi-menu-button-wide"></i><span>Reservation</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="reservation-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="reservation_form">
                            <i class="bi bi-circle"></i><span>form</span>
                        </a>
                    </li>
                </ul>
            </li>
            <% } %>
        </ul>
    </aside><!-- End Sidebar -->

</body>
</html>

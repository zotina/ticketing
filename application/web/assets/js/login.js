(function() {
    $(document).ready(function() {
        $(".alert-close").bind("click", () => {
            $(".alert-container").fadeOut();
        });

        $(".signin-btn").bind("click", () => {
            $(".alert-container").fadeIn();
            $(".alert-container").css("display", "flex");

            // Réinitialiser les messages d'erreur et de succès
            document.getElementById('error').textContent = '';
            document.getElementById('success').textContent = '';
        });
    });
})();

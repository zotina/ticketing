document.addEventListener('DOMContentLoaded', function() {
    var chiffreAffaireNonPaye = JSON.parse(document.getElementById('chiffreAffaireNonPaye').textContent);
    var chiffreAffairePaye = JSON.parse(document.getElementById('chiffreAffairePaye').textContent);

    var ctx = document.getElementById('doughnut').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Chiffre d\'affaire payé', 'Chiffre d\'affaire non payé'],
            datasets: [{
                data: [chiffreAffairePaye, chiffreAffaireNonPaye],
                backgroundColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(255, 99, 132, 1)'
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(255, 99, 132, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true
        }
    });
});

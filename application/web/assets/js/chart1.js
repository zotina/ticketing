var ctx = document.getElementById('lineChart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
        datasets: [{
            label: 'Earnings in $',
            data: [2050,3333,6565,7878,9098,1111,2343,9191,2342,1515,2424,9900],
            backgroundColor: [
                'rgba(85, 85, 85, 1)'
            ],
            borderColor: [
                'rgb(41, 155, 99)'
                
            ],
            borderWidth: 1
        }]
    },
    options: {
        responsive:true
    }
});
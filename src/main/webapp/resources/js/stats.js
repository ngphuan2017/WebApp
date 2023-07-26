/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function randomColor() {
    let r = parseInt(Math.random() * 255);
    let g = parseInt(Math.random() * 255);
    let b = parseInt(Math.random() * 255);

    return `rgb(${r}, ${g}, ${b})`;
}

function drawChart(data, label, title, type = "pie", canvasId = "myCateChart") {
    const ctx = document.getElementById(canvasId);

    let colors = [];
    for (let i = 0; i < data.length; i++)
        colors.push(randomColor());

    new Chart(ctx, {
        type: type,
        data: {
            labels: label,
            datasets: [{
                    label: title,
                    data: data,
                    borderWidth: 1,
                    backgroundColor: colors
                }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}


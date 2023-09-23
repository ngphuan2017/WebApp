/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function deleteItemOrder(endpoint, id, number, total) {
    if (confirm("Bạn chắc chắn muốn hủy đơn không?") === true) {
        fetch(endpoint, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => {
            if (res.status === 200) {
                let status = document.getElementById(`order-status${id}`);
                status.textContent = "Đã hủy";
                let button = document.getElementById(`order-button${id}`);
                let cancelButton = document.createElement("button");
                cancelButton.className = "btn btn-outline-secondary";
                cancelButton.disabled = true;
                cancelButton.textContent = "Hủy đơn";

                // Thay thế nút button ban đầu bằng nút button mới
                button.innerHTML = "";
                button.appendChild(cancelButton);
                let totalQuantity = parseInt(document.getElementById("total-quantity").textContent);
                document.getElementById("total-quantity").textContent = totalQuantity - number;
                let totalAmount = parseInt(document.getElementById("total-amount").textContent.replace(/,/g, ''));
                document.getElementById("total-amount").textContent = totalAmount - total;
            }
        });
    }
}
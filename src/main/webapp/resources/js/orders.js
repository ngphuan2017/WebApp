/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function deleteItemOrder(endpoint, id, number, total) {
    Swal.fire({
        title: 'Hủy',
        text: 'Bạn có chắc chắn muốn hủy đơn ?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
    }).then((result) => {
        if (result.isConfirmed) {
            // Hành động khi người dùng xác nhận
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
                    Swal.fire('Hủy thành công!', 'Đơn hàng này đã được hủy!', 'success');
                } else {
                    Swal.fire('Hủy không thành công!', 'Đã xảy ra lỗi, nhưng đừng bực mình - đây không phải là lỗi của bạn!', 'error');
                }
            });
        }
    });
}
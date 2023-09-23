/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
/* global Double */

function addToCart(endpoint, id, name, price, image) {
    fetch(endpoint, {
        method: "POST",
        body: JSON.stringify({
            "id": id,
            "name": name,
            "price": price,
            "quantity": quantityCart,
            "image": image
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json()).then(data => {
        let counters = document.getElementsByClassName("cart-counter");
        for (let d of counters)
            d.innerText = data.totalQuantity;
    });
}

function updateItem(endpoint, obj, id, price) {
    fetch(endpoint, {
        method: "put",
        body: JSON.stringify({
            "quantity": obj.value
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json()).then(data => {
        let counters = document.getElementsByClassName("cart-counter");
        for (let d of counters)
            d.innerText = data.totalQuantity;
        let amounts = document.getElementsByClassName("cart-amount");
        for (let d of amounts)
            d.innerText = parseFloat(data.totalAmount).toLocaleString("en-US");
        let quantity = obj.value;
        let totalElement = document.getElementById(`total${id}`);
        let totalPrice = price * quantity;
        totalElement.innerText = parseFloat(totalPrice).toLocaleString("en-US");
        ;
    });
}

function deleteItem(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        fetch(endpoint, {
            method: "delete"
        }).then(res => res.json()).then(data => {
            let el = document.getElementById(`cart${id}`);
            el.style.display = "none";

            let counters = document.getElementsByClassName("cart-counter");
            for (let d of counters)
                d.innerText = data.totalQuantity;
            let amounts = document.getElementsByClassName("cart-amount");
            for (let d of amounts)
                d.innerText = parseFloat(data.totalAmount).toLocaleString("en-US");
        });
    }
}

function pay(endpoint) {
    Swal.fire({
        title: 'Xác nhận',
        text: 'Bạn có chắc chắn muốn đặt hàng chứ ?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            // Hành động khi người dùng xác nhận
            fetch(endpoint, {
                method: "POST",
                body: JSON.stringify({
                    "optionPay": document.getElementById("option-pay").value
                }),
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(res => {
                if (res.status === 200) {
                    let counters = document.getElementsByClassName("cart-counter");
                    document.querySelector('.table').innerHTML = ``;
                    document.querySelector('.cart-amount').innerHTML = 0;
                    for (let d of counters)
                        d.innerText = 0;
                    Swal.fire('Đặt hàng thành công!', 'Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!', 'success');
                } else {
                    Swal.fire('Đặt hàng không thành công!', 'Hệ thống đang có lỗi, vui lòng quay lại sau!', 'error');
                }
            });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            // Hành động khi người dùng hủy
            Swal.fire('Hủy', 'Bạn đã hủy đặt hàng!');
        }
    });
}

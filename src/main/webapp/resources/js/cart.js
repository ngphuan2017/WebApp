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
    var inputQuantity = document.getElementById(`product-quantity-${id}`);
    var inputQuantityOld = document.getElementById(`product-quantity-${id}-old`);
    if (obj.value < 1) {
        inputQuantity.value = inputQuantityOld.textContent;
        Swal.fire('Lỗi!', 'Vui lòng nhập giá trị không âm!', 'error');
        return;
    } else if (obj.value > 9999) {
        inputQuantity.value = inputQuantityOld.textContent;
        Swal.fire('Lỗi!', 'Vui lòng nhập giá trị nhỏ hơn!', 'error');
        return;
    }
    inputQuantityOld.textContent = obj.value;
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
    Swal.fire({
        title: 'Xóa',
        text: 'Bạn có chắc chắn muốn xóa ?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
// Hành động khi người dùng xác nhận
            fetch(endpoint, {
                method: "delete"
            }).then(res => res.json()).then(data => {
                let el = document.getElementById(`cart${id}`);
                el.style.display = "none";
                let counters = document.getElementsByClassName("cart-counter");
                for (let d of counters)
                    d.innerText = data.totalQuantity;
                let amounts = document.getElementsByClassName("cart-amount");
                for (let d of amounts) {
                    d.innerText = parseFloat(data.totalAmount).toLocaleString("en-US");
                }
                Swal.fire('Xóa thành công!', 'Sản phẩm đã bị xóa khỏi giỏ hàng!', 'success');
            });
        }
    });
}

function pay(endpoint) {
    Swal.fire({
        title: 'Xác nhận',
        text: 'Bạn có chắc chắn muốn đặt hàng chứ ?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Đặt hàng',
        cancelButtonText: 'Hủy'
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
                    Swal.fire('Đặt hàng không thành công!', 'Đã xảy ra lỗi, nhưng đừng bực mình - đây không phải là lỗi của bạn!', 'error');
                }
            });
        }
    });
}

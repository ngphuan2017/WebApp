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
            d.innerText = data.totalCount;
    });
}

function updateItem(endpoint, productUrl, obj, id, price) {
    let dVoucherValue = document.getElementById("d-voucher-discount");
    let totalPrices = document.getElementById("total-price");
    let dTotalPrices = document.getElementById("d-total-price");
    var inputQuantity = document.getElementById(`product-quantity-${id}`);
    var inputQuantityOld = document.getElementById(`product-quantity-${id}-old`);
    if (parseInt(obj.value) < 1) {
        inputQuantity.value = inputQuantityOld.textContent;
        Swal.fire('Lỗi!', 'Vui lòng nhập giá trị dương!', 'error');
    } else {
        fetch(productUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/xml'
            }
        }).then(res =>
            res.text()
        ).then(data => {
            const parser = new DOMParser();
            const xml = parser.parseFromString(data, 'application/xml');
            const json = xmlToJson(xml);
            if (parseInt(obj.value) > parseInt(json.product.quantity)) {
                inputQuantity.value = inputQuantityOld.textContent;
                Swal.fire('Sản phẩm giới hạn số lượng!', 'Vui lòng nhập giá trị nhỏ hơn hoặc bằng: ' + json.product.quantity, 'error');
            } else {
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
                    let quantitys = document.getElementsByClassName("cart-quantity");
                    for (let d of quantitys)
                        d.innerText = data.totalQuantity;
                    let counters = document.getElementsByClassName("cart-counter");
                    for (let d of counters)
                        d.innerText = data.totalCount;
                    let amounts = document.getElementsByClassName("cart-amount");
                    for (let d of amounts)
                        d.innerText = parseFloat(data.totalAmount).toLocaleString("en-US");
                    let quantity = parseInt(obj.value);
                    let totalElement = document.getElementById(`total${id}`);
                    let totalPrice = price * quantity;
                    totalElement.innerText = parseFloat(totalPrice).toLocaleString("en-US");
                    dTotalPrices.textContent = parseFloat(data.totalAmount);
                    let discount = parseInt(dVoucherValue.textContent);
                    if (discount > 0) {
                        totalPrices.textContent = numberWithCommas(parseFloat(data.totalAmount) - discount > 0 ? parseFloat(data.totalAmount) - discount : 0);
                    }
                    ;
                });
            }
        }).catch(error => {
            console.info(error);
        });
    }
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
                let quantitys = document.getElementsByClassName("cart-quantity");
                for (let d of quantitys)
                    d.innerText = data.totalQuantity;
                let counters = document.getElementsByClassName("cart-counter");
                for (let d of counters)
                    d.innerText = data.totalCount;
                let amounts = document.getElementsByClassName("cart-amount");
                for (let d of amounts) {
                    d.innerText = parseFloat(data.totalAmount).toLocaleString("en-US");
                }
                let dTotalPrices = document.getElementById("d-total-price");
                let dVoucherValue = document.getElementById("d-voucher-discount");
                let totalPrices = document.getElementById("total-price");
                dTotalPrices.textContent = parseFloat(data.totalAmount);
                let discount = parseInt(dVoucherValue.textContent);
                    if (discount > 0) {
                        totalPrices.textContent = numberWithCommas(parseFloat(data.totalAmount) - discount > 0 ? parseFloat(data.totalAmount) - discount : 0);
                    }
                    ;
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
            let totalQuantity = document.getElementById("total-quantity").textContent;
            if (totalQuantity !== "0") {
                fetch(endpoint, {
                    method: "POST",
                    body: JSON.stringify({
                        "optionPay": document.getElementById("option-pay").value,
                        "discount": document.getElementById("d-voucher-discount").textContent
                    }),
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(res => {
                    if (res.status === 200) {
                        const locationHeader = res.headers.get('Location');
                        if (locationHeader !== null) {
                            window.location.href = locationHeader;
                        } else {
                            let totalCount = document.getElementById("total-count").textContent;
                            let quantitys = document.getElementsByClassName("cart-quantity");
                            let counters = document.getElementsByClassName("cart-counter");
                            document.querySelector('.table').innerHTML = ``;
                            document.querySelector('.cart-amount').innerHTML = 0;
                            for (let d of counters)
                                d.innerText = 0;
                            for (let d of quantitys)
                                d.innerText = 0;
                            setNotification(1, totalCount);
                            Swal.fire('Đặt hàng thành công!', 'Thông tin đơn hàng của bạn đã được gửi về Email. Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!', 'success');
                        }
                    } else {
                        Swal.fire('Đặt hàng không thành công!', 'Đã xảy ra lỗi, nhưng đừng bực mình - đây không phải là lỗi của bạn!', 'error');
                    }
                });
            } else {
                Swal.fire('Đặt hàng không thành công!', 'Vui lòng thêm ít nhất 1 sản phẩm vào giỏ hàng!', 'warning');
            }
        }
    });
}

function checkVoucherCode(endpoint) {
    fetch(endpoint, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/xml'
        }
    }).then(res =>
        res.json()
    ).then(data => {
        let voucher = document.getElementById("voucher");
        let voucherContent = document.getElementById("voucher-content");
        let voucherValue = document.getElementById("voucher-discount");
        let dVoucherValue = document.getElementById("d-voucher-discount");
        let totalPrice = document.getElementById("total-price");
        let dTotalPrice = document.getElementById("d-total-price");
        voucherContent.classList.remove('d-none');
        voucherContent.classList.add('d-none');
        let flag = 0;
        for (let check of data.promotions) {
            if (check.code !== null && check.code.toLowerCase() === voucher.value.toLowerCase()) {
                voucherContent.textContent = check.note;
                voucherValue.textContent = numberWithCommas(check.discount * 1000);
                dVoucherValue.textContent = check.discount * 1000;
                let total = parseInt(dTotalPrice.textContent) - check.discount * 1000 > 0 ? parseInt(dTotalPrice.textContent) - check.discount * 1000 : 0;
                totalPrice.textContent = numberWithCommas(total);
                flag = 1;
            }
        }
        if (flag !== 1) {
            voucherContent.textContent = "Không tìm thấy Voucher";
            voucherValue.textContent = 0;
            dVoucherValue.textContent = 0;
        }
        voucherContent.classList.remove('d-none');
    });
}

/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */
/* global Double */

const quantityInput = document.getElementById('quantity');
const quantityValue = quantityInput.value;
let quantityCart = quantityValue;
const updateQuantityValue = () => {
    quantityCart = quantityInput.value;
};
const handleQuantityIncrease = () => {
    quantityInput.stepUp();
    updateQuantityValue(); // Cập nhật giá trị quantityValue sau khi thay đổi giá trị trong ô input
};

// Hàm xử lý sự kiện khi ấn nút trừ
const handleQuantityDecrease = () => {
    quantityInput.stepDown();
    updateQuantityValue(); // Cập nhật giá trị quantityValue sau khi thay đổi giá trị trong ô input
};
// Gọi hàm cập nhật giá trị khi có sự kiện 'input'
quantityInput.addEventListener('input', updateQuantityValue);
// Gọi hàm cập nhật giá trị khi ấn nút cộng hoặc nút trừ
document.querySelector('.quantity-right-plus').addEventListener('click', handleQuantityIncrease);
document.querySelector('.quantity-left-minus').addEventListener('click', handleQuantityDecrease);

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
        console.info(data);
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
        totalElement.innerText = parseFloat(totalPrice).toLocaleString("en-US");;
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
    fetch(endpoint, {
        method: "post"
    }).then(res => {
        console.info(res);
        if (res.status === 200) {
            let e = document.getElementById("content");
            e.innerText = "Đơn hàng đã được ghi nhận";

            let counters = document.getElementsByClassName("cart-counter");
            for (let d of counters)
                d.innerText = 0;
        }
    })
}

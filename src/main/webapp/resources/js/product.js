/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function deleteProduct(endpoint, id) {
    Swal.fire({
        title: 'Xóa',
        text: 'Bạn chắc chắn xóa? Nó sẽ xóa luôn các hóa đơn liên quan?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            let s = document.getElementById(`spinner${id}`);
            s.style.display = "block";

            fetch(endpoint, {
                method: "delete"
            }).then(res => {
                s.style.display = "none";
                if (res.status === 204) {
                    document.getElementById(`product${id}`).style.display = "none";
                    Swal.fire('Xóa thành công!', 'Sản phẩm đã bị xóa khỏi giỏ hàng!', 'success');
                } else
                    Swal.fire('Xóa không thành công!', 'Đã xảy ra lỗi, nhưng đừng bực mình - đây không phải là lỗi của bạn!', 'error');
            });
        }
    });
}

let btns = document.querySelectorAll('.js-add-cart');
let cart = document.querySelector('.js-modal');
let modalClose = document.querySelector('.js-modal-close');
let modalContainer = document.querySelector('.js-modal-container');

function showCart() {
    // alert('Modal opened successfully');
    cart.classList.add('js-modal-open');
}
function hideCart() {
    cart.classList.remove('js-modal-open');
}

let modalAccount = document.querySelector('.js-modal-account');
function showAccount() {
    modalAccount.classList.add('js-modal-open');
}
function hideAccount() {
    modalAccount.classList.remove('js-modal-open');
}

function productView(endpointview, endpoint) {
    fetch(endpointview, {
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
        let js = document.getElementById("modal-product-img");
        js.innerHTML = `
            <img src="${json.product.image}" alt="image">
            <i class="d-none" id="product-quantity">${json.product.quantity}</i>
        `;
        let jss = document.getElementById("modal-product-buy");
        jss.innerHTML = `
            <a href="javascript:;" onclick="addToCart('${endpoint}', ${json.product.id}, '${json.product.name}', ${json.product.discount.discount} > 0 ? ${json.product.price} * (1 - ${json.product.discount.discount} / 100) : ${json.product.price}, '${json.product.image}')" id="btn-buy-product">
                <i class="fa-solid fa-cart-plus"></i> Thêm vào giỏ hàng</a>
        `;
        for (const btn of btns) {
            btn.addEventListener('click', showCart);
        }
        modalClose.addEventListener('click', hideCart);
        cart.addEventListener('click', hideCart);
        modalContainer.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }
    ).catch(error => {
        console.info(error);
    });
}

function productDetailView(endpoint) {
    fetch(endpoint, {
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
        let js = document.getElementById("modal-account-img");
        js.innerHTML = `
            <img src="${json.product.image}" alt="avatar">
            <div class="js-currency level">
                <span class="money">${json.product.price}</span>
            </div>
        `;
        let jss = document.getElementById("modal-account-about");
        jss.innerHTML = `
            <span style="margin: 7px 0;">ID: #${json.product.id}</span>
            <span style="margin: 7px 0;">Sản phẩm: <span class="text-info">${json.product.name}</span></span>
            <span style="margin: 7px 0;">Phân loại: ${json.product.categorysubId.name}</span>
            <span style="margin: 7px 0;">Sản phẩm có sẵn: ${json.product.quantity}</span>
            <span style="margin: 7px 0;">Khuyến mãi: <span class="text-danger">${json.product.discount.discount}%</span></span>
            <span style="margin: 7px 0;">Điểm đánh giá: ${json.product.averageRating}</span>
            <span style="margin: 7px 0;">Số lượng đánh giá: ${json.product.reviewCount}</span>
            <span style="margin: 7px 0;">Sản phẩm đã bán: ${json.product.unitsSold}</span>
            <span style="margin: 7px 0 14px;">Trạng thái: <span class="${json.product.productstatus.id === "5" ? `text-customer-active` : json.product.productstatus.id === "6" ? `text-customer-warning` : json.product.productstatus.id === "7" ? `text-customer-danger` : `text-customer-primary`}">${json.product.productstatus.statusname}</span></span>
        `;
        let jsss = document.getElementById("modal-account-title");
        jsss.innerHTML = `
            <i class="fas fa-crown" style="color: yellow;"></i>
            <span class="text-account-title">
                #${json.product.id}
            </span>
            <i class="fas fa-crown" style="color: yellow;"></i>
        `;
        let btns = document.querySelectorAll('.js-add-cart');
        let cart = document.querySelector('.js-modal');
        let modalClose = document.querySelector('.js-modal-close');
        let modalContainer = document.querySelector('.js-modal-container');
        let createDated = document.querySelectorAll(".modal-content .create-date");
        let currencyElement = document.querySelectorAll(".js-currency .money");
        currencyElement.forEach((element) => {
            const amountValue = parseFloat(element.textContent);
            element.textContent = numberWithCommas(amountValue);
        });
        createDated.forEach((element) => {
            const dateValue = moment(element.textContent);
            element.textContent = dateValue.format('DD-MM-YYYY');
        });
        for (const btn of btns) {
            btn.addEventListener('click', showCart);
        }
        modalClose.addEventListener('click', hideCart);
        cart.addEventListener('click', hideCart);
        modalContainer.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }
    ).catch(error => {
        console.info(error);
    });
}

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

function xmlToJson(xml) {
    var obj = {};
    if (xml.nodeType == 1) {
        if (xml.attributes.length > 0) {
            obj["@attributes"] = {};
            for (var j = 0; j < xml.attributes.length; j++) {
                var attribute = xml.attributes.item(j);
                obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
            }
        }
    } else if (xml.nodeType == 3) {
        obj = xml.nodeValue.trim();
    }
    if (xml.hasChildNodes()) {
        for (var i = 0; i < xml.childNodes.length; i++) {
            var item = xml.childNodes.item(i);
            var nodeName = item.nodeName;
            if (nodeName == "#text") {
                obj = item.nodeValue.trim();
                continue;
            }
            if (typeof (obj[nodeName]) == "undefined") {
                obj[nodeName] = xmlToJson(item);
            } else {
                if (typeof (obj[nodeName].push) == "undefined") {
                    var old = obj[nodeName];
                    obj[nodeName] = [];
                    obj[nodeName].push(old);
                }
                obj[nodeName].push(xmlToJson(item));
            }
        }
    }
    return obj;
}
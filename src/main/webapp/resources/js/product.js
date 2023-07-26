/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function deleteProduct(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa? Nó sẽ xóa luôn các hóa đơn liên quan?") === true) {
        let s = document.getElementById(`spinner${id}`);
        s.style.display = "block";

        fetch(endpoint, {
            method: "delete"
        }).then(res => {
            s.style.display = "none";
            console.info(res);
            if (res.status === 204) {
                document.getElementById(`product${id}`).style.display = "none";
            } else
                alert("Hệ thống đang có lỗi, vui lòng quay lại sau!");
        });
    }
}

const btns = document.querySelectorAll('.js-add-cart');
const cart = document.querySelector('.js-modal');
const modalClose = document.querySelector('.js-modal-close');
const modalContainer = document.querySelector('.js-modal-container');
function showCart(){
    // alert('Modal opened successfully');
    cart.classList.add('js-modal-open');
}
function hideCart(){
    cart.classList.remove('js-modal-open');
}

function productView(endpointview) {
    debugger;
    fetch(endpointview, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/xml'
        }
    }).then(res => {
        res.text();
        debugger;
    }
    ).then(data => {
        debugger;
        const parser = new DOMParser();
        const xml = parser.parseFromString(data, 'application/xml');
        const json = xmlToJson(xml);
        console.log(json);
        let js = document.getElementById("modal-body");
        js.innerHTML = `
        <div class="modal-img">
          <img src="${json.product.image}" alt="image">
        </div>
        <div class="modal-quantity">
          <button type="button" class="quantity">-</button>
          <input style="width: 50px; background-color: #fff;" type="number" class="quantity" id="quantity"
            maxlength="12" min="1" size="2" value="1" step="0.01">
          <button type="button" class="quantity">+</button>
        </div>
        <div class="btn-buy-product">
          <a href="javascript:;" onclick="addToCart('/api/cart', ${json.product.id}, '${json.product.name}', ${json.product.price})" id="btn-buy-product"><i class="fa-solid fa-cart-plus"></i> Thêm vào giỏ hàng</a>
        </div>
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
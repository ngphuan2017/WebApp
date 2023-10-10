/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//format price  ////////////////
function numberWithCommas(x) {
    return parseFloat(x).toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",").toString();
}

let currencyElements = document.querySelectorAll(".currency .money");
currencyElements.forEach((element) => {
    const amountValue = parseFloat(element.textContent);
    element.textContent = numberWithCommas(amountValue);
});

//format date
let createDate = document.querySelectorAll(".create-date");
createDate.forEach((element) => {
    const dateValue = moment(element.textContent);
    element.textContent = dateValue.format('DD-MM-YYYY');
});

function accountView(endpoint) {
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
            <img src="${json.users.avatar}" alt="avatar">
            <div class="level">
                Lv.${json.users.exp <= 10 ? "0" : json.users.exp <= 20 ? "1" : json.users.exp <= 40 ? "2" :
                json.users.exp <= 80 ? "3" : json.users.exp <= 160 ? "4" : json.users.exp <= 320 ? "5" :
                json.users.exp <= 640 ? "6" : json.users.exp <= 1280 ? "7" : json.users.exp <= 2560 ? "8" :
                json.users.exp <= 5120 ? "9" : json.users.exp <= 10240 ? "10" : "11"}
            </div>
        `;
        let jss = document.getElementById("modal-account-about");
        let requiredExp = json.users.exp <= 10 ? 10 : json.users.exp <= 20 ? 20 : json.users.exp <= 40 ? 40 : json.users.exp <= 80 ? 80 : json.users.exp <= 160 ? 160 : json.users.exp <= 320 ? 320 : json.users.exp <= 640 ? 640 : json.users.exp <= 1280 ? 1280 : json.users.exp <= 2560 ? 2560 : json.users.exp <= 5120 ? 5120 : json.users.exp <= 10240 ? 10240 : 99999;
        jss.innerHTML = `
            <span style="margin: 7px 0;">ID: #${json.users.id}</span>
            <span style="margin: 7px 0;">Họ và tên: <span class="text-info">${json.users.fullname}</span></span>
            <span style="margin: 7px 0;">Giới tính: ${json.users.gender === "1" ? `Nam` : json.users.gender === "2" ? `Nữ` : `Khác`}</span>
            <span style="margin: 7px 0;">Email: ${json.users.email}</span>
            <span style="margin: 7px 0;">Số điện thoại: ${json.users.phone}</span>
            <span style="margin: 7px 0;">Địa chỉ: ${json.users.address}</span>
            <span style="margin: 7px 0;">Loại tài khoản: ${json.users.userRole.id == 1 ? "Quản trị" : json.users.userRole.id == 2 ? "Quản lý" : "Người dùng"}</span>
            <span style="margin: 7px 0;">Trạng thái: <span class="${json.users.userstatus.id === "1" ? `text-customer-active` : json.users.userstatus.id === "2" ? `text-customer-warning` : `text-customer-danger`}">${json.users.userstatus.statusname}</span></span>
            <span style="margin: 7px 0;">Cấp độ: </span>
            <span class="profile-exp"><span class="profile-exp-bar" style="width: ${json.users.exp * 100 / requiredExp}%;">${json.users.exp * 100 / requiredExp}% (${json.users.exp}/${requiredExp})</span></span>
            <span style="margin: 14px 0;">Ngày tham gia: <span class="create-date">${json.users.createdDate}</span></span>
        `;
        let jsss = document.getElementById("modal-account-title");
        jsss.innerHTML = `
            <i class="fas fa-crown" style="color: yellow;"></i>
            <span class="text-account-title">
                ${json.users.exp <= 10 ? "Sắt" : json.users.exp <= 20 ? "Đồng" : json.users.exp <= 40 ? "Bạc" :
                json.users.exp <= 80 ? "Vàng" : json.users.exp <= 160 ? "Bạch Kim" : json.users.exp <= 320 ? "Kim cương" :
                json.users.exp <= 640 ? "Tinh anh" : json.users.exp <= 1280 ? "Cao thủ" : json.users.exp <= 2560 ? "Chiến tướng" :
                json.users.exp <= 5120 ? "Thách đấu" : json.users.exp <= 10240 ? "Phi thăng" : json.users.exp <= 99999 ? "Á thần" : "Siêu thần"}
            </span>
            <i class="fas fa-crown" style="color: yellow;"></i>
        `;
        let btns = document.querySelectorAll('.js-add-cart');
        let cart = document.querySelector('.js-modal');
        let modalClose = document.querySelector('.js-modal-close');
        let modalContainer = document.querySelector('.js-modal-container');
        let createDated = document.querySelectorAll(".modal-content .create-date");
        createDated.forEach((element) => {
            const dateValue = moment(element.textContent);
            element.textContent = dateValue.format('DD-MM-YYYY');
        });
        for (const btn of btns) {
            btn.addEventListener('click', showProfile);
        }
        modalClose.addEventListener('click', hideProfile);
        cart.addEventListener('click', hideProfile);
        modalContainer.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }
    ).catch(error => {
        console.info(error);
    });
}

function productView(endpoint) {
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
        debugger;
        js.innerHTML = `
            <img src="${json.product.image}" alt="avatar">
            <div class="currency level">
                <span class="money">${json.product.price}</span>
            </div>
        `;
        let jss = document.getElementById("modal-account-about");
        jss.innerHTML = `
            <span style="margin: 7px 0;">ID: #${json.product.id}</span>
            <span style="margin: 7px 0;">Sản phẩm: <span class="text-info">${json.product.name}</span></span>
            <span style="margin: 7px 0;">Phân loại: ${json.product.categorysubId.name}</span>
            <span style="margin: 7px 0;">Sản phẩm có sẵn: ${json.product.quantity}</span>
            <span style="margin: 7px 0;">Khuyến mãi: ${json.product.discount}</span>
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
        let currencyElements = document.querySelectorAll(".currency .money");
        currencyElements.forEach((element) => {
            const amountValue = parseFloat(element.textContent);
            element.textContent = numberWithCommas(amountValue);
        });
        createDated.forEach((element) => {
            const dateValue = moment(element.textContent);
            element.textContent = dateValue.format('DD-MM-YYYY');
        });
        for (const btn of btns) {
            btn.addEventListener('click', showProfile);
        }
        modalClose.addEventListener('click', hideProfile);
        cart.addEventListener('click', hideProfile);
        modalContainer.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }
    ).catch(error => {
        console.info(error);
    });
}

let cart = document.querySelector('.js-modal');
function showProfile() {
    // alert('Modal opened successfully');
    cart.classList.add('js-modal-open');
}
function hideProfile() {
    cart.classList.remove('js-modal-open');
}

function permissionAccount() {
    Swal.fire({
        icon: "warning",
        title: "Cảnh báo!",
        text: "Tài khoản của bạn không có quyền này!"
    });
}

function deleteCustomer(endpoint, id) {
    Swal.fire({
        title: 'Xóa',
        text: 'Bạn có chắc chắn muốn xóa tài khoản này ?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy'
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
                    let customerStatus = document.getElementById(`customer-status${id}`);
                    customerStatus.innerHTML = `<span class="text-customer-danger">Bị xóa</span>`;
                    Swal.fire('Xóa thành công!', 'Tài khoản sẽ bị xóa vĩnh viễn sau 30 ngày!', 'success');
                } else {
                    Swal.fire('Xóa không thành công!', 'Hệ thống đang có lỗi, vui lòng quay lại sau!', 'error');
                }
            });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            // Hành động khi người dùng hủy
            Swal.fire('Hủy', 'Bạn đã hủy thao tác xóa!');
        }
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
$(document).ready(function () {
    $(window).scroll(function () {
        if ($(this).scrollTop() > 600) {
            $('#backtop').addClass('d-block');
            $('#backtop').fadeIn();
        } else {
            $('#backtop').removeClass('d-block');
            $('#backtop').fadeOut();
        }
        if ($(this).scrollTop() > 92.2) {
            $('#nav').addClass('sticky');
        } else {
            $('#nav').removeClass('sticky');
        }
    });
    $('#backtop').click(function () {
        $('html, body').animate({scrollTop: 0}, 0);
        return false;
    });
});
/////////////////////////
AOS.init({
    offset: 150,
    delay: 0,
    duration: 1000
});

//////////////////////////
function showDropdownMenu(elem) {
    elem.querySelector('.dropdown-menu').classList.add('show');
}

function hideDropdownMenu(elem) {
    elem.querySelector('.dropdown-menu').classList.remove('show');
}

//            icon search        
document.getElementById('searchToggle').addEventListener('click', function () {
    const searchInput = document.getElementById('searchInput');
    if (searchInput.style.display === 'none') {
        searchInput.style.display = 'block';
        searchInput.focus();
    } else {
        searchInput.style.display = 'none';
    }
});

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
let createDate = document.querySelectorAll(".row .create-date");
createDate.forEach((element) => {
    const dateValue = moment(element.textContent);
    element.textContent = dateValue.format('DD-MM-YYYY');
});

let timeAgo = document.querySelectorAll(".row .time-ago");
timeAgo.forEach((element) => {
    const timeValue = moment.utc(element.textContent);
    element.textContent = moment(timeValue).locale("vi").utcOffset(0).fromNow();
});

var discountElements = document.querySelectorAll(".price-discount");
var oldPriceElements = document.querySelectorAll(".price-del");
var newPriceElements = document.querySelectorAll(".price-new");

for (var i = 0; i < discountElements.length; i++) {
    var discountPercentage = parseFloat(discountElements[i].querySelector(".discount").textContent);
    var originalPriceText = oldPriceElements[i].querySelector(".price-old").textContent;
    var originalPrice = parseFloat(originalPriceText.replace(/,/g, ""));

    if (discountPercentage > 0) {
        var discountedPrice = originalPrice * (1 - discountPercentage / 100);
        oldPriceElements[i].style.display = 'inline-block';
        discountElements[i].style.display = 'inline-block';
        newPriceElements[i].textContent = numberWithCommas(discountedPrice);
        ;
    }
}

///////////////////
document.addEventListener('DOMContentLoaded', function() {
    // Lấy ngôn ngữ từ localStorage hoặc mặc định là 'vi'
    const langCode = localStorage.getItem('selectedLang') || 'vi';

    // Cập nhật ngôn ngữ khi trang tải
    changeLanguage(langCode);
});

// Hàm thay đổi ngôn ngữ
function changeLanguage(langCode) {
    // Lưu ngôn ngữ vào localStorage
    localStorage.setItem('selectedLang', langCode);

    // Cập nhật cờ và văn bản hiển thị
    if (langCode === 'vi') {
        document.getElementById('selectedFlag').src = 'https://flagcdn.com/w20/vn.png';
        document.getElementById('selectedLanguage').textContent = 'Tiếng Việt';
    } else if (langCode === 'en') {
        document.getElementById('selectedFlag').src = 'https://flagcdn.com/w20/gb.png';
        document.getElementById('selectedLanguage').textContent = 'English';
    }

    // Ẩn tất cả các dấu check
    document.getElementById('check-vi').style.display = 'none';
    document.getElementById('check-en').style.display = 'none';

    // Hiển thị dấu check cho ngôn ngữ được chọn
    if (langCode === 'vi') {
        document.getElementById('check-vi').style.display = 'inline';
    } else if (langCode === 'en') {
        document.getElementById('check-en').style.display = 'inline';
    }
}

function checkQuantity(index) {
    var inputQuantity = document.getElementById('quantity');
    var minQuantity = 1;
    var maxQuantity = parseInt(document.getElementById('product-quantity').textContent);

    if (inputQuantity.value < minQuantity) {
        inputQuantity.value = minQuantity;
        quantityCart = minQuantity;
        Swal.fire('Lỗi!', 'Vui lòng nhập giá trị dương!', 'error');
    } else if (inputQuantity.value > maxQuantity - index) {
        inputQuantity.value = minQuantity;
        quantityCart = minQuantity;
        Swal.fire('Cảnh báo!', 'Số lượng sản phẩm shop có sẳn: ' + maxQuantity + ' - Xin lỗi vì sự bất tiện này!', 'warning');
    }
}

function setNotification(flag, setValue) {
    let badgePosition = document.getElementById('badge-position');
    let notification = document.getElementById('session-notification');
    let setValueNumber = parseInt(setValue);
    badgePosition.classList.remove('d-none');
    if (flag === 0) {
        badgePosition.classList.add('d-none');
        notification.textContent = setValueNumber;
    } else {
        notification.textContent = parseInt(notification.textContent) + setValueNumber;
    }
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

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
function checkQuantity() {
    var inputQuantity = document.getElementById('quantity');
    var minQuantity = 1;
    var maxQuantity = parseInt(document.getElementById('product-quantity').textContent);

    if (inputQuantity.value < minQuantity) {
        inputQuantity.value = minQuantity;
        Swal.fire('Lỗi!', 'Vui lòng nhập giá trị không âm!', 'error');
    } else if (inputQuantity.value > maxQuantity) {
        Swal.fire('Lỗi!', 'Số lượng sản phẩm shop có sẳn: ' + maxQuantity + ' - Xin lỗi vì sự bất tiện này!', 'error');
        inputQuantity.value = minQuantity;
    }
}
/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
///////////////////////Product Detail//////////////////////
var discountPercentage = parseFloat(document.getElementById("discount").textContent);
var originalPrice = parseFloat(document.getElementById("price-old").textContent);
if (discountPercentage > 0) {
    var discountedPrice = originalPrice * (1 - discountPercentage / 100);
    document.getElementById("price-del").style.display = 'inline-block';
    document.getElementById("price-discount").style.display = 'inline-block';
    document.getElementById("price-new").textContent = discountedPrice.toFixed(2) + "₫";
}

const ratingElements = document.querySelectorAll(".line-padding #averageRating");
ratingElements.forEach((element) => {
    if (element.textContent) {
        const starValue = parseFloat(element.textContent);
        element.textContent = starValue.toFixed(1);
    }
});

function hasUserRatedProductToday(userId, productId) {
    const key = `rating_${userId}_${productId}`;
    const lastRatingTimestamp = localStorage.getItem(key);
    if (lastRatingTimestamp) {
        const lastRatingDate = new Date(Number(lastRatingTimestamp));
        const today = new Date();
        return (
                lastRatingDate.getDate() === today.getDate() &&
                lastRatingDate.getMonth() === today.getMonth() &&
                lastRatingDate.getFullYear() === today.getFullYear()
                );
    }
    return false;
}

function saveUserProductRating(userId, productId) {
    const key = `rating_${userId}_${productId}`;
    localStorage.setItem(key, Date.now());
}

function ratingStar(endpoint, obj, userId, productId) {
    const successAlert = document.querySelector('.alert-success');
    const failureAlert = document.querySelector('.alert-danger');
    if (hasUserRatedProductToday(userId, productId)) {
        failureAlert.style.display = 'block';
        setTimeout(() => {
            failureAlert.style.display = 'none';
        }, 5000);
        return;
    }
    fetch(endpoint, {
        method: "put",
        body: JSON.stringify({
            "star": obj.value
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(() => {
        let elementReviewCount = document.getElementById('reviewCount');
        let elementAverageRating = document.getElementById('average-rating');
        let reviewCount = parseInt(elementReviewCount.textContent);
        let averageRating = parseFloat(elementAverageRating.textContent);
        if (!reviewCount || isNaN(reviewCount)) {
            reviewCount = 1;
            averageRating = obj.value;
            elementAverageRating.innerHTML = obj.value;
        } else {
            reviewCount = reviewCount + 1;
            averageRating = ((averageRating * (reviewCount - 1) + parseInt(obj.value)) / reviewCount).toFixed(1);
            elementAverageRating.innerHTML = ((averageRating * (reviewCount - 1) + parseInt(obj.value)) / reviewCount);
        }
        elementReviewCount.innerHTML = reviewCount;
        document.getElementById('averageRating').innerHTML = averageRating;
        saveUserProductRating(userId, productId);
        successAlert.style.display = 'block';
        setTimeout(() => {
            successAlert.style.display = 'none';
        }, 5000);
    });
}

function reviewProduct(event){
    var selectedImage = event.target;
    // Lấy tất cả các ảnh thumbnails
    var thumbnails = document.querySelectorAll(".img-thumbnail");
    // Loại bỏ viền màu đỏ cho tất cả các ảnh thumbnails
    thumbnails.forEach(function(thumbnail) {
        thumbnail.style.border = "none";
    });
    // Đặt đường dẫn cho thẻ img
    var review = document.getElementById("review-product");
    review.src = selectedImage.src;
    // Thêm viền màu đỏ cho ảnh đang được chọn
    selectedImage.style.border = "2px solid red";
}
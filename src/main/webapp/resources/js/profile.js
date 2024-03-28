/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
let modalFrame = document.querySelector('.js-modal');
function showFrame() {
    modalFrame.classList.add('js-modal-open');
}
function hideFrame() {
    modalFrame.classList.remove('js-modal-open');
}

function AvatarBrowse() {
    document.getElementById("avatarBrowse").click();
}

function showPreviewDiv(event) {
    document.getElementById("changeAvatar").style.display = 'none';
    document.getElementById("saveAvatar").style.display = 'block';
    const oldview = document.getElementById("img-oldview");
    let preview = document.getElementById("img-preview");
    preview.src = oldview.src;
    let currentUserId = document.getElementById("currentUserId").textContent;
    let pathUserID = document.getElementById("avatarUserId");
    pathUserID.value = currentUserId;
    if (event.target.files.length > 0) {
        let src = URL.createObjectURL(event.target.files[0]);
        preview.src = src;
    }
}

function cancelChangeAvatar() {
    document.getElementById("changeAvatar").style.display = 'block';
    document.getElementById("saveAvatar").style.display = 'none';
    const oldview = document.getElementById("img-oldview");
    let preview = document.getElementById("img-preview");
    preview.src = oldview.src;
}

function changeAvatarFrame() {
    let btns = document.querySelectorAll('.js-modal-frame');
    let modalFrame = document.querySelector('.js-modal');
    let modalClose = document.querySelector('.js-modal-close');
    let modalContainer = document.querySelector('.js-modal-container');
    for (let btn of btns) {
        btn.addEventListener('click', showFrame);
    }
    modalClose.addEventListener('click', hideFrame);
    modalFrame.addEventListener('click', hideFrame);
    modalContainer.addEventListener('click', function (event) {
        event.stopPropagation();
    });
}

function focusItem(event, id) {
    let selectedItem = event.target;
    document.querySelectorAll(".item-frame").forEach(function (item) {
        item.style.border = "none";
    });
    let frameId = document.getElementById('frame-id');
    frameId.textContent = id;
    selectedItem.style.border = "5px solid #00bbb3";
}

function saveAvatarFrame(endpoint) {
    let frameId = parseInt(document.getElementById('frame-id').textContent);
    fetch(endpoint, {
        method: "put",
        body: JSON.stringify({
            "frameId": frameId
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status === 200) {
            hideFrame();
            Swal.fire('Cập nhật thành công!', 'Dữ liệu sẽ mất chút thời gian để thay đổi!', 'success');
        }
    });
}

/////////////////////////////////////////
// Lấy nội dung của thẻ span
const facebookURL = document.getElementById("facebookUsername").textContent;
const instagramURL = document.getElementById("instagramUsername").textContent;
const youtubeURL = document.getElementById("youtubeUsername").textContent;
const tiktokURL = document.getElementById("tiktokUsername").textContent;
// Tách phần cuối cùng của URL (tên người dùng)
var fusername = facebookURL.match(/\/([^\/]+)\/?$/)[1];
var iusername = instagramURL.match(/\/([^\/]+)\/?$/)[1];
var yusername = youtubeURL.match(/\/([^\/]+)\/?$/)[1];
var tusername = tiktokURL.match(/\/([^\/]+)\/?$/)[1];
// Hiển thị tên người dùng
document.getElementById("facebookUsername").textContent = fusername;
document.getElementById("instagramUsername").textContent = iusername;
document.getElementById("youtubeUsername").textContent = yusername;
document.getElementById("tiktokUsername").textContent = tusername;
function changeNetwork() {
    document.getElementById("changeNetwork").style.display = 'none';
    document.getElementById("saveNetwork").style.display = 'block';
    let currentUserId = document.getElementById("currentUserId").textContent;
    let pathUserID = document.getElementById("networkUserId");
    pathUserID.value = currentUserId;
    let pathFacebook = document.getElementById("facebook");
    pathFacebook.style.display = 'block';
    pathFacebook.value = facebookURL;
    let pathInstagram = document.getElementById("instagram");
    pathInstagram.style.display = 'block';
    pathInstagram.value = instagramURL;
    let pathYoutube = document.getElementById("youtube");
    pathYoutube.style.display = 'block';
    pathYoutube.value = youtubeURL;
    let pathTiktok = document.getElementById("tiktok");
    pathTiktok.style.display = 'block';
    pathTiktok.value = tiktokURL;
}

function cancelNetwork() {
    document.getElementById("changeNetwork").style.display = 'block';
    document.getElementById("saveNetwork").style.display = 'none';
    document.getElementById("facebook").style.display = 'none';
    document.getElementById("instagram").style.display = 'none';
    document.getElementById("youtube").style.display = 'none';
    document.getElementById("tiktok").style.display = 'none';
}

function editProfile() {
    document.getElementById("editProfile").style.display = 'none';
    document.getElementById("saveEditProfile").style.display = 'block';
    let currentUserId = document.getElementById("currentUserId").textContent;
    let pathUserID = document.getElementById("editUserId");
    pathUserID.value = currentUserId;
    let pathFullname = document.getElementById("fullname");
    pathFullname.style.display = 'block';
    pathFullname.value = document.getElementById("editFullname").textContent;
    document.getElementById("editFullname").style.display = 'none';
    let pathEmail = document.getElementById("email");
    pathEmail.style.display = 'block';
    pathEmail.value = document.getElementById("editEmail").textContent;
    document.getElementById("editEmail").style.display = 'none';
    let pathPhone = document.getElementById("phone");
    pathPhone.style.display = 'block';
    pathPhone.value = document.getElementById("editPhone").textContent;
    document.getElementById("editPhone").style.display = 'none';
    let gender = document.getElementById("gender");
    let pathGender = document.getElementById("editGender");
    gender.value = pathGender.textContent;
    document.getElementById("gender").style.display = 'block';
    document.getElementById("editGenderText").style.display = 'none';
    document.getElementById("editAddress").style.display = 'none';
    let pathAddress = document.getElementById("address");
    pathAddress.value = document.getElementById("editAddress").textContent;
    document.getElementById("editCity").style.display = 'block';
    document.getElementById("editDistrict").style.display = 'block';
    document.getElementById("editWard").style.display = 'block';
    let pathCity = document.getElementById("editCity");
    let editCity = document.getElementById("editAddress").textContent.split(' - ')[2];
    for (let i = 0; i < pathCity.options.length; i++) {
        var option = pathCity.options[i];
        if (option.label === editCity) {
            option.selected = true;
            break;
        }
    }
    let pathDistrict = document.getElementById("editDistrict");
    let editDistrict = document.getElementById("editAddress").textContent.split(' - ')[1];
    var option = document.createElement("option");
    option.value = "";
    option.label = editDistrict;
    option.selected = true;
    option.disabled = true;
    pathDistrict.appendChild(option);
    let pathWard = document.getElementById("editWard");
    let editWard = document.getElementById("editAddress").textContent.split(' - ')[0];
    var option = document.createElement("option");
    option.value = "";
    option.label = editWard;
    option.selected = true;
    option.disabled = true;
    pathWard.appendChild(option);
}

function cancelEditProfile() {
    document.getElementById("saveEditProfile").style.display = 'none';
    document.getElementById("editProfile").style.display = 'block';
    document.getElementById("fullname").style.display = 'none';
    document.getElementById("editFullname").style.display = 'block';
    document.getElementById("email").style.display = 'none';
    document.getElementById("editEmail").style.display = 'block';
    document.getElementById("phone").style.display = 'none';
    document.getElementById("editPhone").style.display = 'block';
    document.getElementById("gender").style.display = 'none';
    document.getElementById("editGenderText").style.display = 'block';
    document.getElementById("editCity").style.display = 'none';
    document.getElementById("editDistrict").style.display = 'none';
    document.getElementById("editWard").style.display = 'none';
    document.getElementById("editAddress").style.display = 'block';
}

function selectCity(city) {
    let address = document.getElementById("address");
    let editCity = document.getElementById("editCity");
    let editDistrict = document.getElementById("editDistrict");
    let editWard = document.getElementById("editWard");
    let editCityLabel = editCity.options[editCity.selectedIndex].label;
    let endpoint = city + "/" + editCity.value;
    fetch(endpoint, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/xml'
        }
    }).then(res =>
        res.json()
    ).then(data => {
        editDistrict.innerHTML = '';
        var option = document.createElement("option");
        option.value = "";
        option.label = "Quận/Huyện";
        option.selected = true;
        editDistrict.appendChild(option);
        data.forEach(district => {
            var option = document.createElement("option");
            option.value = district.id;
            option.label = district.district;
            editDistrict.appendChild(option);
        });
        editWard.innerHTML = '';
        var option = document.createElement("option");
        option.value = "";
        option.label = "Phường/Xã";
        option.selected = true;
        editWard.appendChild(option);
    }).catch(error => {
        console.info(error);
    });
    let addressParts = address.value.split(" - ");
    addressParts[2] = editCityLabel;
    address.value = addressParts.join(" - ");
}


function selectDistrict(district) {
    let address = document.getElementById("address");
    let editDistrict = document.getElementById("editDistrict");
    let editWard = document.getElementById("editWard");
    let editDistrictLabel = editDistrict.options[editDistrict.selectedIndex].label;
    let endpoint = district + "/" + editDistrict.value;
    fetch(endpoint, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/xml'
        }
    }).then(res =>
        res.json()
    ).then(data => {
        editWard.innerHTML = '';
        var option = document.createElement("option");
        option.value = "";
        option.label = "Phường/Xã";
        option.selected = true;
        editWard.appendChild(option);
        data.forEach(ward => {
            var option = document.createElement("option");
            option.value = ward.id;
            option.label = ward.ward;
            editWard.appendChild(option);
        });
    }).catch(error => {
        console.info(error);
    });
    let addressParts = address.value.split(" - ");
    addressParts[1] = editDistrictLabel;
    address.value = addressParts.join(" - ");
}

function selectWard() {
    let address = document.getElementById("address");
    let editWard = document.getElementById("editWard");
    let editWardLabel = editWard.options[editWard.selectedIndex].label;
    let addressParts = address.value.split(" - ");
    addressParts[0] = editWardLabel;
    address.value = addressParts.join(" - ");
}

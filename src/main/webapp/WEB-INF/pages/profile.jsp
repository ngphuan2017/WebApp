<%-- 
    Document   : profile
    Created on : Apr 1, 2023, 1:14:34 PM
    Author     : admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/me/profile" var="changeProfile" />
<c:url value="/me/orders" var="ordered" />
<c:url value="/api/users/register/city" var="city"/>
<c:url value="/api/users/register/district" var="district"/>
<c:url value="/api/users/avatar/frame" var="framed"/>

<h1 class="text-center text-success mt-4 mb-4" data-aos="flip-down" style="text-transform: uppercase;"><i class="fa-solid fa-user-gear"></i> Thông tin tài khoản</h1>
<div class="container">
    <div class="main-body" style="padding: 15px;">
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3" data-aos="fade-right">
                <div class="card-profile">
                    <div class="card-body-profile">
                        <div class="d-flex flex-column align-items-center text-center">
                            <div class="position-relative">
                                <img id="img-preview" src="${currentUser.avatar}" alt="${currentUser.fullname}"
                                     width="150" height="150">
                                <img class="avatar-frame" width="150" height="150"
                                     src="${currentUser.avatarFrame.url}"/>
                                <img id="img-oldview" src="${currentUser.avatar}" alt="Avatar" class="d-none"
                                     width="150" height="150">
                            </div>
                            <div class="mt-3">
                                <span id="currentUserId" style="display: none;">${currentUser.id}</span>
                                <h4>${currentUser.fullname}</h4>
                                <c:set var="foundLevel" value="false" />
                                <c:forEach items="${listUserLevels}" var="exp" varStatus="status">
                                    <c:if test="${currentUser.exp <= exp.requiredExp && status.index > 0 && foundLevel eq false}">
                                        <img class="rounded mb-1" src="${listUserLevels[status.index - 1].rankImg}" style="width: 100px; height: 100px;" alt="rank" />
                                        <c:set var="foundLevel" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:set var="foundLevel" value="false" />
                                <c:forEach items="${listUserLevels}" var="exp" varStatus="status">
                                    <c:if test="${currentUser.exp <= exp.requiredExp && status.index > 0 && foundLevel eq false}">
                                        <p class="font-size-sm" style="color: ${listUserLevels[status.index - 1].rankColor}">
                                            ${listUserLevels[status.index - 1].levelName}
                                        </p>
                                        <c:set var="foundLevel" value="true" />
                                    </c:if>
                                </c:forEach>
                                <div class="d-flex m-auto">
                                    <form:form method="POST" action="${changeProfile}"
                                               modelAttribute="user" enctype="multipart/form-data">
                                        <form:hidden path="id" id="avatarUserId"/>
                                        <form:hidden path="avatar"/>
                                        <button onclick="AvatarBrowse()" type="button" id="changeAvatar" class="btn btn-outline-primary m-1">Đổi Avatar</button>
                                        <div class="m-1" id="saveAvatar" style="display: none;">
                                            <button type="submit" class="btn btn-outline-primary">Lưu Thay Đổi</button>
                                            <button type="button" onclick="cancelChangeAvatar()" class="btn btn-outline-danger">Trở lại</button>
                                        </div>
                                        <form:input type="file" id="avatarBrowse" path="file"
                                                    onchange="showPreviewDiv(event);"
                                                    accept="image/*" class="form-input" cssStyle="display: none"/>
                                    </form:form>
                                    <button type="button" onclick="changeAvatarFrame()" class="btn btn-outline-success m-1 js-modal-frame">Đổi khung viền</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-profile mt-3">
                    <form:form method="POST" action="${changeProfile}"
                               modelAttribute="user" enctype="multipart/form-data">
                        <form:hidden path="id" id="networkUserId"/>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                <h6 class="mb-0">
                                    <i class="fa-brands fa-facebook-f" style="font-size: 24px; color: #0f6efd; width: 27px;"></i>&nbsp;Facebook
                                </h6>
                                <a id="facebookUsername" href="${currentUser.facebook}" target="_blank" rel="noopener" class="network-profile">${currentUser.facebook}</a>
                                <form:input path="facebook" class="form-control" id="facebook"
                                            type="text" style="display: none;"/>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                <h6 class="mb-0">
                                    <i class="fa-brands fa-instagram" style="font-size: 24px; color: #fc21c8; width: 27px;"></i>&nbsp;Instagram
                                </h6>
                                <a id="instagramUsername" href="${currentUser.instagram}" target="_blank" rel="noopener" class="network-profile">${currentUser.instagram}</a>
                                <form:input path="instagram" class="form-control" id="instagram"
                                            type="text" style="display: none;"/>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                <h6 class="mb-0">
                                    <i class="fa-brands fa-youtube" style="font-size: 24px; color: #dc3545; width: 27px;"></i>&nbsp;Youtube</h6>
                                <a id="youtubeUsername" href="${currentUser.youtube}" target="_blank" rel="noopener" class="network-profile">${currentUser.youtube}</a>
                                <form:input path="youtube" class="form-control" id="youtube"
                                            type="text" style="display: none;"/>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                <h6 class="mb-0">
                                    <i class="fa-brands fa-tiktok" style="font-size: 24px; width: 27px;"></i>&nbsp;Tiktok</h6>
                                <a id="tiktokUsername" href="${currentUser.tiktok}" target="_blank" rel="noopener" class="network-profile">${currentUser.tiktok}</a>
                                <form:input path="tiktok" class="form-control" id="tiktok"
                                            type="text" style="display: none;"/>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                <a href="javascript:;" id="changeNetwork" class="m-auto" onclick="changeNetwork()"><button type="button" class="btn btn-outline-info">Thay đổi</button></a>
                                <div id="saveNetwork" class="m-auto" style="display: none;">
                                    <button type="submit" class="btn btn-outline-success m-auto">Lưu</button>
                                    <button type="button" onclick="cancelNetwork()" class="btn btn-outline-danger">Trở lại</button>
                                </div>
                            </li>
                        </ul>
                    </form:form>
                </div>
            </div>
            <div class="col-md-8" data-aos="fade-left">
                <div class="card-profile mb-3">
                    <form:form method="POST" action="${changeProfile}"
                               modelAttribute="user" enctype="multipart/form-data">
                        <form:hidden path="id" id="editUserId"/>
                        <div class="card-body-profile">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Tên đăng nhập</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <c:choose>
                                        <c:when test="${currentUser.googleID != null}">
                                            <span id="editUsername">Tài khoản đăng nhập bằng liên kết Google #ID: ${currentUser.username}</span>
                                        </c:when>
                                        <c:when test="${currentUser.facebookID != null}">
                                            <span id="editUsername">Tài khoản đăng nhập bằng liên kết Facebook #ID: ${currentUser.username}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span id="editUsername">${currentUser.username}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Họ và tên</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <span id="editFullname">${currentUser.fullname}</span>
                                    <form:input path="fullname" class="form-control" id="fullname" onchange="validateFullname()"
                                                type="text" placeholder="Họ và tên" style="display: none;"/>
                                    <div class="error" id="fullname-error"></div>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Email</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <span id="editEmail">${currentUser.email}</span>
                                    <form:input path="email" class="form-control" id="email" onchange="validateEmail()"
                                                type="email" placeholder="Email" style="display: none;"/>
                                    <div class="error" id="email-error"></div>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Số điện thoại</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <span id="editPhone">${currentUser.phone}</span>
                                    <form:input path="phone" class="form-control" id="phone" onchange="validatePhone()"
                                                type="text" placeholder="Số điện thoại" style="display: none;" maxlength="10" minlength="10"/>
                                    <div class="error" id="phone-error"></div>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Địa chỉ</h6>
                                </div>
                                <div class="col-sm-9 text-secondary row m-auto">
                                    <span id="editAddress" style="padding: 0;">${currentUser.address}</span>
                                    <form:hidden path="address" id="address"/>
                                    <select class="form-control col-4" id="editWard" onchange="selectWard()" style="display: none; width: 33.33%;">
                                    </select>
                                    <select class="form-control col-4" id="editDistrict" onchange="selectDistrict('${district}')" style="display: none; width: 33.33%;">
                                    </select>
                                    <select class="form-control col-4" id="editCity" onchange="selectCity('${city}')" style="display: none; width: 33.33%;">
                                        <c:forEach items="${listCitys}" var="c">
                                            <option value="${c.id}" label="${c.city}">${c.city}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Giới tính</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <span id="editGender" style="display: none;">${currentUser.gender}</span>
                                    <span id="editGenderText">
                                        <c:choose>
                                            <c:when test="${currentUser.gender == 1}">
                                                Nam
                                            </c:when>
                                            <c:when test="${currentUser.gender == 2}">
                                                Nữ
                                            </c:when>
                                            <c:when test="${currentUser.gender == 3}">
                                                Khác
                                            </c:when>
                                        </c:choose>
                                    </span>
                                    <form:select path="gender" id="gender" class="form-control" style="display: none;">
                                        <form:option value="" label="Giới tính"/>
                                        <form:option value="1" label="Nam"/>
                                        <form:option value="2" label="Nữ"/>
                                        <form:option value="3" label="Khác"/>
                                    </form:select>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Danh hiệu</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <c:choose>
                                        <c:when test="${currentUser.userRole.id == 1}">
                                            <span class="badge bg-danger">Trùm Cuối</span>
                                            <span class="badge bg-success">Quản trị viên</span>
                                        </c:when>
                                        <c:when test="${currentUser.userRole.id == 2}">
                                            <span class="badge bg-success">Quản trị viên</span>
                                        </c:when>
                                    </c:choose>
                                    <c:set var="foundRank" value="false" />
                                    <c:forEach items="${listUserLevels}" var="exp" varStatus="status">
                                        <c:if test="${currentUser.exp <= exp.requiredExp && status.index > 0 && foundRank eq false}">
                                            <span class="badge bg-primary">${listUserLevels[status.index - 1].userRank}</span>
                                            <c:set var="foundRank" value="true" />
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Cấp độ</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    <div class="profile-exp">
                                        <c:set var="foundLevel" value="false" />
                                        <c:forEach items="${listUserLevels}" var="exp" varStatus="status">
                                            <c:if test="${currentUser.exp <= exp.requiredExp && foundLevel eq false}">
                                                <span class="profile-exp-bar" style="width: ${currentUser.exp * 100 / exp.requiredExp}%;">${currentUser.exp * 100 / exp.requiredExp}% (${currentUser.exp}/${exp.requiredExp})</span>
                                                <c:set var="foundLevel" value="true" />
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Ngày gia nhập</h6>
                                </div>
                                <div class="col-sm-9 text-secondary create-date">
                                    ${currentUser.createdDate}
                                </div>
                            </div><hr/>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="d-flex justify-content-center">
                                        <a href="javascript:;" id="editProfile" onclick="editProfile()"><button class="btn btn-outline-secondary" type="button"><i class="fa-solid fa-pen"></i> Chỉnh sửa thông tin</button></a>
                                        <div id="saveEditProfile" style="display: none;">
                                            <button type="submit" class="btn btn-outline-success">Lưu</button>
                                            <button type="button" onclick="cancelEditProfile()" class="btn btn-outline-danger">Trở lại</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>

                <div class="row gutters-sm">
                    <div class="col-sm-6 mb-3">
                        <div class="card-profile h-100">
                            <div class="card-body-profile">
                                <h6 class="d-flex align-items-center mb-3"><i class="fa-solid fa-list-ul" style="color: #0dcaf0;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Đơn Mua</h6>
                                <div style="overflow: auto; height: 125px;">
                                    <c:forEach items="${orders}" var="o">
                                        <li id="${o.id}">
                                            <a href="${ordered}/${o.id}">Đơn #${o.id} ~ <span class="create-date">${o.createdDate}</span> ( ${o.type.statusname} )</a>
                                        </li>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 mb-3">
                        <div class="card-profile h-100">
                            <div class="card-body-profile">
                                <h6 class="d-flex align-items-center mb-3"><i class="fa-solid fa-calendar-days" style="color: #0dcaf0;"></i>&nbsp;&nbsp;&nbsp;&nbsp;Lịch sử đăng nhập</h6>
                                <div style="overflow: auto; height: 125px;">
                                    <c:forEach items="${clients}" var="c">
                                        <li style="display: flex; flex-wrap: wrap; gap: 4px; margin-bottom: 4px;" id="${c.id}">
                                            <span class="badge text-bg-warning create-date">${c.timestamp}</span>
                                            <span class="badge text-bg-success">IP: ${c.ip}</span>
                                            <span class="badge text-bg-secondary">Device: ${c.device}</span>
                                        </li>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="js-modal">
    <div class="modal-container-frame js-modal-container">
        <div class="js-modal-close">
            <i class="fa-solid fa-xmark"></i>
        </div>
        <header class="modal-header-black">
            <h3><i class="fa-solid fa-address-card"></i> Khung viền Avatar</h3>
        </header>
        <div class="modal-body-frame overflow-auto p-3">
            <div class="row p-0">
                <div class="d-none" id="frame-id"></div>
                <c:forEach items="${frames}" var="f">
                    <div class="block-frame position-relative col-lg-3 col-md-4 col-sm-6 col-12">
                        <a href="javascript:;" onclick="focusItem(event, ${f.id})">
                            <img src="https://res.cloudinary.com/dkmug1913/image/upload/v1711524012/WebApp/Khung/avatar_default_gpsilt.jpg" alt="default" width="180" height="180" />
                            <img class="avatar-frame item-frame" style="left: 2.2rem;" src="${f.url}" alt="${f.id}" width="180" height="180" />
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="footer-modal-black p-2">
            <button type="button" onclick="saveAvatarFrame('${framed}/${currentUser.id}')" class="btn btn-outline-light disabled" id="save-avatar-frame">Lưu Thay Đổi</button>
            <button type="button" onclick="hideFrame()" class="btn btn-outline-danger">Trở lại</button>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/profile.js" />"></script>
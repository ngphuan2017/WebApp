/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function loadSpinner(flag) {
    let els = document.getElementsByClassName("spinner");
    for (let d of els)
        d.style.display = flag;
}

function accountView(endpoint, leveled) {
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
            <div class="position-relative">
                <img src="${json.users.avatar}" alt="${json.users.fullname}">
                <img class="avatar-frame p-0" src="${json.users.avatarFrame.url}"/>
            </div>
            <div class="level level-${json.users.id}"></div>
        `;
        let required = document.querySelector(`.required-exp-${json.users.id}`);
        let jss = document.getElementById("modal-account-about");
        let requiredExp = json.users.exp >= 12800 ? 99999 : parseInt(required.textContent) !== 0 ? parseInt(required.textContent) : 50;
        jss.innerHTML = `
            <span style="margin: 7px 0;">ID: #${json.users.id}</span>
            <span style="margin: 7px 0;">Họ và tên: <span class="text-info">${json.users.fullname}</span></span>
            <span style="margin: 7px 0;">Giới tính: ${json.users.gender === "1" ? `Nam` : json.users.gender === "2" ? `Nữ` : `Khác`}</span>
            <span style="margin: 7px 0;">Trạng thái: <span class="${json.users.userstatus.id === "1" ? `text-customer-active` : json.users.userstatus.id === "2" ? `text-customer-warning` : `text-customer-danger`}">${json.users.userstatus.statusname}</span></span>
            <span style="margin: 7px 0;">Danh hiệu: ${json.users.userRole.id === "1" ? `<span class="badge bg-danger m-1 level-name-${json.users.id}" style="font-size: 11px;">Trùm cuối</span><span class="badge bg-success m-1 level-name-${json.users.id}" style="font-size: 11px;">Quản trị viên</span><span class="badge bg-primary m-1 level-name-${json.users.id}" style="font-size: 11px;">${json.users.exp}</span>`
                : json.users.userRole.id === "2" ? `<span class="badge bg-success m-1 level-name-${json.users.id}" style="font-size: 11px;">Quản trị viên</span><span class="badge bg-primary m-1 level-name-${json.users.id}" style="font-size: 11px;">${json.users.exp}</span>`
                : `<span class="badge bg-primary level-name-${json.users.id}" style="font-size: 11px;">${json.users.exp}</span>`}</span>
            <span style="margin: 7px 0;">Cấp độ: </span>
            <span class="profile-exp"><span class="profile-exp-bar" style="width: ${json.users.exp * 100 / requiredExp}%;">${json.users.exp * 100 / requiredExp}% (${json.users.exp}/${requiredExp})</span></span>
            <span style="margin: 14px 0;">Ngày tham gia: <span class="create-date">${json.users.createdDate}</span></span>
        `;
        let jsss = document.getElementById("modal-account-title");
        jsss.innerHTML = `
            <div class="text-account-title">
                <i class="fa-solid fa-gun" style="color: yellow;"></i>
                <img class="rounded account-rank-img-${json.users.id}" src="https://res.cloudinary.com/dkmug1913/image/upload/v1709201059/WebApp/Rank/iron_abwewo.png" style="width: 60px; height: 60px;" alt="rank" />
                <i class="fa-solid fa-gun fa-flip-horizontal" style="color: yellow;"></i>
            </div>
        `;
        levelCmt(leveled + "/" + json.users.exp, json.users.id);
        let btns = document.querySelectorAll('.js-modal-user');
        let modalAccount = document.querySelector('.js-modal-account');
        let modalClose = document.querySelector('.js-modal-close');
        let modalContainer = document.querySelector('.js-modal-container');
        let createDated = document.querySelectorAll(".modal-content .create-date");
        createDated.forEach((element) => {
            const dateValue = moment(element.textContent);
            element.textContent = dateValue.format('DD-MM-YYYY');
        });
        for (const btn of btns) {
            btn.addEventListener('click', showAccount);
        }
        modalClose.addEventListener('click', hideAccount);
        modalAccount.addEventListener('click', hideAccount);
        modalContainer.addEventListener('click', function (event) {
            event.stopPropagation();
        });
    }
    ).catch(error => {
        console.info(error);
    });
}

function loadComments(endpointed, voted, report, deleted, changed, leveled) {
    loadSpinner("block");
    const endpointInfo = extractBaseEndpointAndPage(endpointed);
    const {endpoint, page} = endpointInfo;
    const updatedEndpoint = `${endpoint}?page=${page}`;
    fetch(updatedEndpoint).then(res => res.json()).then(data => {
        let msg = "";
        let userId = parseInt(document.getElementById("currentUserId").textContent);
        let userRole = parseInt(document.getElementById("currentUserRole").textContent);
        for (let d of data.comments) {
            msg += `
                <div class="d-flex flex-start m-2" id="comment${d.id}">
                    <div class="d-none required-exp-${d.userid.id}"></div>
                    <a href="javascript:;" onclick="accountView('${endpoint}/${d.userid.id}', '${leveled}')" class="js-modal-user">
                        <div class="position-relative float-start">
                            <img class="shadow-1-strong me-3" src="${d.userid.avatar}" alt="${d.userid.fullname}" width="50" height="50" />
                            <img class="avatar-frame item-frame" width="50" height="50" src="${d.userid.avatarFrame.url}"/>
                        </div>
                    </a>
                    <div class="card w-100">
                      <div class="card-body p-1">
                        <div>
                          <div class="d-flex align-items-center justify-content-between">
                            <h6>${d.userid.fullname} <img class="rounded rank-${d.userid.id}" src="https://res.cloudinary.com/dkmug1913/image/upload/v1709201059/WebApp/Rank/iron_abwewo.png" style="width: 30px; height: 30px;" alt="rank" />
                                ${d.userid.userRole.id === 1 ? `<span class="badge bg-danger m-1 level-name-${d.userid.id}" style="font-size: 11px;">Trùm cuối</span><span class="badge bg-success m-1 level-name-${d.userid.id}" style="font-size: 11px;">Quản trị viên</span><span class="badge bg-primary m-1 level-name-${d.userid.id}" style="font-size: 11px;">${d.userid.exp}</span>`
                    : d.userid.userRole.id === 2 ? `<span class="badge bg-success m-1 level-name-${d.userid.id}" style="font-size: 11px;">Quản trị viên</span><span class="badge bg-primary m-1 level-name-${d.userid.id}" style="font-size: 11px;">${d.userid.exp}</span>`
                    : `<span class="badge bg-primary level-name-${d.userid.id}" style="font-size: 11px;">${d.userid.exp}</span>`}
                            </h6>
                            <div class="dropdown mr-auto">
                                <a href="javascript:;" title="Báo cáo vi phạm" data-bs-toggle="dropdown"><i class="fa-solid fa-ellipsis"></i></a>
                                <ul class="dropdown-menu">
                                    ${d.userid.id === userId || userRole === 1 ?
                    `<li><a class="dropdown-item" href="javascript:;" onclick="changeCmt(${d.id})">Chỉnh sửa bình luận</a></li>
                                    <li><a class="dropdown-item" href="javascript:;" onclick="deleteCmt('${deleted}/${d.id}', ${d.id})">Xóa bình luận</a></li>
                                    ` : ``}
                                    ${!isNaN(userId) ? `<li><a class="dropdown-item" href="javascript:;" onclick="reportCmt('${report}/${d.id}')">Báo cáo vi phạm</a></li>` : `<li><span class="dropdown-item">Vui lòng đăng nhập</span></li>`}
                                </ul>
                            </div>
                          </div>
                          <p class="small m-1"><i class="fa-solid fa-clock"></i> ${moment(d.createdDate).locale("vi").fromNow()}</p>
                          <p class="m-2" id="old-content${d.id}">${d.content}</p>
                          <div style="display: none;" id="change-content${d.id}">
                            <textarea class="form-control m-2" id="new-content${d.id}" rows="2" style="background-color: #eff;">${d.content}</textarea>
                            <button type="button" class="btn btn-outline-success" onclick="saveChangeCmt('${changed}/${d.id}', ${d.id})">Lưu thay đổi</button>
                            <button type="button" class="btn btn-outline-danger" onclick="cancelChangeCmt(${d.id})">Hủy</button>
                          </div>
                          <div class="d-flex justify-content-between align-items-center m-1">
                            <div class="d-flex align-items-center">
                              <a href="javascript:;" class="link-muted me-2" title="Like" onclick="likeCount('${voted}/${d.id}', ${d.id})"><i class="fa-solid fa-thumbs-up me-1"></i><span id="likeCount${d.id}">${d.likeCount}</span></a>
                              <a href="javascript:;" class="link-muted" title="Dislike" onclick="dislike('${voted}/${d.id}', ${d.id})"><i class="fa-solid fa-thumbs-down me-1"></i><span id="dislike${d.id}">${d.dislike}</span></a>
                            </div>
                            ${userRole === 1 || userRole === 2 ? `<a href="javascript:;" class="link-muted" id="reply${d.id}" onclick="reply(${d.id})"><i class="fa-solid fa-reply me-1"></i>Trả lời</a>` : ``}
                          </div>
                        </div>
                      </div>
                    </div>
                </div>
            `;
        }
        let el = document.getElementById("comments");
        el.innerHTML = msg;
        let paginates = document.getElementById("pagination-comment");
        let page = "";
        for (let i = 1; i <= data.paginates.totalPage; i++) {
            page += `
                <li class="page-item"><a class="page-link" href="javascript:;" onclick="loadComments('${endpoint}?page=${i}', '${voted}', '${report}', '${deleted}', '${changed}', '${leveled}')">${i}</a></li>
            `;
        }
        paginates.innerHTML = page;
        for (let d of data.comments) {
            levelCmt(leveled + "/" + d.userid.exp, d.userid.id);
        }
        loadSpinner("none");
    });
}

function addComment(endpoint, voted, report, deleted, changed, leveled) {
    var contentValue = document.getElementById("content-comment").value;
    if (contentValue.trim().replace(/\s+/g, '').length >= 4) {
        loadSpinner("block");
        fetch(endpoint, {
            method: "POST",
            body: JSON.stringify({
                "content": contentValue
            }),
            headers: {
                "Content-Type": "application/json"
            }
        }).then(res => res.json()).then(d => {
            let userRole = parseInt(document.getElementById("currentUserRole").textContent);
            let el = document.getElementById("comments");
            el.innerHTML = `
                <div class="d-flex flex-start m-2" id="comment${d.id}">
                    <div class="d-none required-exp-${d.userid.id}"></div>
                    <a href="javascript:;" onclick="accountView('${endpoint}/${d.userid.id}', '${leveled}')" class="js-modal-user">
                        <div class="position-relative float-start">
                            <img class="shadow-1-strong me-3" src="${d.userid.avatar}" alt="${d.userid.fullname}" width="50" height="50" />
                            <img class="avatar-frame item-frame" width="50" height="50" src="${d.userid.avatarFrame.url}"/>
                        </div>
                    </a>
                    <div class="card w-100">
                      <div class="card-body p-1">
                        <div>
                          <div class="d-flex align-items-center justify-content-between">
                            <h6>${d.userid.fullname} <img class="rounded rank-${d.userid.id}" src="https://res.cloudinary.com/dkmug1913/image/upload/v1709201059/WebApp/Rank/iron_abwewo.png" style="width: 30px; height: 30px;" alt="rank" />
                                ${d.userid.userRole.id === 1 ? `<span class="badge bg-danger m-1 level-name-${d.userid.id}" style="font-size: 11px;">Trùm cuối</span><span class="badge bg-success m-1 level-name-${d.userid.id}" style="font-size: 11px;">Quản trị viên</span><span class="badge bg-primary m-1 level-name-${d.userid.id}" style="font-size: 11px;">${d.userid.exp}</span>`
                    : d.userid.userRole.id === 2 ? `<span class="badge bg-success m-1 level-name-${d.userid.id}" style="font-size: 11px;">Quản trị viên</span><span class="badge bg-primary m-1 level-name-${d.userid.id}" style="font-size: 11px;">${d.userid.exp}</span>`
                    : `<span class="badge bg-primary level-name-${d.userid.id}" style="font-size: 11px;">${d.userid.exp}</span>`}
                            </h6>
                            <div class="dropdown mr-auto">
                                <a href="javascript:;" title="Báo cáo vi phạm" data-bs-toggle="dropdown"><i class="fa-solid fa-ellipsis"></i></a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="javascript:;" onclick="changeCmt(${d.id})">Chỉnh sửa bình luận</a></li>
                                    <li><a class="dropdown-item" href="javascript:;" onclick="deleteCmt('${deleted}/${d.id}', ${d.id})">Xóa bình luận</a></li>
                                    <li><a class="dropdown-item" href="javascript:;" onclick="reportCmt('${report}/${d.id}')">Báo cáo vi phạm</a></li>
                                </ul>
                            </div>
                          </div>
                          <p class="small m-1"><i class="fa-solid fa-clock"></i> ${moment(d.createdDate).locale("vi").fromNow()}</p>
                          <p class="m-2" id="old-content${d.id}">${d.content}</p>
                          <div style="display: none;" id="change-content${d.id}">
                            <textarea class="form-control m-2" id="new-content${d.id}" rows="2" style="background-color: #eff;">${d.content}</textarea>
                            <button type="button" class="btn btn-outline-success" onclick="saveChangeCmt('${changed}/${d.id}', ${d.id})">Lưu thay đổi</button>
                            <button type="button" class="btn btn-outline-danger" onclick="cancelChangeCmt(${d.id})">Hủy</button>
                          </div>
                          <div class="d-flex justify-content-between align-items-center m-1">
                            <div class="d-flex align-items-center">
                              <a href="javascript:;" class="link-muted me-2" title="Like" onclick="likeCount('${voted}/${d.id}', ${d.id})"><i class="fa-solid fa-thumbs-up me-1"></i><span id="likeCount${d.id}">${d.likeCount}</span></a>
                              <a href="javascript:;" class="link-muted" title="Dislike" onclick="dislike('${voted}/${d.id}', ${d.id})"><i class="fa-solid fa-thumbs-down me-1"></i><span id="dislike${d.id}">${d.dislike}</span></a>
                            </div>
                            ${userRole === 1 || userRole === 2 ? `<a href="javascript:;" class="link-muted" id="reply${d.id}" onclick="reply(${d.id})"><i class="fa-solid fa-reply me-1"></i>Trả lời</a>` : ``}
                          </div>
                        </div>
                      </div>
                    </div>
                </div>
            ` + el.innerHTML;
            levelCmt(leveled + "/" + d.userid.exp, d.userid.id);
            loadSpinner("none");
        });
    } else {
        Swal.fire('Bình luận không thành công!', 'Vui lòng nhập tối thiểu 4 kí tự!', 'warning');
    }
}

function banComment() {
    Swal.fire('Bình luận không thành công!', 'Bạn đang bị cấm bình luận do quy phạm tiêu chuẩn cộng đồng.', 'warning');
}

function hasUserCmtToday(userId, cmtId) {
    const key = `liking_${userId}_${cmtId}`;
    const lastLikingTimestamp = localStorage.getItem(key);
    if (lastLikingTimestamp) {
        const lastLikingDate = new Date(Number(lastLikingTimestamp));
        const today = new Date();
        return (
                lastLikingDate.getDate() === today.getDate() &&
                lastLikingDate.getMonth() === today.getMonth() &&
                lastLikingDate.getFullYear() === today.getFullYear()
                );
    }
    return false;
}

function saveUserCmt(userId, cmtId) {
    const key = `liking_${userId}_${cmtId}`;
    localStorage.setItem(key, Date.now());
}

function likeCount(endpoint, cmtid) {
    let userId = parseInt(document.getElementById("currentUserId").textContent);
    if (userId === "" || isNaN(userId)) {
        const failureAlert = document.querySelector('.alert-absolute-nologin');
        failureAlert.style.display = 'block';
        setTimeout(() => {
            failureAlert.style.display = 'none';
        }, 5000);
        return;
    } else if (hasUserCmtToday(userId, cmtid)) {
        const failureAlert = document.querySelector('.alert-absolute');
        failureAlert.style.display = 'block';
        setTimeout(() => {
            failureAlert.style.display = 'none';
        }, 5000);
        return;
    }
    fetch(endpoint, {
        method: "put",
        body: JSON.stringify({
            "vote": 1
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status === 200) {
            let likeCount = document.getElementById(`likeCount${cmtid}`);
            var like = parseInt(likeCount.textContent) + 1;
            likeCount.innerHTML = like;
            saveUserCmt(userId, cmtid);
        }
    });
}

function dislike(endpoint, cmtid) {
    let userId = parseInt(document.getElementById("currentUserId").textContent);
    if (userId === "" || isNaN(userId)) {
        const failureAlert = document.querySelector('.alert-absolute-nologin');
        failureAlert.style.display = 'block';
        setTimeout(() => {
            failureAlert.style.display = 'none';
        }, 5000);
        return;
    } else if (hasUserCmtToday(userId, cmtid)) {
        const failureAlert = document.querySelector('.alert-absolute');
        failureAlert.style.display = 'block';
        setTimeout(() => {
            failureAlert.style.display = 'none';
        }, 5000);
        return;
    }
    fetch(endpoint, {
        method: "put",
        body: JSON.stringify({
            "vote": 0
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status === 200) {
            let dislikeCount = document.getElementById(`dislike${cmtid}`);
            var dislike = parseInt(dislikeCount.textContent) + 1;
            dislikeCount.innerHTML = dislike;
            saveUserCmt(userId, cmtid);
        }
    });
}

function reportCmt(endpoint) {
    fetch(endpoint, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status === 200) {
            const failureAlert = document.querySelector('.alert-absolute-report');
            failureAlert.style.display = 'block';
            setTimeout(() => {
                failureAlert.style.display = 'none';
            }, 5000);
        }
    });
}

function changeCmt(id) {
    var content = document.getElementById(`old-content${id}`);
    var changeContent = document.getElementById(`change-content${id}`);
    content.style.display = 'none';
    changeContent.style.display = 'block';
}

function saveChangeCmt(endpoint, id) {
    var content = document.getElementById(`old-content${id}`);
    var changeContent = document.getElementById(`change-content${id}`);
    var newContent = document.getElementById(`new-content${id}`);
    fetch(endpoint, {
        method: "put",
        body: JSON.stringify({
            "content": newContent.value
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => {
        if (res.status === 200) {
            content.textContent = newContent.value;
            content.style.display = 'block';
            changeContent.style.display = 'none';
        }
    });
}

function cancelChangeCmt(id) {
    var content = document.getElementById(`old-content${id}`);
    var changeContent = document.getElementById(`change-content${id}`);
    content.style.display = 'block';
    changeContent.style.display = 'none';
}

function deleteCmt(endpoint, id) {
    Swal.fire({
        title: 'Xóa',
        text: 'Bạn có chắc chắn muốn xóa ?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
// Hành động khi người dùng xác nhận
            fetch(endpoint, {
                method: "delete"
            }).then(res => {
                if (res.status === 204) {
                    var element = document.getElementById(`comment${id}`);
                    element.classList.remove('d-flex');
                    element.style.display = 'none';
                    Swal.fire('Xóa thành công!', 'Bình luận của bạn đã bị xóa!', 'success');
                } else
                    Swal.fire('Xóa không thành công!', 'Bình luận của bạn đã bị cáo vi phạm, quản trị viên đang xem xét!', 'error');
            });
        }
    });
}

function levelCmt(endpoint, id) {
    fetch(endpoint, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/xml'
        }
    }).then(res =>
        res.json()
    ).then(data => {
        var levelsCmt = document.querySelectorAll(`.card-body .level-name-${id}`);
        var ranks = document.querySelectorAll(`.card-body .rank-${id}`);
        var levels = document.querySelectorAll(`.level-${id}`);
        var titleRanks = document.querySelectorAll(`.account-rank-img-${id}`);
        var requiredExps = document.querySelectorAll(`.required-exp-${id}`);
        levelsCmt.forEach(function (levelCmt) {
            if (levelCmt.textContent !== "Trùm cuối" && levelCmt.textContent !== "Quản trị viên") {
                levelCmt.textContent = data.level.userRank;
            }
            ranks.forEach(rank => {
                rank.src = data.level.rankImg;
            });
            titleRanks.forEach(titleRank => {
                titleRank.src = data.level.rankImg;
            });
            levels.forEach(level => {
                level.textContent = data.level.levelName;
                level.style.color = data.level.rankColor;
            });
            requiredExps.forEach(requiredExp => {
                requiredExp.textContent = data.level.requiredExp * 2;
            });
        });
    });
}

function reply(cmtid) {
    let reply = document.getElementById(`reply${cmtid}`);
    let formReply = ``;
    reply.innerHTML = formReply;
    Swal.fire('Thông báo!', 'Chức năng đang phát triển!', 'info');
}

function extractBaseEndpointAndPage(endpointed) {
    const endpoint = endpointed.split("?page=")[0]; // Lấy đường dẫn không bao gồm query string
    const page = endpointed.split("?page=")[1] !== undefined ? endpointed.split("?page=")[1] : 1; // Lấy giá trị của tham số "page" trong query string

    return {endpoint, page};
}
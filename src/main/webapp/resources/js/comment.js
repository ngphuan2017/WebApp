/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function loadSpinner(flag) {
    let els = document.getElementsByClassName("spinner");
    for (let d of els)
        d.style.display = flag;
}

function loadComments(endpoint, voted, report, deleted, changed, callback) {
    loadSpinner("block");
    fetch(endpoint).then(res => res.json()).then(data => {
        let msg = "";
        let userId = parseInt(document.getElementById("currentUserId").textContent);
        let userRole = parseInt(document.getElementById("currentUserRole").textContent);
        for (let d of data) {
            msg += `
                <div class="d-flex flex-start m-2" id="comment${d.id}">
                    <img class="rounded-circle shadow-1-strong me-3"
                      src="${d.userid.avatar}" alt="avatar" width="50" height="50" />
                    <div class="card w-100">
                      <div class="card-body p-1">
                        <div>
                          <div class="d-flex align-items-center justify-content-between">
                            <h6>${d.userid.fullname} <i class="fa fa-diamond" style="color: #999; font-size: 12px;"></i> <span class="level-name" style="font-size: 11px;">${d.userid.userRole.id === 1 ? "Trùm cuối" : d.userid.userRole.id === 2 ? "Quản trị viên" : d.userid.exp}</span></h6>
                            <div class="dropdown mr-auto">
                                <a href="javascript:;" title="Báo cáo vi phạm" data-bs-toggle="dropdown"><i class="fa-solid fa-ellipsis"></i></a>
                                <ul class="dropdown-menu">
                                    ${d.userid.id === userId ?
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
        callback();
        loadSpinner("none");
    });
}

function addComment(endpoint, voted, report, deleted, changed) {
    loadSpinner("block");
    fetch(endpoint, {
        method: "POST",
        body: JSON.stringify({
            "content": document.getElementById("content-comment").value
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(res => res.json()).then(d => {
        let userRole = parseInt(document.getElementById("currentUserRole").textContent);
        let el = document.getElementById("comments");
        el.innerHTML = `
                <div class="d-flex flex-start m-2" id="comment${d.id}">
                    <img class="rounded-circle shadow-1-strong me-3"
                      src="${d.userid.avatar}" alt="avatar" width="50" height="50" />
                    <div class="card w-100">
                      <div class="card-body p-1">
                        <div>
                          <div class="d-flex align-items-center justify-content-between">
                            <h6>${d.userid.fullname} <i class="fa fa-diamond" style="color: #999; font-size: 12px;"></i> <span class="level-name" style="font-size: 11px;">${d.userid.userRole.id === 1 ? "Trùm cuối" : d.userid.userRole.id === 2 ? "Quản trị viên" : d.userid.exp}</span></h6>
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
        var levelsCmt = document.querySelectorAll(".card-body .level-name");
        levelCmt(levelsCmt);
        loadSpinner("none");
    });
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
    if (confirm("Bạn chắc chắn xóa?") === true) {
        fetch(endpoint, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                var element = document.getElementById(`comment${id}`);
                element.classList.remove('d-flex');
                element.style.display = 'none';
            } else
                alert("Hệ thống đang có lỗi, vui lòng quay lại sau!");
        });
    }
}

function reply(cmtid) {
    let reply = document.getElementById(`reply${cmtid}`);
}
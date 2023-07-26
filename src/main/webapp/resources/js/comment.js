/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function loadSpinner(flag) {
    let els = document.getElementsByClassName("spinner");
    for (let d of els) 
        d.style.display = flag;
}

function loadComments(endpoint) {
    loadSpinner("block");
    debugger;
    fetch(endpoint).then(res => res.json()).then(data => {
        let msg = "";
        for (let d of data) {
            msg += `
                <div class="row bg-light m-1">
                    <div class="col-md-1 col-xs-3">
                        <h5>${d.user.firstName} ${d.user.lastName}</h5>
                    </div>
                    <div class="col-md-10 col-xs-9">
                        <p>${d.content}</p>
                        <small>Binh luan boi <a href="#">${d.user.username}</a> luc ${moment(d.createdDate).locale("vi").fromNow()}</small>
                    </div>
                </div>
            `;
        }
        let el = document.getElementById("comments");
        el.innerHTML = msg;
        loadSpinner("none");
    });
}

function addComment(endpoint) {
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
      
        let el = document.getElementById("comments");
        el.innerHTML += `
                <div class="row bg-light m-1">
                    <div class="col-md-1 col-xs-3">
                        <h5>${d.user.firstName} ${d.user.lastName}</h5>
                    </div>
                    <div class="col-md-10 col-xs-9">
                        <p>${d.content}</p>
                        <small>Binh luan boi <a href="#">${d.user.username}</a> luc ${moment(d.createdDate).locale("vi").fromNow()}</small>
                    </div>
                </div>
            ` + el.innerHTML;
        loadSpinner("none");
    });
}


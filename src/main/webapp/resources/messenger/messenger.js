/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


var chatbox = document.getElementById('fb-customer-chat');
chatbox.setAttribute("page_id", "107733572071094");
chatbox.setAttribute("attribution", "biz_inbox");

// Your SDK code 
window.fbAsyncInit = function () {
    FB.init({
        xfbml: true,
        version: 'v18.0'
    });
}
;

(function (d, s, id)
{
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id))
        return;
    js = d.createElement(s);
    js.id = id;
    js.src = 'https://connect.facebook.net/vi_VN/sdk/xfbml.customerchat.js';
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
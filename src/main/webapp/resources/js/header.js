$(document).ready(function() {
    $(window).scroll(function() {
        if($(this).scrollTop() > 92.2){
            $('#nav').addClass('sticky');
        }else{
            $('#nav').removeClass('sticky');
        }
    });
});
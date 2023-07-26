$(document).ready(function(){
    $(window).scroll(function(){
        if($(this).scrollTop() > 600){
            $('#backtop').fadeIn();
        }else{
            $('#backtop').fadeOut();
        }
    });
    $('#backtop').click(function(){
        $('html, body').animate({scrollTop: 0}, 2000);
        return false;
    });
});
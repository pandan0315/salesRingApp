
    //----- OPEN
    $('[data-popup-open]').on('click', function()  {
       
        var targeted_popup_class = jQuery(this).attr('data-popup-open');
        $('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);
         $(".overlay" ).fadeIn( 200, function() {});
       
     
        
 
        
    });

 
    //----- CLOSE
    $('[data-popup-close]').on('click', function()  {
        var targeted_popup_class = jQuery(this).attr('data-popup-close');
        $('[data-popup="' + targeted_popup_class + '"]').fadeOut(350);
        $(".overlay" ).fadeOut( 600, function() {});
    });


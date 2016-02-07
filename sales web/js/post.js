    //----- OPEN
    $('#btnShow').on('click', function()  {
       
        
        $('#box').fadeIn(350,function(){});
         $(".overlay" ).fadeIn( 200, function() {});
       
     
        
 
        
    });

 
    //----- CLOSE
    $(".overlay").on('click', function()  {
        $('#box').fadeOut(350,function(){});
        $(".overlay" ).fadeOut( 600, function() {});
    });
